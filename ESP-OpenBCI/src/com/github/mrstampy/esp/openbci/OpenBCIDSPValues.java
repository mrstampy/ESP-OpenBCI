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

import com.github.mrstampy.esp.dsp.AbstractDSPValues;

// TODO: Auto-generated Javadoc
/**
 * The Class OpenBCIDSPValues.
 */
public class OpenBCIDSPValues extends AbstractDSPValues {

	private static final OpenBCIDSPValues instance = new OpenBCIDSPValues();

	/**
	 * Gets the single instance of OpenBCIDSPValues.
	 *
	 * @return single instance of OpenBCIDSPValues
	 */
	public static OpenBCIDSPValues getInstance() {
		return instance;
	}

	private OpenBCIDSPValues() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.mrstampy.esp.dsp.AbstractDSPValues#initialize()
	 */
	@Override
	protected void initialize() {
		setSampleRate(OpenBCIProperties.getIntegerProperty("sample.rate"));
		setSampleSize(OpenBCIProperties.getIntegerProperty("fft.size"));
		setNumChannels(OpenBCIProperties.getIntegerProperty("esp.openbci.num.channels"));
	}

}
