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
package com.github.mrstampy.esp.openbci.dsp;

import java.util.Map;
import java.util.Map.Entry;

import com.github.mrstampy.esp.dsp.EspDSP;
import com.github.mrstampy.esp.dsp.EspSignalUtilities;
import com.github.mrstampy.esp.dsp.RawProcessedListener;
import com.github.mrstampy.esp.dsp.RawSignalAggregator;
import com.github.mrstampy.esp.openbci.MultiConnectOpenBCISocket;
import com.github.mrstampy.esp.openbci.OpenBCIDSPValues;

// TODO: Auto-generated Javadoc
/**
 * The Class OpenBCIDSP.
 */
public class OpenBCIDSP extends EspDSP<MultiConnectOpenBCISocket> {

	private OpenBCISignalAggregator aggregator;
	private OpenBCISignalUtilities utilities = new OpenBCISignalUtilities();

	/**
	 * Instantiates a new open bcidsp.
	 *
	 * @param socket
	 *          the socket
	 * @param channelNumber
	 *          the channel number
	 * @param frequencies
	 *          the frequencies
	 */
	public OpenBCIDSP(MultiConnectOpenBCISocket socket, int channelNumber, double... frequencies) {
		super(socket, OpenBCIDSPValues.getInstance().getSampleRate(), frequencies);

		aggregator = new OpenBCISignalAggregator(channelNumber);

		socket.addListener(aggregator);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.mrstampy.esp.dsp.EspDSP#destroyImpl()
	 */
	@Override
	protected void destroyImpl() {
		socket.removeListener(aggregator);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.mrstampy.esp.dsp.EspDSP#getAggregator()
	 */
	@Override
	protected RawSignalAggregator getAggregator() {
		return aggregator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.mrstampy.esp.dsp.EspDSP#getUtilities()
	 */
	@Override
	protected EspSignalUtilities getUtilities() {
		return utilities;
	}

	/**
	 * Main method to demonstrate {@link OpenBCIDSP} use.
	 *
	 * @param args
	 *          the arguments
	 * @throws Exception
	 *           the exception
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
