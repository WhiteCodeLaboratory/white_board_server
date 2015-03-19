package com.whitecodelab.whiteboard.common.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

	private static final PropertiesUtil INSTANCE = new PropertiesUtil();

	private Properties props;
	
	private String localHostName = "";
	private String propertiesPath = null;
	
	public static PropertiesUtil getInstance() {
		return INSTANCE;
	}

	private PropertiesUtil() {
		
		try {
			
			String serverMode = System.getProperty("server.mode");
			String propertiesHome = System.getProperty("prop.home");
			
			localHostName = java.net.InetAddress.getLocalHost().getHostName();

			InputStream is = null;
			if(EmptyUtil.isStringEmpty(propertiesHome)){
				is = getClass().getResourceAsStream("/local/properties/poc.properties");	
			} else {
				propertiesPath = propertiesHome + serverMode + "/properties/poc.properties";
				is = new FileInputStream(propertiesPath);
			}
			
			Properties properties = new Properties();
			properties.load(is);
			is.close();
			props = properties;
			
		} catch (Exception e) {  
			e.printStackTrace();
		}
	}

	public Properties getProperties() {
		return this.props; 
	}
	
	/**
	 * String 값을 조회한다.
	 * @param key 키 값
	 * @return value
	 */
	public String getString(String key) {
		if (this.props == null)
			return null;
		
		return props.getProperty(key);
	}
	
	/**
	 * Integer 값을 조회한다.
	 * @param key 키 값
	 * @return value
	 */
	public int getInt(String key) throws NumberFormatException {
		if (this.props == null)
			return -1;
		
		String value = getString(key);
		return Integer.parseInt(value);
	}
	
	/**
	 * Long 값을 조회한다.
	 * @param key 키 값
	 * @return value
	 */
	public long getLong(String key) throws NumberFormatException {
		if (this.props == null)
			return -1;
		
		String value = getString(key);
		return Long.parseLong(value);
	}
	
	/**
	 * Boolean 값을 조회한다.
	 * @param key 키 값
	 * @return value
	 */
	public boolean getBoolean(String key) {
		if (this.props == null)
			return false;
		
		String value = getString(key);
		return Boolean.parseBoolean(value);
	}
	
	public String getLocalHostName() {
		return localHostName;
	}
	
	public String getServerName() {
		return System.getProperty("server.name");
	}

	
}
