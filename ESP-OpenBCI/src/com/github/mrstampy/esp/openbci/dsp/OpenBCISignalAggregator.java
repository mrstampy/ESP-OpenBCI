package com.github.mrstampy.esp.openbci.dsp;

import com.github.mrstampy.esp.dsp.RawSignalAggregator;
import com.github.mrstampy.esp.openbci.OpenBCIDSPValues;
import com.github.mrstampy.esp.openbci.subscription.OpenBCIEvent;
import com.github.mrstampy.esp.openbci.subscription.OpenBCIEventListener;

public final class OpenBCISignalAggregator extends RawSignalAggregator implements OpenBCIEventListener {

	private final int channelNumber;

	public OpenBCISignalAggregator(int channelNumber) {
		super(OpenBCIDSPValues.getInstance().getSampleRate());
		this.channelNumber = channelNumber;
	}

	public void dataEventPerformed(OpenBCIEvent event) {
		if (isForChannel(event)) addSample(event.getSample());
	}

	private boolean isForChannel(OpenBCIEvent event) {
		return getChannelNumber() == event.getChannelNumber();
	}

	public int getChannelNumber() {
		return channelNumber;
	}

}
