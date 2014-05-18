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

import java.math.BigDecimal;

import com.github.mrstampy.esp.dsp.AbstractDSPValues;
import com.github.mrstampy.esp.dsp.EspSignalUtilities;
import com.github.mrstampy.esp.openbci.OpenBCIDSPValues;
import com.github.mrstampy.esp.openbci.OpenBCIProperties;

import ddf.minim.analysis.HammingWindow;

// TODO: Auto-generated Javadoc
/**
 * The Class OpenBCISignalUtilities.
 */
public class OpenBCISignalUtilities extends EspSignalUtilities {

	private static final BigDecimal SIGNAL_BREADTH;

	static {
		double lowVal = OpenBCIProperties.getDoubleProperty("lowest.signal.val");
		double highVal = OpenBCIProperties.getDoubleProperty("highest.signal.val");

		SIGNAL_BREADTH = new BigDecimal(highVal - lowVal);
	}

	/**
	 * Instantiates a new open bci signal utilities.
	 */
	public OpenBCISignalUtilities() {
		super(new HammingWindow());
	}

	/* (non-Javadoc)
	 * @see com.github.mrstampy.esp.dsp.EspSignalUtilities#getFFTSize()
	 */
	@Override
	protected int getFFTSize() {
		return OpenBCIDSPValues.getInstance().getSampleSize();
	}

	/* (non-Javadoc)
	 * @see com.github.mrstampy.esp.dsp.EspSignalUtilities#getSampleRate()
	 */
	@Override
	protected double getSampleRate() {
		return OpenBCIDSPValues.getInstance().getSampleRate();
	}

	/* (non-Javadoc)
	 * @see com.github.mrstampy.esp.dsp.EspSignalUtilities#getRawSignalBreadth()
	 */
	@Override
	protected BigDecimal getRawSignalBreadth() {
		return SIGNAL_BREADTH;
	}

	/* (non-Javadoc)
	 * @see com.github.mrstampy.esp.dsp.EspSignalUtilities#getDSPValues()
	 */
	@Override
	public AbstractDSPValues getDSPValues() {
		return OpenBCIDSPValues.getInstance();
	}
}
