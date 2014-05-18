package com.github.mrstampy.esp.openbci.dsp;

import java.math.BigDecimal;

import com.github.mrstampy.esp.dsp.AbstractDSPValues;
import com.github.mrstampy.esp.dsp.EspSignalUtilities;
import com.github.mrstampy.esp.openbci.OpenBCIDSPValues;
import com.github.mrstampy.esp.openbci.OpenBCIProperties;

import ddf.minim.analysis.HammingWindow;

public class OpenBCISignalUtilities extends EspSignalUtilities {

	private static final BigDecimal SIGNAL_BREADTH;

	static {
		double lowVal = OpenBCIProperties.getDoubleProperty("lowest.signal.val");
		double highVal = OpenBCIProperties.getDoubleProperty("highest.signal.val");

		SIGNAL_BREADTH = new BigDecimal(highVal - lowVal);
	}

	public OpenBCISignalUtilities() {
		super(new HammingWindow());
	}

	@Override
	protected int getFFTSize() {
		return OpenBCIDSPValues.getInstance().getSampleSize();
	}

	@Override
	protected double getSampleRate() {
		return OpenBCIDSPValues.getInstance().getSampleRate();
	}

	@Override
	protected BigDecimal getRawSignalBreadth() {
		return SIGNAL_BREADTH;
	}

	@Override
	public AbstractDSPValues getDSPValues() {
		return OpenBCIDSPValues.getInstance();
	}
}
