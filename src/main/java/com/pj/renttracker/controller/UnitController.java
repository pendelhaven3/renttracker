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
import com.pj.renttracker.model.Unit;
import com.pj.renttracker.service.LocationService;
import com.pj.renttracker.service.UnitService;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UnitController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(UnitController.class);
	
	@Autowired private UnitService unitService;
	@Autowired private LocationService locationService;
	
	@FXML private TextField nameField;
	@FXML private ComboBox<Location> locationComboBox;
	@FXML private Button deleteButton;
	
	@Parameter private Unit unit;
	
	@Override
	public void updateDisplay() {
		locationComboBox.setItems(FXCollections.observableList(locationService.getAllLocations()));
		if (unit != null) {
			stageController.setTitle("Update Unit");
			unit = unitService.getUnit(unit.getId());
			nameField.setText(unit.getName());
			locationComboBox.setValue(unit.getLocation());
			deleteButton.setDisable(false);
		} else {
			stageController.setTitle("Add New Unit");
		}
		nameField.requestFocus();
	}

	@FXML public void doOnBack() {
		stageController.showUnitListScreen();
	}

	@FXML public void deleteUnit() {
		ShowDialog.info("Feature coming soon!");
		// TODO: Implement this
	}

	@FXML public void saveUnit() {
		if (!validateFields()) {
			return;
		}
		
		if (unit == null) {
			unit = new Unit();
		}
		unit.setName(nameField.getText());
		unit.setLocation(locationComboBox.getValue());
		
		try {
			unitService.save(unit);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ShowDialog.unexpectedError();
			return;
		}
		
		ShowDialog.info("Unit saved");
		stageController.showUnitListScreen();
	}

	private boolean validateFields() {
		if (nameField.getText().isEmpty()) {
			ShowDialog.error("Name must be specified");
			nameField.requestFocus();
			return false;
		}
		
		// TODO: Check for duplicate name!
		
		if (locationComboBox.getValue() == null) {
			ShowDialog.error("Location must be specified");
			locationComboBox.requestFocus();
			return false;
		}
		
		return true;
	}

}
