package com.pj.renttracker.dialog;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;

import com.pj.renttracker.Parameter;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class AbstractDialog extends Stage {

	private static final int WIDTH = 600;
	private static final int HEIGHT = 400;
	
	public AbstractDialog() {
		setResizable(false);
		initModality(Modality.APPLICATION_MODAL);
	} 
	
	@Override
	public void showAndWait() {
		showAndWait(null);
	}

	public void showAndWait(Map<String, Object> model) {
		setScene(null);
		loadSceneFromFXML(model);
		setTitle(getDialogTitle());
		
		super.showAndWait();
	}
	
	protected abstract String getDialogTitle();

	private void loadSceneFromFXML(Map<String, Object> model) {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setController(this);
		
		Parent root = null;
		try {
			root = fxmlLoader.load(getClass().getResourceAsStream("/fxml/" + getSceneName() + ".fxml"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		Scene scene = new Scene(root, WIDTH, HEIGHT);
		scene.getStylesheets().add("css/application.css");
		setScene(scene);
	
		if (model == null) {
			model = Collections.emptyMap();
		}
		
		for (Field field : getClass().getDeclaredFields()) {
			if (field.getAnnotation(Parameter.class) != null) {
				field.setAccessible(true);
				try {
					if (model.containsKey(field.getName())) {
						field.set(this, model.get(field.getName()));
					} else {
						field.set(this, null);
					}
				} catch (Exception e) {
					System.out.println("Error setting parameter " + field.getName());
				}
			}
		}
		
		if (model != null && !model.isEmpty()) {
			for (String key : model.keySet()) {
				try {
					Field field = getClass().getDeclaredField(key);
					if (field != null && field.getAnnotation(Parameter.class) != null) {
						field.setAccessible(true);
						field.set(this, model.get(key));
					}
				} catch (Exception e) {
					System.out.println("Error setting parameter " + key);
				}
			}
		}
		
		updateDisplay();
	}

	protected abstract void updateDisplay();

	protected abstract String getSceneName();
	
}
