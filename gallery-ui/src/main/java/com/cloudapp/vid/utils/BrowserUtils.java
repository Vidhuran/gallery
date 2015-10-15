package com.cloudapp.vid.utils;

import com.vaadin.ui.UI;

public class BrowserUtils {

	private static float width = -1;
	private static float height = -1;
	private static int centreWidth = -1;
	private static int centreHeight = -1;
	
	public static float getHeight() {
		if(isLoadingNeeded()) {
			getBrowserSize();
		}
		return height;
	}

	public static int getCentreHeight() {
		if(isLoadingNeeded()) {
			getBrowserSize();
		}
		return centreHeight;
	}
	
	public static int getCentreWidth() {
		if(isLoadingNeeded()) {
			getBrowserSize();
		}
		return centreWidth;
	}

	private static boolean isLoadingNeeded() {
		return (width == -1 || height == -1 || centreWidth == -1 || centreHeight == -1);
	}

	public static float getWidth() {
		if(isLoadingNeeded()) {
			getBrowserSize();
		}
		return width;
	}
	
	private static void getBrowserSize() {
		int windowWidth = UI.getCurrent().getPage().getBrowserWindowWidth();
		int windowHeight = UI.getCurrent().getPage().getBrowserWindowHeight();
		width = windowWidth - 30;
		height = (float) (windowHeight - (0.1*windowHeight) - 30);
		centreWidth = windowWidth/2;
		centreHeight = windowHeight/2;
	}

	

}
