/*
 * ESP-OpenBCI Copyright (C) 2014 Burton Alexander
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 * 
 */
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

// TODO: Auto-generated Javadoc
/**
 * The Class OpenBCIConnection.
 */
public class OpenBCIConnection extends AbstractRawEspConnection<MultiConnectOpenBCISocket> {

	private MultiConnectOpenBCISocket socket;

	private OpenBCISignalUtilities utilities = new OpenBCISignalUtilities();
	private Map<Integer, OpenBCISignalAggregator> aggregators = new ConcurrentHashMap<Integer, OpenBCISignalAggregator>();

	/**
	 * Instantiates a new open bci connection.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public OpenBCIConnection() throws IOException {
		this(false);
	}

	/**
	 * Instantiates a new open bci connection.
	 *
	 * @param broadcast the broadcast
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public OpenBCIConnection(boolean broadcast) throws IOException {
		socket = new MultiConnectOpenBCISocket(broadcast);
		initAggregators();
	}

	/* (non-Javadoc)
	 * @see com.github.mrstampy.esp.dsp.lab.AbstractRawEspConnection#start()
	 */
	@Override
	public void start() throws MultiConnectionSocketException {
		for (OpenBCISignalAggregator aggregator : aggregators.values()) {
			getSocket().addListener(aggregator);
		}

		super.start();
	}

	/* (non-Javadoc)
	 * @see com.github.mrstampy.esp.dsp.lab.AbstractRawEspConnection#stop()
	 */
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

	/* (non-Javadoc)
	 * @see com.github.mrstampy.esp.dsp.lab.RawEspConnection#getUtilities()
	 */
	@Override
	public EspSignalUtilities getUtilities() {
		return utilities;
	}

	/* (non-Javadoc)
	 * @see com.github.mrstampy.esp.dsp.lab.RawEspConnection#getDSPValues()
	 */
	@Override
	public AbstractDSPValues getDSPValues() {
		return OpenBCIDSPValues.getInstance();
	}

	/* (non-Javadoc)
	 * @see com.github.mrstampy.esp.dsp.lab.RawEspConnection#getCurrent()
	 */
	@Override
	public double[][] getCurrent() {
		return getCurrentFor(1);
	}

	/* (non-Javadoc)
	 * @see com.github.mrstampy.esp.dsp.lab.RawEspConnection#getCurrent(int)
	 */
	@Override
	public double[][] getCurrent(int numSamples) {
		return getCurrentFor(numSamples, 1);
	}

	/* (non-Javadoc)
	 * @see com.github.mrstampy.esp.dsp.lab.RawEspConnection#getCurrentFor(int)
	 */
	@Override
	public double[][] getCurrentFor(int channelNumber) {
		return aggregators.get(channelNumber).getCurrentSecondOfSampledData();
	}

	/* (non-Javadoc)
	 * @see com.github.mrstampy.esp.dsp.lab.RawEspConnection#getCurrentFor(int, int)
	 */
	@Override
	public double[][] getCurrentFor(int numSamples, int channelNumber) {
		return aggregators.get(channelNumber).getCurrentSecondOfSampledData(numSamples);
	}

	/* (non-Javadoc)
	 * @see com.github.mrstampy.esp.dsp.lab.RawEspConnection#getName()
	 */
	@Override
	public String getName() {
		return "ESP OpenBCI";
	}

	/* (non-Javadoc)
	 * @see com.github.mrstampy.esp.dsp.lab.AbstractRawEspConnection#getSocket()
	 */
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
