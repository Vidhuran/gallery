package com.cloudapp.vid.content;

import com.cloudapp.vid.menu.TopNavFactory;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Notification.Type;

public class ContentView extends VerticalLayout implements View {

	VerticalLayout content;
	
	public ContentView() {
		initView();
		buildTopMenu();
		buildContent();
	}
	
	private void buildContent() { 
		content = new VerticalLayout();
		content.setSizeFull();
		addComponent(content);
		setExpandRatio(content, 1.0f);
	}

	private void initView() {
		setSpacing(true);
		setSizeFull();
	}

	private void buildTopMenu() {
		TopNavFactory topNav = new TopNavFactory();
		addComponent(topNav);
		setComponentAlignment(topNav, Alignment.TOP_CENTER);
	}

	public VerticalLayout getContentArea() {
		if(content == null) {
			buildContent();
		}
		return content;
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}
	
}
