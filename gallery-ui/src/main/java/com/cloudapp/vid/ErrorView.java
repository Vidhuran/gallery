package com.cloudapp.vid;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;

public class ErrorView extends VerticalLayout implements View {

	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show("INFO", "Please select a location to view photos.", Type.HUMANIZED_MESSAGE);
	}

}
