package com.pj.renttracker.gui.component;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class ShowDialog {

	private static final String UNEXPECTED_ERROR_MESSAGE = "Unexpected error occurred";
	
	public static boolean confirm(String message) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setContentText(message);
		alert.setHeaderText(null);
		
		Optional<ButtonType> result = alert.showAndWait();
		return result.isPresent() && result.get() == ButtonType.OK;
	}
	
	public static void error(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setContentText(message);
		alert.setHeaderText(null);
		alert.showAndWait();
	}

	public static void info(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText(message);
		alert.setHeaderText(null);
		alert.showAndWait();
	}
	
	public static void unexpectedError() {
		error(UNEXPECTED_ERROR_MESSAGE);
	}
	
}