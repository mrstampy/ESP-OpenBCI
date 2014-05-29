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

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class OpenBCIProperties.
 */
public class OpenBCIProperties {
	private static final Logger log = LoggerFactory.getLogger(OpenBCIProperties.class);

	private static Properties props;

	static {
		try {
			loadProperties();
		} catch (Exception e) {
			String error = "Could not load esp.openbci.properties.  Ensure the file exists & is on the root of the classpath";
			log.error(error, e);
			throw new IllegalStateException(error, e);
		}
	}

	/**
	 * Gets the property.
	 *
	 * @param key
	 *          the key
	 * @return the property
	 */
	public static String getProperty(String key) {
		return props.getProperty(key);
	}

	/**
	 * Gets the integer property.
	 *
	 * @param key
	 *          the key
	 * @return the integer property
	 */
	public static int getIntegerProperty(String key) {
		String val = getProperty(key);

		try {
			return Integer.parseInt(val);
		} catch (Exception e) {
			log.error("The value {} for key {} is not an integer", val, key, e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Gets the double property.
	 *
	 * @param key
	 *          the key
	 * @return the double property
	 */
	public static double getDoubleProperty(String key) {
		String val = getProperty(key);

		try {
			return Double.parseDouble(val);
		} catch (Exception e) {
			log.error("The value {} for key {} is not a double", val, key, e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Gets the boolean property.
	 *
	 * @param key
	 *          the key
	 * @return the boolean property
	 */
	public static boolean getBooleanProperty(String key) {
		String val = getProperty(key);

		return val == null ? false : Boolean.parseBoolean(val);
	}

	private static void loadProperties() throws IOException {
		InputStream is = OpenBCIProperties.class.getResourceAsStream("/esp.openbci.properties");
		props = new Properties();
		props.load(is);
	}

	private OpenBCIProperties() {
		// no instantiation for you
	}
}
