package com.github.mrstampy.esp.openbci;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.github.mrstampy.esp.dsp.AbstractDSPValues;
import com.github.mrstampy.esp.dsp.EspSignalUtilities;
import com.github.mrstampy.esp.dsp.lab.AbstractRawEspConnection;
import com.github.mrstampy.esp.multiconnectionsocket.MultiConnectionSocketException;
import com.github.mrstampy.esp.openbci.dsp.OpenBCISignalAggregator;
import com.github.mrstampy.esp.openbci.dsp.OpenBCISignalUtilities;

public class OpenBCIConnection extends AbstractRawEspConnection<MultiConnectOpenBCISocket> {

	private MultiConnectOpenBCISocket socket;

	private OpenBCISignalUtilities utilities = new OpenBCISignalUtilities();
	private Map<Integer, OpenBCISignalAggregator> aggregators = new ConcurrentHashMap<Integer, OpenBCISignalAggregator>();

	public OpenBCIConnection() throws IOException {
		this(false);
	}

	public OpenBCIConnection(boolean broadcast) throws IOException {
		socket = new MultiConnectOpenBCISocket(broadcast);
		initAggregators();
	}

	@Override
	public void start() throws MultiConnectionSocketException {
		for (OpenBCISignalAggregator aggregator : aggregators.values()) {
			getSocket().addListener(aggregator);
		}

		super.start();
	}

	@Override
	public void stop() {
		try {
			super.stop();
		} finally {
			for (OpenBCISignalAggregator aggregator : aggregators.values()) {
				getSocket().removeListener(aggregator);
			}
		}
	}

	@Override
	public EspSignalUtilities getUtilities() {
		return utilities;
	}

	@Override
	public AbstractDSPValues getDSPValues() {
		return OpenBCIDSPValues.getInstance();
	}

	@Override
	public double[][] getCurrent() {
		return getCurrentFor(1);
	}

	@Override
	public double[][] getCurrent(int numSamples) {
		return getCurrentFor(numSamples, 1);
	}

	@Override
	public double[][] getCurrentFor(int channelNumber) {
		return aggregators.get(channelNumber).getCurrentSecondOfSampledData();
	}

	@Override
	public double[][] getCurrentFor(int numSamples, int channelNumber) {
		return aggregators.get(channelNumber).getCurrentSecondOfSampledData(numSamples);
	}

	@Override
	public String getName() {
		return "ESP OpenBCI";
	}

	@Override
	protected MultiConnectOpenBCISocket getSocket() {
		return socket;
	}

	private void initAggregators() {
		int numChannels = getNumChannels();
		for (int i = 1; i <= numChannels; i++) {
			OpenBCISignalAggregator aggregator = new OpenBCISignalAggregator(i);
			aggregators.put(i, aggregator);
		}
	}

}
