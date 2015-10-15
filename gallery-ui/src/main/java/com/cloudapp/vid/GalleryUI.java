package com.cloudapp.vid;

import javax.servlet.annotation.WebServlet;

import com.cloudapp.vid.content.ContentView;
import com.cloudapp.vid.mosaic.MosaicLayout;
import com.cloudapp.vid.utils.JSONUtils;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

/**
 *
 */
@Theme("gallery")
@Title("Vidhuran's Gallery")
@Widgetset("com.cloudapp.vid.fancymedia.GalleryWidgetset")
public class GalleryUI extends UI {
	
	@Override
	protected void init(VaadinRequest request) {
		ContentView root = new ContentView();
		setContent(root);
		Navigator navigator = new Navigator(this, root.getContentArea());
		addViewsToNavigator(navigator);
		navigator.setErrorView(ErrorView.class);
	}

		
	private void addViewsToNavigator(Navigator navigator) {
		JSONUtils.getAllViews().forEach((view) -> {
			navigator.addView(view, MosaicLayout.class);
		});	
	}


	@WebServlet(urlPatterns = "/*", name = "GalleryUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = GalleryUI.class, productionMode = false)
    public static class GalleryUIServlet extends VaadinServlet {
    }
	
}
