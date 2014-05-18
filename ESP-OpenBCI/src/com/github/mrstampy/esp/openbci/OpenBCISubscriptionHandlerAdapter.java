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

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.mrstampy.esp.multiconnectionsocket.AbstractSubscriptionHandlerAdapter;
import com.github.mrstampy.esp.openbci.subscription.OpenBCIEventType;
import com.github.mrstampy.esp.openbci.subscription.OpenBCISubscriptionRequest;

// TODO: Auto-generated Javadoc
/**
 * The Class OpenBCISubscriptionHandlerAdapter.
 */
public class OpenBCISubscriptionHandlerAdapter extends
		AbstractSubscriptionHandlerAdapter<OpenBCIEventType, MultiConnectOpenBCISocket, OpenBCISubscriptionRequest> {

	private static final Logger log = LoggerFactory.getLogger(OpenBCISubscriptionHandlerAdapter.class);

	/**
	 * Instantiates a new open bci subscription handler adapter.
	 *
	 * @param socket the socket
	 */
	public OpenBCISubscriptionHandlerAdapter(MultiConnectOpenBCISocket socket) {
		super(socket);
	}

	/* (non-Javadoc)
	 * @see com.github.mrstampy.esp.multiconnectionsocket.AbstractSubscriptionHandlerAdapter#messageReceived(org.apache.mina.core.session.IoSession, java.lang.Object)
	 */
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		if (message instanceof OpenBCISubscriptionRequest) {
			subscribe(session, (OpenBCISubscriptionRequest) message);
		} else {
			log.error("Cannot process message {}", message);
		}
	}

}
