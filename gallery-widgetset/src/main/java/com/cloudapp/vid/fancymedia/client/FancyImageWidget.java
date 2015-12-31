package com.cloudapp.vid.fancymedia.client;

import java.util.ArrayList;
import java.util.List;

import com.cloudapp.vid.fancymedia.client.model.BrowserMode;
import com.cloudapp.vid.fancymedia.client.model.ElementStyler;
import com.cloudapp.vid.fancymedia.client.model.ElementStyler.Value;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
 
// Extend any GWT Widget
public class FancyImageWidget extends Widget implements MouseOverHandler, MouseOutHandler {

	private final List<ImageElement> images = new ArrayList<ImageElement>();
    private int currentIndex = 0;
    private Timer autoBrowseTimer;
    private int autoBrowseTimeoutMs = 3000;

    protected final static String ROTATE_VALUE_VISIBLE = "rotateX(0deg)";
    protected final static String ROTATE_VALUE_HIDDEN = "rotateX(180deg)";
    protected final static String ROTATE_VALUE_DISABLED = "none";
    
    protected final static int HEADER_HEIGHT = 36;
    protected final static int SIDE_COMPONENT_WIDTH = 59;

    protected static BrowserMode browserMode;

    public final static String CLASS_NAME = "fancy-image";

    protected final ElementStyler styler = new ElementStyler();
    
    private final DivElement bottomWrapper, topWrapper;

    native void consoleLog(final String message) 
    /*-{
    console.log( message );
    }-*/;
    
    public FancyImageWidget() {
        DivElement root = Document.get().createDivElement();
        root.getStyle().setPosition(Position.RELATIVE);
        root.addClassName(CLASS_NAME);
        
        this.setElement(root);
        
        topWrapper = Document.get().createDivElement();
        bottomWrapper = Document.get().createDivElement();
        
        topWrapper.getStyle().setWidth(100, Unit.PCT);
        topWrapper.getStyle().setHeight(50, Unit.PX);
        topWrapper.getStyle().setBackgroundColor("#808080");
        topWrapper.getStyle().setOpacity(0.5);
        topWrapper.getStyle().setZIndex(9999);
        topWrapper.getStyle().setPosition(Position.ABSOLUTE);
        
        bottomWrapper.getStyle().setWidth(100, Unit.PCT);
        bottomWrapper.getStyle().setHeight(50, Unit.PX);
        bottomWrapper.getStyle().setBackgroundColor("#808080");
        bottomWrapper.getStyle().setOpacity(0.5);
        bottomWrapper.getStyle().setZIndex(9999);
        bottomWrapper.getStyle().setPosition(Position.ABSOLUTE);
        
        root.appendChild(topWrapper);
        root.appendChild(bottomWrapper);
        
        Window.addResizeHandler(new ResizeHandler() {

        	Timer resizeTimer = new Timer() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
				}
        		
        	};
        	
			@Override
			public void onResize(ResizeEvent event) {
				resizeTimer.schedule(250);
			}
        });
        
        
    }

    private void stopAutoBrowseTimer() {
        if (autoBrowseTimer != null) {
            getAutoBrowseTimer().cancel();
            autoBrowseTimer = null;
        }
    }

    private Timer getAutoBrowseTimer() {
        if (autoBrowseTimer == null) {
            autoBrowseTimer = new Timer() {

                @Override
                public void run() {
                    if (FancyImageWidget.this.isVisible()) {
                        showNextImage();
                    }
                }

            };
        }

        return autoBrowseTimer;
    }

    public void setAutoBrowseEnabled(boolean on) {
        if (on) {
            getAutoBrowseTimer().scheduleRepeating(autoBrowseTimeoutMs);
            getAutoBrowseTimer().run();
        } else {
            stopAutoBrowseTimer();
        }
    }

    public void setAutoBrowseTimeout(int millis) {
        if (millis > 0 && autoBrowseTimeoutMs != millis) {
            autoBrowseTimeoutMs = millis;
            getAutoBrowseTimer().scheduleRepeating(autoBrowseTimeoutMs);
        }
    }

    private ImageElement createImageElement(String url) {
        ImageElement image = Document.get().createImageElement();
        image.setSrc(url);
        // Place image in center
        int left = (getElement().getClientWidth() - image.getWidth())/2 - SIDE_COMPONENT_WIDTH;
        int top = (getElement().getClientHeight() - HEADER_HEIGHT - image.getHeight())/2;
        image.getStyle().setLeft(left, Unit.PX);
        image.getStyle().setTop(top, Unit.PX);
        styler.styleElementOut(image);
        
        // Move it later
        consoleLog("Root client height : " + getElement().getClientHeight());
        consoleLog("Root offset height : " + getElement().getOffsetHeight());
        consoleLog("Root scroll height : " + getElement().getScrollHeight());
        consoleLog("Root style height : " + getElement().getStyle().getHeight());
        
        bottomWrapper.getStyle().setTop(getElement().getClientHeight() - 50, Unit.PX);
        
        return image;
    }

    public void setImage(String url, int index) {
        if (index >= images.size()) {
            addImage(url);
        } else {
            ImageElement replaced = images.get(index);
            if (!replaced.getSrc().equals(url)) {
                replaced.setSrc(url);
            }
        }
    }

    public int addImage(String url) {
        ImageElement image = createImageElement(url);
        images.add(image);
        getElement().appendChild(image);

        if (images.size() == 1) {
            showImage(0);
        }

        applyTransitionStyleNames(image, false);
        return images.size();
    }

    public void trimImages(int amount) {
        while (images.size() > amount) {
            removeImage(images.get(amount).getSrc());
        }

        if (currentIndex >= images.size()) {
            showImage(0);
        }
    }

    public void removeImage(String url) {
        for (ImageElement image : images) {
            if (image.getSrc().equals(url)) {
                getElement().removeChild(image);
                images.remove(image);
                break;
            }
        }
    }

    public void showImage(String url) {
        for (int i = 0; i < images.size(); ++i) {
            if (images.get(i).getSrc().endsWith(url)) {
                showImage(i);
                break;
            }
        }
    }

    public void showImage(int index) {

        if (images.isEmpty()) {
            return;
        }

        if (index >= images.size()) {
            index = 0;
        }

        if (index != currentIndex) {
            ImageElement prevImage = images.get(currentIndex);
            ImageElement currentImage = images.get(index);

            currentIndex = index;

            prevImage.getStyle().setDisplay(
                    styler.hasValues() ? Display.BLOCK : Display.NONE);
            currentImage.getStyle().setDisplay(Display.BLOCK);

            if (styler.hasValues()) {
                applyTransitionStyleNames(prevImage, true);
                applyTransitionStyleNames(currentImage, true);
            }
        }
    }

    private void showNextImage() {
        int nextIndex = currentIndex + 1;
        if (nextIndex >= images.size()) {
            nextIndex = 0;
        }

        showImage(nextIndex);
    }

    public void setFadeImages(boolean fade) {
        if (styler.isValueEnabled(Value.OPACITY) != fade) {
            styler.setValueEnabled(Value.OPACITY, fade);
            if (fade) {
                updateStylingOfImages();
            } else {
                for (ImageElement element : images) {
                    styler.removeStylingFromElement(element, Value.OPACITY);
                }
            }
        }
    }

    public void setRotateImages(boolean rotate, boolean horizontal) {
        if (rotate) {
            if (horizontal) {
                styler.setValueEnabled(Value.VERTICAL2_ROTATE, false);
                styler.setValueEnabled(Value.HORIZONTAL2_ROTATE, true);
            } else {
                styler.setValueEnabled(Value.HORIZONTAL2_ROTATE, false);
                styler.setValueEnabled(Value.VERTICAL2_ROTATE, true);
            }
            addStyleName(CLASS_NAME + "-rotate");
        } else {
            styler.setValueEnabled(Value.VERTICAL2_ROTATE, false);
            styler.setValueEnabled(Value.HORIZONTAL2_ROTATE, false);

            removeStyleName(CLASS_NAME + "-rotate");

            for (ImageElement element : images) {
                styler.removeStylingFromElement(element, Value.VERTICAL2_ROTATE);
            }
        }
    }

    protected static BrowserMode getBrowserMode() {
        if (browserMode == null) {
            browserMode = BrowserMode.resolve();
        }

        return browserMode;
    }

    protected void updateStylingOfImages() {
        for (ImageElement element : images) {
            applyTransitionStyleNames(element, false);
        }
    }

    protected void applyTransitionStyleNames(final ImageElement element,
            boolean animate) {

        boolean isCurrent = currentIndex == images.indexOf(element);

        if (isCurrent) {
            if (animate) {
                styler.styleElementIn(element);
                Scheduler.get().scheduleDeferred(new ScheduledCommand() {

                    @Override
                    public void execute() {
                        styler.styleElementOn(element);
                    }

                });
            } else {
                styler.styleElementOn(element);
            }
        } else {
            styler.styleElementOut(element);
        }

    }

	@Override
	public void onMouseOut(MouseOutEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseOver(MouseOverEvent event) {
		// TODO Auto-generated method stub
		
	}

}