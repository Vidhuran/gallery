package com.cloudapp.vid.fancymedia.client.connector;


import java.util.List;
import java.util.logging.Logger;

import com.cloudapp.vid.fancymedia.FancyImage;
import com.cloudapp.vid.fancymedia.client.FancyImageWidget;
import com.cloudapp.vid.fancymedia.client.shared.FancyImageState;
import com.cloudapp.vid.fancymedia.client.shared.RotateDirection;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.communication.URLReference;
import com.vaadin.shared.ui.Connect;

// Connector binds client-side widget class to server-side component class
// Connector lives in the client and the @Connect annotation specifies the
// corresponding server-side component
@Connect(FancyImage.class)
public class FancyImageConnector extends AbstractComponentConnector {

	// ServerRpc is used to send events to server. Communication implementation
	// is automatically created here
	// FancyImageServerRpc rpc = RpcProxy.create(FancyImageServerRpc.class, this);

	private final FancyImageClientRpc rpc = new FancyImageClientRpc() {
        @Override
        public void showImage(String key) {
            URLReference url = getState().resources.get(key);
            if (url != null) {
                getWidget().showImage(url.getURL());
            } else {
                Logger.getLogger(
                        FancyImageConnector.this.getClass().getSimpleName())
                        .severe("Resource not found!");
            }
        }
    };

	@Override
    public void init() {
        super.init();
        registerRpc(FancyImageClientRpc.class, rpc);
    }
	
	// We must implement createWidget() to create correct type of widget
	@Override
	protected Widget createWidget() {
		return GWT.create(FancyImageWidget.class);
	}

	
	// We must implement getWidget() to cast to correct type
	@Override
	public FancyImageWidget getWidget() {
		return (FancyImageWidget) super.getWidget();
	}

	// We must implement getState() to cast to correct type
	@Override
	public FancyImageState getState() {
		return (FancyImageState) super.getState();
	}

	// Whenever the state changes in the server-side, this method is called
	@Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
		super.onStateChanged(stateChangeEvent);

        List<String> ids = getState().imageResIds;
        getWidget().trimImages(ids.size());

        for (int i = 0; i < ids.size(); ++i) {
            String key = ids.get(i);
            getWidget().setImage(getState().resources.get(key).getURL(), i);
        }

        getWidget().setAutoBrowseTimeout(getState().timeoutMs);
        getWidget().setAutoBrowseEnabled(getState().autoBrowse);
        getWidget().setFadeImages(getState().fadeTransition);
        getWidget().setRotateImages(
                getState().rotateTransition != RotateDirection.NONE,
                getState().rotateTransition == RotateDirection.HORIZONTAL);
    }

}
