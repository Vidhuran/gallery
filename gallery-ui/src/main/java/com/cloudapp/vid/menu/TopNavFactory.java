package com.cloudapp.vid.menu;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.cloudapp.vid.utils.FileUtils;
import com.cloudapp.vid.utils.JSONUtils;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;

public class TopNavFactory extends HorizontalLayout {
	
	public TopNavFactory() {
		createMenu();
	}
	
	private void createMenu() {
		
		addStyleName("top-menu");
		setHeight(50, Unit.PIXELS);
		setWidth(100, Unit.PERCENTAGE);
		
		MenuBar menu = new MenuBar();
        menu.addStyleName("borderless");
        menu.setAutoOpen(true);
        menu.setWidth(70,  Unit.PERCENTAGE);
        
        addComponent(menu);
        setComponentAlignment(menu, Alignment.MIDDLE_CENTER);
        
        addMenuItems(menu);
	}

	@SuppressWarnings("unchecked")
	private void addMenuItems(MenuBar menu) {
		
		JSONArray countries = JSONUtils.getArrayWithKey(null, "countries");
		
		countries.forEach((country) -> {
			JSONObject temp = (JSONObject)country;
			String name = JSONUtils.getStringWithKey(temp, "name");
			String flag = JSONUtils.getStringWithKey(temp, "flag");
			ExternalResource resource = new ExternalResource(FileUtils.getWebRoot() + "flags/" + flag + ".gif");
			MenuItem item = menu.addItem(name, resource, null);
			populateSubMenu(item, temp);
		});
	}

	@SuppressWarnings("unchecked")
	private void populateSubMenu(MenuItem item, JSONObject country) {
		JSONArray cities = JSONUtils.getArrayWithKey(country, "cities");
		cities.forEach((city) -> {
			JSONObject temp = (JSONObject)city;
			String displayName = JSONUtils.getStringWithKey(temp, "displayName");
			String simpleName = JSONUtils.getStringWithKey(temp, "simpleName");
			item.addItem(displayName, (event) -> {
				UI.getCurrent().getNavigator().navigateTo(simpleName);
			});	
		});
	}

}
