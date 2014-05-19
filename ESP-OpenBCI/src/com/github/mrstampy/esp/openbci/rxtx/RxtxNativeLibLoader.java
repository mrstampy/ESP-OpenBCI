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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class RxtxNativeLibLoader.
 */
public class RxtxNativeLibLoader {
	private static final Logger log = LoggerFactory.getLogger(RxtxNativeLibLoader.class);

	/** The Constant MAC_OS. */
	public static final String MAC_OS = "mac os x";
	
	/** The Constant WINDOWS_OS. */
	public static final String WINDOWS_OS = "windows";

	/** The Constant LINUX_OS. */
	public static final String LINUX_OS = "linux";
	
	public static final String WIN_32 = "x86";
	
	public static final String WIN_64 = "64";
	
	/** The Constant X86_64_ARCH. */
	public static final String X86_64_ARCH = "x86_64";
	
	/** The Constant IA64_ARCH. */
	public static final String IA64_ARCH = "ia64";
	
	/** The Constant I686_ARCH. */
	public static final String I686_ARCH = "i686";

	/** The Constant SOLARIS_OS. */
	public static final String SOLARIS_OS = "solaris";
	
	/** The Constant SPARC64_ARCH. */
	public static final String SPARC64_ARCH = "sparc64";
	
	/** The Constant SPARC32_ARCH. */
	public static final String SPARC32_ARCH = "sparc32";

	/**
	 * Load rxtx serial native lib.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void loadRxtxSerialNativeLib() throws IOException {
		String osName = System.getProperty("os.name").toLowerCase();
		String osArch = System.getProperty("os.arch");

		loadRxtxSerialNativeLib(osName, osArch);
	}

	/**
	 * Load rxtx serial native lib.
	 *
	 * @param osName the os name
	 * @param osArch the os arch
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void loadRxtxSerialNativeLib(String osName, String osArch) throws IOException {
		log.debug("Loading RXTX native lib for os {}, arch {}", osName, osArch);

		String path = getPath(osName, osArch);

		log.debug("Getting library from {}", path);

		File lib = extractToFileSystem(path);

		log.debug("Loading library from {}", lib);

		System.load(lib.getAbsolutePath());
	}

	private static File extractToFileSystem(String path) throws IOException {
		BufferedInputStream is = new BufferedInputStream(RxtxNativeLibLoader.class.getResourceAsStream(path));

		File tempDir = new File(System.getProperty("java.io.tmpdir"));
		
		File temp = new File(tempDir, path.substring(path.lastIndexOf("/") + 1, path.length()));
		temp.deleteOnExit();

		byte[] b = new byte[is.available()];
		is.read(b);

		BufferedOutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(temp));
			out.write(b);

			return temp;
		} finally {
			if (out != null) out.close();
			is.close();
		}
	}

	private static String getPath(String osName, String osArch) {
		if (osName.contains(WINDOWS_OS)) return getWindowsNativeSerialLib(osArch);

		if (osName.contains(MAC_OS)) return "/Mac_OS_X/librxtxSerial.jnilib";

		if (osName.contains(LINUX_OS)) return getLinuxNativeSerialLib(osArch);

		if (osName.contains(SOLARIS_OS)) return getSolarisNativeSerialLib(osArch);

		throw new IllegalStateException("No RXTX native lib for " + osName + ", " + osArch);
	}
	
	private static String getWindowsNativeSerialLib(String osArch) {
		if(osArch.contains(WIN_64)) return "/Windows/64bit/rxtxSerial.dll";
		
		if(osArch.contains(WIN_32)) return "/Windows/32bit/rxtxSerial.dll";
		
		return "/Windows/i386-mingw32/rxtxSerial.dll";
	}

	private static String getSolarisNativeSerialLib(String osArch) {
		if (osArch.contains(SPARC32_ARCH)) return "/Solaris/sparc-solaris/sparc32-sun-solaris2.8/librxtxSerial.so";

		if (osArch.contains(SPARC64_ARCH)) return "/Solaris/sparc-solaris/sparc64-sun-solaris2.8/librxtxSerial.so";

		throw new IllegalStateException("No Solaris RXTX native lib for " + osArch);
	}

	private static String getLinuxNativeSerialLib(String osArch) {
		if (osArch.contains(I686_ARCH)) return "/Linux/i686-unknown-linux-gnu/librxtxSerial.so";

		if (osArch.contains(IA64_ARCH)) return "/Linux/ia64-unknown-linux-gnu/librxtxSerial.so";

		if (osArch.contains(X86_64_ARCH)) return "/Linux/x86_64-unknown-linux-gnu/librxtxSerial.so";

		throw new IllegalStateException("No Linux RXTX native lib for " + osArch);
	}
	
	private RxtxNativeLibLoader() {
		// no instantiation for you
	}
}
