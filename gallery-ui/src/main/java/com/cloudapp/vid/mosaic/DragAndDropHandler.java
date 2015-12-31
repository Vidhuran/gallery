package com.cloudapp.vid.mosaic;

import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.AbsoluteLayout.ComponentPosition;
import com.vaadin.ui.DragAndDropWrapper.WrapperTargetDetails;
import com.vaadin.ui.DragAndDropWrapper.WrapperTransferable;

public class DragAndDropHandler implements DropHandler {

	private AbsoluteLayout mosaic;

	public DragAndDropHandler(AbsoluteLayout mosaic) {
		this.mosaic = mosaic;
	}

	@Override
	public void drop(DragAndDropEvent event) {
		WrapperTransferable t =
				(WrapperTransferable) event.getTransferable();
		WrapperTargetDetails details =
				(WrapperTargetDetails) event.getTargetDetails();
		// Calculate the drag coordinate difference
		int xChange = details.getMouseEvent().getClientX()
				- t.getMouseDownEvent().getClientX();
		int yChange = details.getMouseEvent().getClientY()
				- t.getMouseDownEvent().getClientY();
		// Move the component in the absolute layout
		ComponentPosition pos =
				mosaic.getPosition(t.getSourceComponent());
		pos.setLeftValue(pos.getLeftValue() + xChange);
		pos.setTopValue(pos.getTopValue() + yChange);
		int zIndex = (int)mosaic.getData();
		pos.setZIndex(++zIndex);
		mosaic.setData(zIndex);
		
	}

	@Override
	public AcceptCriterion getAcceptCriterion() {
		return AcceptAll.get();
	}

}
