package com.github.mrstampy.esp.openbci;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenBCIProperties {
	private static final Logger log = LoggerFactory.getLogger(OpenBCIProperties.class);

	private static Properties props;

	static {
		try {
			loadProperties();
		} catch (IOException e) {
			String error = "Could not load esp.openbci.properties.  Ensure the file exists & is on the classpath";
			log.error(error, e);
			throw new IllegalStateException(error, e);
		}
	}

	public static String getProperty(String key) {
		return props.getProperty(key);
	}

	public static int getIntegerProperty(String key) {
		String val = getProperty(key);

		try {
			return Integer.parseInt(val);
		} catch (Exception e) {
			log.error("The value {} for key {} is not an integer", val, key, e);
			throw new RuntimeException(e);
		}
	}

	public static double getDoubleProperty(String key) {
		String val = getProperty(key);

		try {
			return Double.parseDouble(val);
		} catch (Exception e) {
			log.error("The value {} for key {} is not a double", val, key, e);
			throw new RuntimeException(e);
		}
	}

	public static boolean getBooleanProperty(String key) {
		String val = getProperty(key);

		return val == null ? false : Boolean.parseBoolean(val);
	}

	public static String getChannelNameKey(int channelNumber) {
		return "esp.openbci.channel" + channelNumber + ".name";
	}

	public static String getChannelIdentiferKey(int channelNumber) {
		return "esp.openbci.channel" + channelNumber + ".identifier";
	}

	public static String getChannelLengthKey(int channelNumber) {
		return "esp.openbci.channel" + channelNumber + ".length";
	}

	private static void loadProperties() throws IOException {
		InputStream is = OpenBCIProperties.class.getResourceAsStream("esp.openbci.properties");
		props = new Properties();
		props.load(is);
	}

	private OpenBCIProperties() {
		// no instantiation for you
	}
}
