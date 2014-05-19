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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.After;
import org.junit.Test;

import com.github.mrstampy.esp.openbci.OpenBCIConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class RxtxDataBufferTest.
 */
public class RxtxDataBufferTest implements OpenBCIConstants {

	private RxtxDataBuffer buffer = new RxtxDataBuffer();
	private int numChannels = 3;

	/**
	 * After.
	 *
	 * @throws Exception
	 *           the exception
	 */
	@After
	public void after() throws Exception {
		buffer.clear();
	}

	/**
	 * Test multiple messages.
	 *
	 * @throws Exception
	 *           the exception
	 */
	@Test
	public void testMultipleMessages() throws Exception {
		int expected = 3;

		for (int i = 0; i < expected; i++) {
			buffer.add(getFullMessage());
		}

		int cnt = 0;
		byte[] msg = buffer.get();
		while (msg != null) {
			assertTrue(isValidMessage(msg));
			cnt++;
			msg = buffer.get();
		}

		assertTrue(cnt == expected);
		assertTrue(buffer.getBufferCopy().length == 0);
	}

	/**
	 * Test split message.
	 *
	 * @throws Exception
	 *           the exception
	 */
	@Test
	public void testSplitMessage() throws Exception {
		buffer.add(getHeader());
		byte[] msg = buffer.get();

		assertNull(msg);

		buffer.add(getData());
		msg = buffer.get();

		assertNull(msg);

		buffer.add(END_PACKET);
		buffer.add(getHeader());

		msg = buffer.get();
		assertNotNull(msg);
		assertTrue(isValidMessage(msg));

		msg = buffer.get();
		assertNull(msg);

		assertTrue(buffer.getBufferCopy().length == 2);

		buffer.add(getData());
		buffer.add(END_PACKET);

		msg = buffer.get();
		assertNotNull(msg);
		assertTrue(isValidMessage(msg));

		assertTrue(buffer.getBufferCopy().length == 0);
	}

	/**
	 * Test broken messages.
	 *
	 * @throws Exception
	 *           the exception
	 */
	@Test
	public void testBrokenMessages() throws Exception {
		buffer.add(getHeader());
		buffer.add(getHeader());
		buffer.add(getHeader());
		buffer.add(getData());
		buffer.add(END_PACKET);

		byte[] msg = buffer.get();
		assertNotNull(msg);
		assertTrue(isValidMessage(msg));
		assertTrue(buffer.getBufferCopy().length == 0);

		buffer.add(getHeader());
		buffer.add(getData());
		buffer.add(getData());
		buffer.add(END_PACKET);

		msg = buffer.get();
		assertNotNull(msg);
		assertTrue(!isValidMessage(msg));
		assertTrue(buffer.getBufferCopy().length == 0);

		buffer.add(getHeader());
		buffer.add(getHeader());
		buffer.add(getData());
		buffer.add(getData());
		buffer.add(END_PACKET);
		buffer.add(END_PACKET);
		buffer.add(END_PACKET);

		msg = buffer.get();
		assertNotNull(msg);
		assertTrue(!isValidMessage(msg));
		assertTrue(buffer.getBufferCopy().length == 2);

		buffer.add(getHeader());
		buffer.add(getHeader());
		buffer.add(getData());
		buffer.add(END_PACKET);

		msg = buffer.get();
		assertNotNull(msg);
		assertTrue(isValidMessage(msg));
		assertTrue(buffer.getBufferCopy().length == 0);
	}

	private boolean isValidMessage(byte[] msg) {
		if (msg == null || msg.length != getFullMessageSize()) return false;

		return msg[0] == START_PACKET && msg[msg.length - 1] == END_PACKET && msg[1] == numChannels;
	}

	private byte[] getHeader() {
		byte[] b = new byte[2];

		b[0] = START_PACKET;
		b[1] = (byte) numChannels;

		return b;
	}

	private byte[] getData() {
		byte[] b = new byte[numChannels * 4];

		Arrays.fill(b, (byte) 1);

		return b;
	}

	private int getFullMessageSize() {
		return 3 + (4 * numChannels);
	}

	private byte[] getFullMessage() {
		byte[] msg = new byte[getFullMessageSize()];

		Arrays.fill(msg, (byte) 1);

		msg[0] = START_PACKET;
		msg[1] = (byte) numChannels;
		msg[msg.length - 1] = END_PACKET;

		return msg;
	}
}
