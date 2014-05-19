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
package com.github.mrstampy.esp.openbci.rxtx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.mrstampy.esp.openbci.OpenBCIConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class RxtxDataBuffer.
 */
public class RxtxDataBuffer implements OpenBCIConstants {
	private static final Logger log = LoggerFactory.getLogger(RxtxDataBuffer.class);

	private byte[] buffer = new byte[0];

	/**
	 * Adds the.
	 *
	 * @param message
	 *          the message
	 */
	public void add(byte... message) {
		byte[] b = new byte[buffer.length + message.length];

		System.arraycopy(buffer, 0, b, 0, buffer.length);
		System.arraycopy(message, 0, b, buffer.length, message.length);

		buffer = b;
	}

	/**
	 * Gets the.
	 *
	 * @return the byte[]
	 */
	public byte[] get() {
		if (buffer.length == 0) return null;

		int startIdx = -1;
		int endIdx = -1;
		for (int i = 0; i < buffer.length; i++) {
			if (buffer[i] == START_PACKET) startIdx = i;

			if (buffer[i] == END_PACKET && startIdx > -1) endIdx = i;

			if (startIdx >= 0 && endIdx > 0) break;
		}

		if (startIdx == -1 || endIdx == -1) return null;

		byte[] message = new byte[(endIdx - startIdx) + 1];

		System.arraycopy(buffer, startIdx, message, 0, message.length);

		if (message.length == buffer.length) {
			clear();
		} else {
			resizeBuffer(startIdx, endIdx);
		}

		return message.length == 0 ? null : message;
	}

	/**
	 * Gets the buffer copy.
	 *
	 * @return the buffer copy
	 */
	public byte[] getBufferCopy() {
		byte[] b = new byte[buffer.length];
		System.arraycopy(buffer, 0, b, 0, b.length);
		return b;
	}

	/**
	 * Clear.
	 */
	public void clear() {
		buffer = new byte[0];
	}

	private void resizeBuffer(int startIdx, int endIdx) {
		if (startIdx != 0) log.warn("Dropping message part prior to index {}", startIdx);

		byte[] b = new byte[buffer.length - 1 - endIdx];
		System.arraycopy(buffer, endIdx + 1, b, 0, b.length);
		buffer = b;
	}
}
