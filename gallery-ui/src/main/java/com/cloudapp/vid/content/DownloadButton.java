package com.cloudapp.vid.content;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import com.cloudapp.vid.utils.DownloadHelper;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.ui.Button;
import com.vaadin.ui.themes.ValoTheme;

public class DownloadButton extends Button {
	
	public DownloadButton() {
		super();
	}

	public DownloadButton(String location) {
		init();
		bindDownloader(location);
	}
	
	private void init() {
		addStyleName(ValoTheme.BUTTON_ICON_ONLY);
		addStyleName("icon-buttons");
		setIcon(FontAwesome.DOWNLOAD);
		setDescription("Downlaod all images as a zip file");		
	}

	private void bindDownloader(String location) {
		new FileDownloader(createResource(location)).extend(this);
	}

	private StreamResource createResource(String location) {
        return new StreamResource(new StreamSource() {
            @Override
            public InputStream getStream() {
        			ByteArrayOutputStream bos = DownloadHelper.getFileStream(location);
        			return new ByteArrayInputStream(bos.toByteArray());
        		} 
        }, location + ".zip");
    }
}
