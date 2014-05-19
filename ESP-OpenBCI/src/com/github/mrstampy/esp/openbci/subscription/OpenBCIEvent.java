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
package com.github.mrstampy.esp.openbci.subscription;

import com.github.mrstampy.esp.multiconnectionsocket.event.AbstractMultiConnectionEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class OpenBCIEvent.
 */
public class OpenBCIEvent extends AbstractMultiConnectionEvent<OpenBCIEventType> {
	private static final long serialVersionUID = -8968759760933731795L;

	private final int channelNumber;
	private final double[] sample;

	/**
	 * Instantiates a new open bci event.
	 *
	 * @param sample
	 *          the sample
	 * @param channelNumber
	 *          the channel number
	 */
	public OpenBCIEvent(double[] sample, int channelNumber) {
		super(OpenBCIEventType.rawSignal);
		this.sample = sample;
		this.channelNumber = channelNumber;
	}

	/**
	 * Gets the sample.
	 *
	 * @return the sample
	 */
	public double[] getSample() {
		return sample;
	}

	/**
	 * Gets the channel number.
	 *
	 * @return the channel number
	 */
	public int getChannelNumber() {
		return channelNumber;
	}
}
