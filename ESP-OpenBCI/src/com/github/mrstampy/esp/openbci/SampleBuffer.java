package com.github.mrstampy.esp.openbci;

import com.github.mrstampy.esp.multiconnectionsocket.RawDataSampleBuffer;

public class SampleBuffer extends RawDataSampleBuffer<byte[]> {

	private final int channelNumber;

	public SampleBuffer(int channelNumber) {
		super(OpenBCIProperties.getIntegerProperty("buffer.size.one.second"), OpenBCIDSPValues.getInstance()
				.getSampleSize());
		this.channelNumber = channelNumber;
	}

	public int getChannelNumber() {
		return channelNumber;
	}

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
