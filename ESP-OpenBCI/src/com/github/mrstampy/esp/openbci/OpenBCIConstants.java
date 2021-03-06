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

// TODO: Auto-generated Javadoc
/**
 * The Interface OpenBCIConstants.
 */
public interface OpenBCIConstants {

	/** The start packet. */
	public static final byte START_PACKET = (byte) (0xA0);

	/** The end packet. */
	public static final byte END_PACKET = (byte) (0xC0);
	
	/** The num samples idx. */
	public static final int NUM_CHANNELS_IDX = 1;
	
	/** The channels start idx. */
	public static final int CHANNELS_START_IDX = 2;

}
