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

import java.util.EventListener;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving openBCIEvent events. The class that is
 * interested in processing a openBCIEvent event implements this interface, and
 * the object created with that class is registered with a component using the
 * component's <code>addOpenBCIEventListener<code> method. When
 * the openBCIEvent event occurs, that object's appropriate
 * method is invoked.
 *
 * @see OpenBCIEventEvent
 */
public interface OpenBCIEventListener extends EventListener {

	/**
	 * Data event performed.
	 *
	 * @param event
	 *          the event
	 */
	void dataEventPerformed(OpenBCIEvent event);
}
