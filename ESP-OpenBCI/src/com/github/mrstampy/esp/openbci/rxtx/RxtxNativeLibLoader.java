package com.github.mrstampy.esp.openbci.rxtx;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RxtxNativeLibLoader {
	private static final Logger log = LoggerFactory.getLogger(RxtxNativeLibLoader.class);

	public static final String MAC_OS = "apple";
	public static final String WINDOWS_OS = "windows";

	public static final String LINUX_OS = "linux";
	public static final String X86_64_ARCH = "x86_64";
	public static final String IA64_ARCH = "ia64";
	public static final String I686_ARCH = "i686";

	public static final String SOLARIS_OS = "solaris";
	public static final String SPARC64_ARCH = "sparc64";
	public static final String SPARC32_ARCH = "sparc32";

	public static void loadRxtxSerialNativeLib() throws IOException {
		String osName = System.getProperty("os.name").toLowerCase();
		String osArch = System.getProperty("os.arch");

		loadRxtxSerialNativeLib(osName, osArch);
	}

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

		File temp = File.createTempFile("esp.rxtx", ".tmp");
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
		if (osName.contains(WINDOWS_OS)) return "Windows/i386-mingw32/rxtxSerial.dll";

		if (osName.contains(MAC_OS)) return "Mac_OS_X/librxtxSerial.jnilib";

		if (osName.contains(LINUX_OS)) return getLinuxNativeSerialLib(osArch);

		if (osName.contains(SOLARIS_OS)) return getSolarisNativeSerialLib(osArch);

		throw new IllegalStateException("No RXTX native lib for " + osName + ", " + osArch);
	}

	private static String getSolarisNativeSerialLib(String osArch) {
		if (osArch.contains(SPARC32_ARCH)) return "Solaris/sparc-solaris/sparc32-sun-solaris2.8/librxtxSerial.so";

		if (osArch.contains(SPARC64_ARCH)) return "Solaris/sparc-solaris/sparc64-sun-solaris2.8/librxtxSerial.so";

		throw new IllegalStateException("No Solaris RXTX native lib for " + osArch);
	}

	private static String getLinuxNativeSerialLib(String osArch) {
		if (osArch.contains(I686_ARCH)) return "Linux/i686-unknown-linux-gnu/librxtxSerial.so";

		if (osArch.contains(IA64_ARCH)) return "Linux/ia64-unknown-linux-gnu/librxtxSerial.so";

		if (osArch.contains(X86_64_ARCH)) return "Linux/x86_64-unknown-linux-gnu/librxtxSerial.so";

		throw new IllegalStateException("No Linux RXTX native lib for " + osArch);
	}
	
	private RxtxNativeLibLoader() {
		// no instantiation for you
	}
}
