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

import com.github.mrstampy.esp.multiconnectionsocket.RawDataSampleBuffer;

// TODO: Auto-generated Javadoc
/**
 * The Class SampleBuffer.
 */
public class SampleBuffer extends RawDataSampleBuffer<byte[]> {

	private final int channelNumber;

	/**
	 * Instantiates a new sample buffer.
	 *
	 * @param channelNumber the channel number
	 */
	public SampleBuffer(int channelNumber) {
		super(OpenBCIProperties.getIntegerProperty("buffer.size.one.second"), OpenBCIDSPValues.getInstance()
				.getSampleSize());
		this.channelNumber = channelNumber;
	}

	/**
	 * Gets the channel number.
	 *
	 * @return the channel number
	 */
	public int getChannelNumber() {
		return channelNumber;
	}

	/* (non-Javadoc)
	 * @see com.github.mrstampy.esp.multiconnectionsocket.RawDataSampleBuffer#addSample(java.lang.Object)
	 */
	public void addSample(byte[] buffer) {
		int numSamples = getNumberOfSamples(buffer);

		double[] samples = new double[numSamples];

		process(buffer, samples);

		addSampleImpl(samples);
	}

	private void process(byte[] buffer, double[] samples) {
		// TODO Auto-generated method stub
	}

	private int getNumberOfSamples(byte[] data) {
		return 10; // TODO implement me
	}
}
