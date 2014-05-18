package com.github.mrstampy.esp.openbci;

import com.github.mrstampy.esp.dsp.AbstractDSPValues;

public class OpenBCIDSPValues extends AbstractDSPValues {

	private static final OpenBCIDSPValues instance = new OpenBCIDSPValues();

	public static OpenBCIDSPValues getInstance() {
		return instance;
	}

	private OpenBCIDSPValues() {
		super();
	}

	@Override
	protected void initialize() {
		setSampleRate(OpenBCIProperties.getIntegerProperty("sample.rate"));
		setSampleSize(OpenBCIProperties.getIntegerProperty("fft.size"));
		setNumChannels(OpenBCIProperties.getIntegerProperty("esp.openbci.num.channels"));
	}

}
