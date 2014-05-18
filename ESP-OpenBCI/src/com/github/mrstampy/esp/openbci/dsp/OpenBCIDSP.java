package com.github.mrstampy.esp.openbci.dsp;

import java.util.Map;
import java.util.Map.Entry;

import com.github.mrstampy.esp.dsp.EspDSP;
import com.github.mrstampy.esp.dsp.EspSignalUtilities;
import com.github.mrstampy.esp.dsp.RawProcessedListener;
import com.github.mrstampy.esp.dsp.RawSignalAggregator;
import com.github.mrstampy.esp.openbci.MultiConnectOpenBCISocket;
import com.github.mrstampy.esp.openbci.OpenBCIDSPValues;

public class OpenBCIDSP extends EspDSP<MultiConnectOpenBCISocket> {

	private OpenBCISignalAggregator aggregator;
	private OpenBCISignalUtilities utilities = new OpenBCISignalUtilities();

	public OpenBCIDSP(MultiConnectOpenBCISocket socket, int channelNumber, double... frequencies) {
		super(socket, OpenBCIDSPValues.getInstance().getSampleRate(), frequencies);
		
		aggregator = new OpenBCISignalAggregator(channelNumber);

		socket.addListener(aggregator);
	}

	@Override
	protected void destroyImpl() {
		socket.removeListener(aggregator);
	}

	@Override
	protected RawSignalAggregator getAggregator() {
		return aggregator;
	}

	@Override
	protected EspSignalUtilities getUtilities() {
		return utilities;
	}

	/**
	 * Main method to demonstrate {@link OpenBCIDSP} use.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String... args) throws Exception {
		MultiConnectOpenBCISocket socket = new MultiConnectOpenBCISocket();

		final OpenBCIDSP dsp = new OpenBCIDSP(socket, 1, 3.6, 5.4, 7.83, 10.4);
		dsp.addProcessedListener(new RawProcessedListener() {

			@Override
			public void signalProcessed() {
				showValues(dsp.getSnapshot());
			}
		});

		socket.start();
	}

	private static void showValues(Map<Double, Double> snapshot) {
		for (Entry<Double, Double> entry : snapshot.entrySet()) {
			System.out.println("Frequency: " + entry.getKey() + ", value: " + entry.getValue());
		}
		System.out.println();
	}

}
