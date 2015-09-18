package com.pj.renttracker.gui.component;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public abstract class DoubleClickEventHandler implements EventHandler<MouseEvent> {

	@Override
	public void handle(MouseEvent event) {
		if (event.getClickCount() == 2) {
			onDoubleClick(event);
		}
	}

	protected abstract void onDoubleClick(MouseEvent event);

}
