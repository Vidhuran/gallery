package com.cloudapp.vid.mosaic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import com.cloudapp.vid.fancymedia.FancyImage;
import com.cloudapp.vid.mediacache.MediaCache;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Resource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class ImageWindow extends Window {

	private Integer CURR_INDEX = 0;
	private Integer LIST_SIZE = 0;
	
	public ImageWindow(Integer imageIndex) {
		
		CURR_INDEX = imageIndex;
		LIST_SIZE = MediaCache.getCacheSize();
		
		setCaption("Dummy Title");
		setWidth(80, Unit.PERCENTAGE);
		setHeight(80, Unit.PERCENTAGE);
		setModal(true);
		setClosable(true);
	    HorizontalLayout content = new HorizontalLayout();
	    content.setSpacing(true);
	    content.setSizeFull();
	    
	    Button prevImage = new Button();
	    prevImage.setCaption("❰");
	    prevImage.addStyleName(ValoTheme.BUTTON_LINK);
	    prevImage.addStyleName("icon-buttons");
	    prevImage.setHeight(100, Unit.PERCENTAGE);
	        
	    content.addComponent(prevImage);
	    
	    final List<Resource> imageResources = getImageResources();
	    		
	    FancyImage image = new FancyImage();
	    image.setSizeFull();
	    image.setFadeTransition(true);
	    image.setRotateTransition(false);
	    image.setSlideShowEnabled(false);
	    content.addComponent(image);
	    content.setComponentAlignment(image, Alignment.MIDDLE_CENTER);
	    image.showResource(imageResources.get(CURR_INDEX));
	    
	    Button nextImage = new Button();
	    nextImage.setCaption("❱");
	    nextImage.addStyleName(ValoTheme.BUTTON_LINK);
	    nextImage.addStyleName("icon-buttons");
	    nextImage.setHeight(100, Unit.PERCENTAGE);
	    setNextImageListeners(nextImage, image, imageResources);
	    setPrevImageListeners(prevImage, image, imageResources);
	    
	    content.addComponent(nextImage);
	    content.setComponentAlignment(prevImage, Alignment.MIDDLE_LEFT);
	    content.setComponentAlignment(nextImage, Alignment.MIDDLE_RIGHT);
	    content.setComponentAlignment(image, Alignment.MIDDLE_CENTER);
	    content.setExpandRatio(image, 1.0f);
	    setContent(content);
	    
	    center();		
	}

	private List<Resource> getImageResources() {
		return MediaCache.getImageList()
			.stream()
			.map(this::createFileResource)
			.collect(Collectors.toList());
	}

	private Resource createFileResource(String file) {
		return new ExternalResource(file);
	}
	
	private void setPrevImageListeners(Button prevImage, FancyImage image, List<Resource> imageResources) {
		prevImage.addClickListener((e) -> {
			CURR_INDEX = Math.floorMod((CURR_INDEX - 1),LIST_SIZE);
	    	image.showResource(imageResources.get(CURR_INDEX));
	    });
		
		addShortcutListener(new ShortcutListener("", KeyCode.ARROW_LEFT, null) {

			@Override
			public void handleAction(Object sender, Object target) {
				prevImage.click();
				
			}
			
		});
	}

	private void setNextImageListeners(Button nextImage, FancyImage image, List<Resource> imageResources) {
		nextImage.addClickListener((e) -> {
	    	CURR_INDEX = Math.floorMod((CURR_INDEX + 1),LIST_SIZE);
	    	image.showResource(imageResources.get(CURR_INDEX));
	    });
		
		addShortcutListener(new ShortcutListener("", KeyCode.ARROW_RIGHT, null) {

			@Override
			public void handleAction(Object sender, Object target) {
				nextImage.click();
			}
			
		});
		
	}
}
