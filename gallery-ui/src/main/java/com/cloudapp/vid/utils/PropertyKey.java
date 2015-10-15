package com.cloudapp.vid.utils;

public enum PropertyKey {

	BaseDirectory("root.file.location"),
	DefaultLocation("default.selection.choice"),
	WebRoot("images.document.root");
	
	private String key;

    PropertyKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
	
}
