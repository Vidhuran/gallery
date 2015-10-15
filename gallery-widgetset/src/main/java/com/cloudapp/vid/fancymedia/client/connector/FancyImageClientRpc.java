package com.cloudapp.vid.fancymedia.client.connector;

import com.vaadin.shared.communication.ClientRpc;

// ClientRpc is used to pass events from server to client
// For sending information about the changes to component state, use State instead
public interface FancyImageClientRpc extends ClientRpc {

    public void showImage(String resId);

}