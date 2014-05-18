package com.github.mrstampy.esp.openbci.subscription;

import com.github.mrstampy.esp.multiconnectionsocket.subscription.MultiConnectionSubscriptionRequest;

public class OpenBCISubscriptionRequest implements MultiConnectionSubscriptionRequest<OpenBCIEventType> {

	private static final long serialVersionUID = 6054403295795982525L;

	@Override
	public OpenBCIEventType[] getEventTypes() {
		return new OpenBCIEventType[] { OpenBCIEventType.rawSignal };
	}

	@Override
	public boolean containsEventType(OpenBCIEventType eventType) {
		return OpenBCIEventType.rawSignal == eventType;
	}

}
