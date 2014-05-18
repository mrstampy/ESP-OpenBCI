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
package com.github.mrstampy.esp.openbci.subscription;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.mrstampy.esp.multiconnectionsocket.AbstractSocketConnector;
import com.github.mrstampy.esp.multiconnectionsocket.event.AbstractMultiConnectionEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class OpenBCISocketConnector.
 */
public class OpenBCISocketConnector extends AbstractSocketConnector<OpenBCIEventType> {

	private List<OpenBCIEventListener> listeners = Collections.synchronizedList(new ArrayList<OpenBCIEventListener>());

	/**
	 * Instantiates a new open bci socket connector.
	 *
	 * @param socketBroadcasterHost the socket broadcaster host
	 */
	public OpenBCISocketConnector(String socketBroadcasterHost) {
		super(socketBroadcasterHost);
	}

	/**
	 * Adds the listener.
	 *
	 * @param l the l
	 */
	public void addListener(OpenBCIEventListener l) {
		if (l != null && !listeners.contains(l)) listeners.add(l);
	}

	/**
	 * Removes the listener.
	 *
	 * @param l the l
	 */
	public void removeListener(OpenBCIEventListener l) {
		if (l != null) listeners.remove(l);
	}

	/**
	 * Clear listeners.
	 */
	public void clearListeners() {
		listeners.clear();
	}

	/**
	 * Subscribe.
	 *
	 * @return true, if successful
	 */
	public boolean subscribe() {
		return subscribe(new OpenBCISubscriptionRequest());
	}

	/* (non-Javadoc)
	 * @see com.github.mrstampy.esp.multiconnectionsocket.AbstractSocketConnector#subscribeAll()
	 */
	@Override
	public boolean subscribeAll() {
		return subscribe();
	}

	/* (non-Javadoc)
	 * @see com.github.mrstampy.esp.multiconnectionsocket.AbstractSocketConnector#processEvent(com.github.mrstampy.esp.multiconnectionsocket.event.AbstractMultiConnectionEvent)
	 */
	@Override
	protected void processEvent(AbstractMultiConnectionEvent<OpenBCIEventType> message) {
		OpenBCIEvent event = (OpenBCIEvent) message;

		for (OpenBCIEventListener l : listeners) {
			l.dataEventPerformed(event);
		}
	}

}
