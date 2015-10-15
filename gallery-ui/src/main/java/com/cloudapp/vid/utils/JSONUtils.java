package com.cloudapp.vid.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

public class JSONUtils {

	private static JSONObject root;
	
	@SuppressWarnings("unchecked")
	public static List<String> getAllViews() {
		if(root == null) {
			readRootJsonObject();
		}
		List<String> views = new ArrayList<String>();
		JSONArray countries = getArrayWithKey(null, "countries");
		countries.forEach((country) -> {
			JSONObject obj1 = (JSONObject)country;
			JSONArray cities = JSONUtils.getArrayWithKey(obj1, "cities");
			cities.forEach((city) -> {
				JSONObject obj2 = (JSONObject)city;
				String viewName = getStringWithKey(obj2, "simpleName");
				views.add(viewName);
			});
		});
		return views;
	}

	private static void readRootJsonObject() {
		JSONParser parser = new JSONParser();
		InputStream stream = JSONUtils.class.getClassLoader().getResourceAsStream("menu.json");
		try {
			InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
			JSONObject object = (JSONObject) parser.parse(reader);
			root = getObjectWithKey(object, "menu");
		} catch (IOException | ParseException e) {
			Notification.show("Sorry, something went wrong!!!", Type.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
	}

	public static JSONObject getObjectWithKey(JSONObject object, String key) {
		if(object == null) {
			if(root == null) {
				readRootJsonObject();
			}
			return (JSONObject) root.get(key);
		}
		return (JSONObject) object.get(key);
	}
	
	public static JSONArray getArrayWithKey(JSONObject object, String key) {
		if(object == null) {
			if(root == null) {
				readRootJsonObject();
			}
			return (JSONArray) root.get(key);
		}
		return (JSONArray) object.get(key);
	}
	
	public static String getStringWithKey(JSONObject object, String key) {
		if(object == null) {
			if(root == null) {
				readRootJsonObject();
			}
			return (String) root.get(key);
		}
		return (String) object.get(key);
	}

}
