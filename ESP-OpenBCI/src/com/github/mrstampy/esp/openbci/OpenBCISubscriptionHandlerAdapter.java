package com.github.mrstampy.esp.openbci;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.mrstampy.esp.multiconnectionsocket.AbstractSubscriptionHandlerAdapter;
import com.github.mrstampy.esp.openbci.subscription.OpenBCIEventType;
import com.github.mrstampy.esp.openbci.subscription.OpenBCISubscriptionRequest;

public class OpenBCISubscriptionHandlerAdapter extends
		AbstractSubscriptionHandlerAdapter<OpenBCIEventType, MultiConnectOpenBCISocket, OpenBCISubscriptionRequest> {

	private static final Logger log = LoggerFactory.getLogger(OpenBCISubscriptionHandlerAdapter.class);

	public OpenBCISubscriptionHandlerAdapter(MultiConnectOpenBCISocket socket) {
		super(socket);
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		if (message instanceof OpenBCISubscriptionRequest) {
			subscribe(session, (OpenBCISubscriptionRequest) message);
		} else {
			log.error("Cannot process message {}", message);
		}
	}

}
