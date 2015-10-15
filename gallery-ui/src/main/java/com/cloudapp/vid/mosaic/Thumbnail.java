package com.cloudapp.vid.mosaic;

import java.util.Random;

import com.cloudapp.vid.mediacache.MediaCache;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.Image;
import com.vaadin.ui.UI;

public class Thumbnail extends DragAndDropWrapper {

	private static float SCALE_FACTOR = 3.0f;
	
	public Thumbnail(Integer imageIndex) {
		CssLayout layout = initializeLayout();
		buildLayout(layout, imageIndex);
		setDragStartMode(DragStartMode.COMPONENT);
		setSizeUndefined();
		setCompositionRoot(layout);
	}
	
	private CssLayout initializeLayout() {
		return new CssLayout() {
			@Override
		    protected String getCss(Component c) {
		        if (c instanceof Image) {
		        	Random rand = new Random();
		            double rad = Math.random()*35;
		            rad = (rand.nextBoolean() == true) ? rad : -rad;
		            return "-moz-transform:rotate(" + rad + "deg);" + 
		            	   "-webkit-transform:rotate(" + rad + "deg);" + 
		            	   "-o-transform:rotate(" + rad + "deg);" + 
		            	   "-ms-transform:rotate(" + rad + "deg);" + 
		            	   "transform: rotate(" + rad + "deg);";
		        }
		        return null;
		    }
		};
	}

	private void buildLayout(CssLayout layout, Integer imageIndex) {
		layout.setSizeUndefined();
		ExternalResource res = new ExternalResource(MediaCache.getFileAtIndex(imageIndex));
		Image img = new Image(null, res);
		img.setStyleName("thumbnail");
		img.setWidth(160, Unit.PIXELS);
		img.setHeight(120, Unit.PIXELS);
		img.addClickListener((e) -> {
	        // Create a sub-window and set the content
	        ImageWindow subWindow = new ImageWindow(imageIndex);
	        UI.getCurrent().addWindow(subWindow);
		});
		layout.addComponent(img);
	}
}
