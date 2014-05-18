package com.github.mrstampy.esp.openbci.subscription;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.mrstampy.esp.multiconnectionsocket.AbstractSocketConnector;
import com.github.mrstampy.esp.multiconnectionsocket.event.AbstractMultiConnectionEvent;

public class OpenBCISocketConnector extends AbstractSocketConnector<OpenBCIEventType> {

	private List<OpenBCIEventListener> listeners = Collections.synchronizedList(new ArrayList<OpenBCIEventListener>());

	public OpenBCISocketConnector(String socketBroadcasterHost) {
		super(socketBroadcasterHost);
	}

	public void addListener(OpenBCIEventListener l) {
		if (l != null && !listeners.contains(l)) listeners.add(l);
	}

	public void removeListener(OpenBCIEventListener l) {
		if (l != null) listeners.remove(l);
	}

	public void clearListeners() {
		listeners.clear();
	}

	public boolean subscribe() {
		return subscribe(new OpenBCISubscriptionRequest());
	}

	@Override
	public boolean subscribeAll() {
		return subscribe();
	}

	@Override
	protected void processEvent(AbstractMultiConnectionEvent<OpenBCIEventType> message) {
		OpenBCIEvent event = (OpenBCIEvent) message;

		for (OpenBCIEventListener l : listeners) {
			l.dataEventPerformed(event);
		}
	}

}
