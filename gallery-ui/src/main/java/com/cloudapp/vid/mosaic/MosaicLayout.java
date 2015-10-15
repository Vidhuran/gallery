package com.cloudapp.vid.mosaic;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.cloudapp.vid.content.DownloadButton;
import com.cloudapp.vid.mediacache.MediaCache;
import com.cloudapp.vid.utils.BrowserUtils;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.VerticalLayout;

public class MosaicLayout extends VerticalLayout implements View {

	public MosaicLayout() {
		super();
		setSizeFull();
	}

	private AbsoluteLayout mosaic;
	private DragAndDropWrapper mosaicWrap;
	private DownloadButton download;
	
	private static Logger LOG = Logger.getLogger(MosaicLayout.class.getName());
	
	private void construct(String folder) {
		buildLayout(folder);
		getThumbnails(folder);
		addDragAndDrop();
		addToLayout();
	}

	private void buildLayout(String folder) {
		mosaic = new AbsoluteLayout();
		download = new DownloadButton(folder);
	}
	
	private void getThumbnails(String folder) {
		MediaCache.populateMediaFiles(folder);
		List<Thumbnail> thumbs = IntStream.range(0, MediaCache.getCacheSize())
				.mapToObj(i -> buildThumbnail(i))
				.collect(Collectors.toList());

		populateLayout(thumbs);
	}
	
	private Thumbnail buildThumbnail(Integer i) {
		return new Thumbnail(i);
	}
	
	private void populateLayout(List<Thumbnail> thumbnails) {
		thumbnails.forEach((thumbs) -> {
			double left = Math.random()*(0.70 * BrowserUtils.getCentreWidth());
			double top = Math.random()*(0.50 * BrowserUtils.getCentreHeight());
			Random rand = new Random();
			left = (rand.nextBoolean() == true) ? left : -left;
			top = (rand.nextBoolean() == true) ? top : -top;
			String cssPosition = "left: " + (BrowserUtils.getCentreWidth() + left) + "px; top: " + (BrowserUtils.getCentreHeight() + top) + "px;";
			mosaic.addComponent(thumbs, cssPosition);
		});
	}
	
	private void addDragAndDrop() {
		
		mosaicWrap = new DragAndDropWrapper(mosaic);
		mosaicWrap.addStyleName("no-vertical-drag-hints");
		mosaicWrap.addStyleName("no-horizontal-drag-hints");
		mosaicWrap.addStyleName("no-box-drag-hints");
		mosaicWrap.setDropHandler(new DragAndDropHandler(mosaic));
		mosaicWrap.setSizeFull();
		for(Component c: mosaic) {
			((DragAndDropWrapper)c).setDropHandler(new DragAndDropHandler(mosaic));
		}
	}
	
	private void addToLayout() {
		addComponent(mosaicWrap);
		setComponentAlignment(mosaicWrap, Alignment.MIDDLE_CENTER);
		setExpandRatio(mosaicWrap, 1.0f);
		addComponent(download);
		setComponentAlignment(download, Alignment.BOTTOM_RIGHT);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		construct(event.getViewName());
	}
}
