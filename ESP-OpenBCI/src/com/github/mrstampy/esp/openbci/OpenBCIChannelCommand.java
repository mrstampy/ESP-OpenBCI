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
 * The Enum OpenBCIChannelCommand.
 */
public enum OpenBCIChannelCommand {

	/** The channel one. */
	CHANNEL_ONE(1, "q", "1", "!", "Q", "A", "Z"),

	/** The channel two. */
	CHANNEL_TWO(2, "w", "2", "@", "W", "S", "X"),

	/** The channel three. */
	CHANNEL_THREE(3, "e", "3", "#", "E", "D", "C"),

	/** The channel four. */
	CHANNEL_FOUR(4, "r", "4", "$", "R", "F", "V"),

	/** The channel five. */
	CHANNEL_FIVE(5, "t", "5", "%", "T", "G", "B"),

	/** The channel six. */
	CHANNEL_SIX(6, "y", "6", "^", "Y", "H", "N"),

	/** The channel seven. */
	CHANNEL_SEVEN(7, "u", "7", "&", "U", "J", "M"),

	/** The channel eight. */
	CHANNEL_EIGHT(8, "i", "8", "*", "I", "K", "<");

	/** The channel number. */
	int channelNumber;

	/** The activate. */
	String activate;

	/** The deactivate. */
	String deactivate;

	/** The activate leadoff p. */
	String activateLeadoffP;

	/** The deactivate leadoff p. */
	String deactivateLeadoffP;

	/** The activate leadoff n. */
	String activateLeadoffN;

	/** The deactivate leadoff n. */
	String deactivateLeadoffN;

	//@formatter:off
	private OpenBCIChannelCommand(
			int channelNumber, 
			String activate, 
			String deactivate, 
			String activateLeadoffP, 
			String deactivateLeadoffP, 
			String activateLeadoffN, 
			String deactivateLeadoffN) {
		
		this.channelNumber = channelNumber;
		this.activate = activate;
		this.deactivate = deactivate;
		this.activateLeadoffP = activateLeadoffP;
		this.deactivateLeadoffP = deactivateLeadoffP;
		this.activateLeadoffN = activateLeadoffN;
		this.deactivateLeadoffN = deactivateLeadoffN; 
	}
	//@formatter:on

	/**
	 * Gets the channel number.
	 *
	 * @return the channel number
	 */
	public int getChannelNumber() {
		return channelNumber;
	}

	/**
	 * Gets the activate.
	 *
	 * @return the activate
	 */
	public String getActivate() {
		return activate;
	}

	/**
	 * Gets the deactivate.
	 *
	 * @return the deactivate
	 */
	public String getDeactivate() {
		return deactivate;
	}

	/**
	 * Gets the activate leadoff p.
	 *
	 * @return the activate leadoff p
	 */
	public String getActivateLeadoffP() {
		return activateLeadoffP;
	}

	/**
	 * Gets the deactivate leadoff p.
	 *
	 * @return the deactivate leadoff p
	 */
	public String getDeactivateLeadoffP() {
		return deactivateLeadoffP;
	}

	/**
	 * Gets the activate leadoff n.
	 *
	 * @return the activate leadoff n
	 */
	public String getActivateLeadoffN() {
		return activateLeadoffN;
	}

	/**
	 * Gets the deactivate leadoff n.
	 *
	 * @return the deactivate leadoff n
	 */
	public String getDeactivateLeadoffN() {
		return deactivateLeadoffN;
	}

	/**
	 * Gets the from channel number.
	 *
	 * @param channelNumber
	 *          the channel number
	 * @return the from channel number
	 */
	public static OpenBCIChannelCommand getFromChannelNumber(int channelNumber) {
		switch (channelNumber) {
		case 1:
			return OpenBCIChannelCommand.CHANNEL_ONE;
		case 2:
			return OpenBCIChannelCommand.CHANNEL_TWO;
		case 3:
			return OpenBCIChannelCommand.CHANNEL_THREE;
		case 4:
			return OpenBCIChannelCommand.CHANNEL_FOUR;
		case 5:
			return OpenBCIChannelCommand.CHANNEL_FIVE;
		case 6:
			return OpenBCIChannelCommand.CHANNEL_SIX;
		case 7:
			return OpenBCIChannelCommand.CHANNEL_SEVEN;
		case 8:
			return OpenBCIChannelCommand.CHANNEL_EIGHT;
		}

		throw new IllegalArgumentException("Channel numbers must be between 1 and 8 inclusive");
	}
}
