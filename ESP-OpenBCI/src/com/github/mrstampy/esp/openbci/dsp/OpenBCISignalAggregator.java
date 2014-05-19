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

import com.github.mrstampy.esp.dsp.RawSignalAggregator;
import com.github.mrstampy.esp.openbci.OpenBCIDSPValues;
import com.github.mrstampy.esp.openbci.subscription.OpenBCIEvent;
import com.github.mrstampy.esp.openbci.subscription.OpenBCIEventListener;

// TODO: Auto-generated Javadoc
/**
 * The Class OpenBCISignalAggregator.
 */
public final class OpenBCISignalAggregator extends RawSignalAggregator implements OpenBCIEventListener {

	private final int channelNumber;

	/**
	 * Instantiates a new open bci signal aggregator.
	 *
	 * @param channelNumber
	 *          the channel number
	 */
	public OpenBCISignalAggregator(int channelNumber) {
		super(OpenBCIDSPValues.getInstance().getSampleRate());
		this.channelNumber = channelNumber;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.mrstampy.esp.openbci.subscription.OpenBCIEventListener#
	 * dataEventPerformed
	 * (com.github.mrstampy.esp.openbci.subscription.OpenBCIEvent)
	 */
	public void dataEventPerformed(OpenBCIEvent event) {
		if (isForChannel(event)) addSample(event.getSample());
	}

	private boolean isForChannel(OpenBCIEvent event) {
		return getChannelNumber() == event.getChannelNumber();
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
