package com.pj.renttracker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.pj.renttracker.Parameter;
import com.pj.renttracker.gui.component.ShowDialog;
import com.pj.renttracker.model.Location;
import com.pj.renttracker.service.LocationService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LocationController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(LocationController.class);
	
	@Autowired private LocationService locationService;
	
	@FXML private TextField nameField;
	@FXML private Button deleteButton;
	
	@Parameter private Location location;
	
	@Override
	public void updateDisplay() {
		if (location != null) {
			stageController.setTitle("Update Location");
			location = locationService.getLocation(location.getId());
			nameField.setText(location.getName());
			deleteButton.setDisable(false);
		} else {
			stageController.setTitle("Add New Location");
		}
		nameField.requestFocus();
	}

	@FXML public void doOnBack() {
		stageController.showLocationListScreen();
	}

	@FXML public void deleteLocation() {
		ShowDialog.info("Feature coming soon!");
		// TODO: Implement this
	}

	@FXML public void saveLocation() {
		if (!validateFields()) {
			return;
		}
		
		if (location == null) {
			location = new Location();
		}
		location.setName(nameField.getText());
		
		try {
			locationService.save(location);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ShowDialog.unexpectedError();
			return;
		}
		
		ShowDialog.info("Location saved");
		stageController.showLocationListScreen();
	}

	private boolean validateFields() {
		if (nameField.getText().isEmpty()) {
			ShowDialog.error("Name must be specified");
			nameField.requestFocus();
			return false;
		}
		
		// TODO: Check for duplicate name!
		
		return true;
	}

}
