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
 * The Enum OpenBCICommand.
 */
public enum OpenBCICommand {

	//@formatter:off
	/** The stop. */
	STOP("s"),
	
	/** The start text. */
	START_TEXT("x"),
	
	/** The start binary. */
	START_BINARY("b"),
	
	/** The start binary waux. */
	START_BINARY_WAUX("n"),
	
	/** The STAR t_ binar y_4 chan. */
	START_BINARY_4CHAN("v"),
	
	/** The activate filters. */
	ACTIVATE_FILTERS("F"),
	
	/** The deactivate filters. */
	DEACTIVATE_FILTERS("g"),
	
	/** The bias auto. */
	BIAS_AUTO("`"),
	
	/** The bias fixed. */
	BIAS_FIXED("~");
	//@formatter:on

	/** The command. */
	String command;

	/**
	 * Instantiates a new open bci command.
	 *
	 * @param command
	 *          the command
	 */
	OpenBCICommand(String command) {
		this.command = command;
	}

	/**
	 * Gets the command.
	 *
	 * @return the command
	 */
	public String getCommand() {
		return command;
	}

}
