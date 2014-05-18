package com.github.mrstampy.esp.openbci;

import static com.github.mrstampy.esp.openbci.OpenBCIProperties.getBooleanProperty;
import static com.github.mrstampy.esp.openbci.OpenBCIProperties.getIntegerProperty;
import static com.github.mrstampy.esp.openbci.OpenBCIProperties.getProperty;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javolution.util.FastList;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.serial.SerialAddress;
import org.apache.mina.transport.serial.SerialAddress.DataBits;
import org.apache.mina.transport.serial.SerialAddress.FlowControl;
import org.apache.mina.transport.serial.SerialAddress.Parity;
import org.apache.mina.transport.serial.SerialAddress.StopBits;
import org.apache.mina.transport.serial.SerialConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.Observable;
import rx.Scheduler;
import rx.Scheduler.Inner;
import rx.Subscription;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import com.github.mrstampy.esp.multiconnectionsocket.AbstractMultiConnectionSocket;
import com.github.mrstampy.esp.multiconnectionsocket.MultiConnectionSocketException;
import com.github.mrstampy.esp.openbci.rxtx.RxtxNativeLibLoader;
import com.github.mrstampy.esp.openbci.subscription.OpenBCIEvent;
import com.github.mrstampy.esp.openbci.subscription.OpenBCIEventListener;

public class MultiConnectOpenBCISocket extends AbstractMultiConnectionSocket<byte[]> {
	private static final Logger log = LoggerFactory.getLogger(MultiConnectOpenBCISocket.class);

	// load the RXTX native library
	static {
		try {
			initRxtx();
		} catch (Exception e) {
			log.error("Unexpected exception loading RXTX native library", e);
			throw new RuntimeException(e);
		}
	}

	private static void initRxtx() throws IOException {
		if (getBooleanProperty("rxtx.lib.installed")) return;

		String osName = getProperty("os.override.name");
		String osArch = getProperty("os.override.arch");

		if (osName == null && osArch == null) {
			RxtxNativeLibLoader.loadRxtxSerialNativeLib();
		} else {
			RxtxNativeLibLoader.loadRxtxSerialNativeLib(osName, osArch);
		}
	}

	private SerialConnector connector;

	private List<OpenBCIEventListener> listeners = new FastList<OpenBCIEventListener>();

	private OpenBCISubscriptionHandlerAdapter subscriptionHandlerAdapter;

	private Map<Integer, SampleBuffer> samples = new ConcurrentHashMap<Integer, SampleBuffer>();

	private Scheduler scheduler = Schedulers.executor(Executors.newScheduledThreadPool(5));
	private Subscription subscription;

	public MultiConnectOpenBCISocket() throws IOException {
		this(false);
	}

	public MultiConnectOpenBCISocket(boolean broadcasting) throws IOException {
		super(broadcasting);
		initConnector();
		initSampleBuffers();
	}

	private void initSampleBuffers() {
		int numChannels = getIntegerProperty("esp.openbci.num.channels");

		if (numChannels <= 0) throw new IllegalArgumentException("esp.openbci.num.channels property must be > 0");

		for (int i = 1; i <= numChannels; i++) {
			samples.put(i, new SampleBuffer(i));
		}
	}

	public void addListener(OpenBCIEventListener l) {
		if (l != null && !listeners.contains(l)) listeners.add(l);
	}

	public void removeListener(OpenBCIEventListener l) {
		if (l != null) listeners.remove(l);
	}

	public void clearListeners() {
		listeners.clear();
	}

	/**
	 * When invoked the tuning functionality of the {@link SampleBuffer} will be
	 * activated. The tuning process takes ~ 10 seconds, during which the number
	 * of samples will be counted and used to resize the buffer to more closely
	 * represent 1 seconds' worth of data.
	 */
	public void tune() {
		if (!isConnected()) {
			log.warn("Must be connected to the OpenBCI hardware to tune");
			return;
		}

		log.info("Tuning sample buffer");

		for (SampleBuffer sampleBuffer : samples.values()) {
			sampleBuffer.tune();
		}

		scheduler.schedule(new Action1<Scheduler.Inner>() {

			@Override
			public void call(Inner t1) {
				for (SampleBuffer sampleBuffer : samples.values()) {
					sampleBuffer.stopTuning();
				}
			}
		}, 10, TimeUnit.SECONDS);
	}

	@Override
	public boolean isConnected() {
		return connector.isActive();
	}

	@Override
	protected void startImpl() throws MultiConnectionSocketException {
		String error = "Could not connect to OpenBCI hardware";

		try {
			ConnectFuture cf = connector.connect(getSerialAddress());
			cf.await(5000);
			if (!cf.isConnected()) throw new MultiConnectionSocketException(error);
		} catch (InterruptedException e) {
			log.error(error, e);
			throw new MultiConnectionSocketException(error, e);
		}

		scheduleSampling();
	}

	private void scheduleSampling() {
		OpenBCIDSPValues values = OpenBCIDSPValues.getInstance();
		long pause = 500l;
		long snooze = values.getSampleRateSleepTime();
		TimeUnit tu = values.getSampleRateUnits();

		subscription = scheduler.schedulePeriodically(new Action1<Scheduler.Inner>() {

			@Override
			public void call(Inner t1) {
				for (Entry<Integer, SampleBuffer> entry : samples.entrySet()) {
					processSnapshot(entry.getValue().getSnapshot(), entry.getKey());
				}
			}
		}, pause, snooze, tu);
	}

	private void processSnapshot(double[] snapshot, final int channelNumber) {
		Observable.just(snapshot).subscribe(new Action1<double[]>() {

			@Override
			public void call(double[] snap) {
				notifyListeners(snap, channelNumber);
				if (canBroadcast()) subscriptionHandlerAdapter.sendMultiConnectionEvent(new OpenBCIEvent(snap, channelNumber));
			}
		});
	}

	private void notifyListeners(double[] snapshot, int channelNumber) {
		if (listeners.isEmpty()) return;

		OpenBCIEvent event = new OpenBCIEvent(snapshot, channelNumber);
		for (OpenBCIEventListener l : listeners) {
			l.dataEventPerformed(event);
		}
	}

	@Override
	protected void stopImpl() {
		if (!isConnected()) return;

		try {
			connector.dispose();
		} finally {
			if(subscription != null) subscription.unsubscribe();
			initConnector();
		}
	}

	@Override
	protected IoHandler getHandlerAdapter() {
		subscriptionHandlerAdapter = new OpenBCISubscriptionHandlerAdapter(this);
		return subscriptionHandlerAdapter;
	}

	@Override
	protected void parseMessage(byte[] message) {
		Observable.just(message).subscribe(new Action1<byte[]>() {

			@Override
			public void call(byte[] t1) {
				int channelNumber = getChannelNumber(t1);

				if(channelNumber == -1) return;
				
				samples.get(channelNumber).addSample(t1);
			}
		});
	}

	/*
	 * @see OpenBCIProperties#getChannelIdentiferKey(int), return -1 to ignore
	 */
	private int getChannelNumber(byte[] message) {
		return 1; // TODO implement me
	}

	private void initConnector() {
		connector = new SerialConnector();
		connector.setHandler(new IoHandlerAdapter() {

			@Override
			public void messageReceived(IoSession session, Object message) throws Exception {
				// TODO a GROSS oversimplification; will need to be parsed to determine
				// msg length etc. prior to publishing
				publishMessage((byte[]) message);
			}

			@Override
			public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
				log.error("Unexpected RXTX exception", cause);
			}
		});
	}

	private SerialAddress getSerialAddress() {
		String portIdentifier = getProperty("port.identifier");
		int baudRate = getIntegerProperty("baud.rate");
		DataBits dataBits = getDataBits();
		StopBits stopBits = getStopBits();
		Parity parity = getParity();
		FlowControl flowControl = getFlowControl();

		return new SerialAddress(portIdentifier, baudRate, dataBits, stopBits, parity, flowControl);
	}

	private FlowControl getFlowControl() {
		String fc = getProperty("flow.control");

		return FlowControl.valueOf(fc);
	}

	private Parity getParity() {
		String p = getProperty("parity");

		return Parity.valueOf(p);
	}

	private StopBits getStopBits() {
		String sbs = getProperty("stop.bits");

		return StopBits.valueOf(sbs);
	}

	private DataBits getDataBits() {
		String dbs = getProperty("data.bits");

		return DataBits.valueOf(dbs);
	}

}
