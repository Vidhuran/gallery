package com.cloudapp.vid.utils;

import java.io.InputStream;
import java.util.Properties;

import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

public class PropertyUtils {

	private static Properties props = null;
	
	private static void loadProperties() {
		InputStream file = PropertyUtils.class.getClassLoader().getResourceAsStream("config.properties");
		
		try {
			props = new Properties();
			props.load(file);

		} catch (Exception e) {
			e.printStackTrace();
			Notification.show("Sorry, something went wrong!!!", Type.ERROR_MESSAGE);
		} 
	}
	
	public static String getProperty(String key) {
		if(props == null) {
			loadProperties();
		}
		String result = "";
		result = props.getProperty(key);
		return result;
	}
}
