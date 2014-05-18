package com.github.mrstampy.esp.openbci.subscription;

import com.github.mrstampy.esp.multiconnectionsocket.event.AbstractMultiConnectionEvent;

public class OpenBCIEvent extends AbstractMultiConnectionEvent<OpenBCIEventType> {
	private static final long serialVersionUID = -8968759760933731795L;
	
	private final int channelNumber;
	private final double[] sample;

	public OpenBCIEvent(double[] sample, int channelNumber) {
		super(OpenBCIEventType.rawSignal);
		this.sample = sample;
		this.channelNumber = channelNumber;
	}

	public double[] getSample() {
		return sample;
	}

	public int getChannelNumber() {
		return channelNumber;
	}
}
