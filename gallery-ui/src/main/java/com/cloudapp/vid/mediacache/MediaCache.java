package com.cloudapp.vid.mediacache;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cloudapp.vid.utils.FileUtils;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

public class MediaCache {

	private static List<String> imageList;
	static int i = 0;
	public static void populateMediaFiles(String folder) {
		try {
			imageList = FileUtils.getImagesList(folder);
			debug();
		} catch (IOException e) {
			Notification.show("Sorry, cannot read images for this location.", Type.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	private static void debug() {
		i = 0;
		imageList.forEach((file) -> {
			System.out.println(i + " : " + file);
			i++;
		});
	}

	public static int getCacheSize() {
		return imageList.size();
	}

	public static String getFileAtIndex(Integer index) {
		return imageList.get(index);
	}

	public static void clearCache() {
		if(imageList != null && !imageList.isEmpty()) {
			imageList.clear();
		}
	}
	
	public static List<String> getImageList() {
		if(imageList == null) {
			return imageList = new ArrayList<String>();
		}
		return imageList;
	}
	
}
