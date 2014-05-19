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

import com.github.mrstampy.esp.multiconnectionsocket.AbstractSocketConnector;
import com.github.mrstampy.esp.openbci.dsp.OpenBCISignalAggregator;
import com.github.mrstampy.esp.openbci.subscription.OpenBCISocketConnector;

// TODO: Auto-generated Javadoc
/**
 * Main class to demonstrate local and remote notifications from the
 * {@link MultiConnectOpenBCISocket}.
 *
 * @author burton
 */
public class OpenBCITester {

	/**
	 * Demonstrates local raw data acquisition from the OpenBCI.
	 *
	 * @throws Exception
	 *           the exception
	 */
	protected static void testLocalAggregation() throws Exception {
		System.out.println("Local Aggregation");
		MultiConnectOpenBCISocket socket = new MultiConnectOpenBCISocket();

		OpenBCISignalAggregator aggregator = new OpenBCISignalAggregator(1);
		socket.addListener(aggregator);

		socket.start();

		printSampleLengths(aggregator, socket);
	}

	/**
	 * Connects to the {@link MultiConnectOpenBCISocket} on the default port
	 * (12345) to receive raw data events.
	 *
	 * @throws Exception
	 *           the exception
	 * @see {@link AbstractSocketConnector#SOCKET_BROADCASTER_KEY}
	 */
	protected static void testRemoteAggregation() throws Exception {
		System.out.println("Remote Aggregation");
		MultiConnectOpenBCISocket socket = new MultiConnectOpenBCISocket(true);

		OpenBCISocketConnector connector = new OpenBCISocketConnector("localhost");
		OpenBCISignalAggregator aggregator = new OpenBCISignalAggregator(1);
		connector.addListener(aggregator);

		connector.connect();
		connector.subscribe();

		socket.start();

		printSampleLengths(aggregator, socket);
	}

	private static void printSampleLengths(OpenBCISignalAggregator aggregator, MultiConnectOpenBCISocket socket)
			throws InterruptedException {
		boolean tuning = false;
		int cntr = 0;
		while (true) {
			Thread.sleep(1000);
			cntr++;
			if (!tuning && cntr > 4) {
				tuning = true;
				socket.tune();
			}
			double[][] sampled = aggregator.getCurrentSecondOfSampledData();

			int length = sampled.length;
			if (length > 0) {
				System.out.println(length); // should be mostly 100
				System.out.println(sampled[0].length); // should be == 512
			}
		}
	}

	/**
	 * No args == {@link #testLocalAggregation()}, any args ==
	 * {@link #testRemoteAggregation()}.
	 *
	 * @param args
	 *          the arguments
	 * @throws Exception
	 *           the exception
	 */
	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			testLocalAggregation();
		} else {
			testRemoteAggregation();
		}
	}

}
