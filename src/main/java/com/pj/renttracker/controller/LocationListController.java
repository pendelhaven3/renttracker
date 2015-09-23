package com.pj.renttracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.pj.renttracker.gui.component.DoubleClickEventHandler;
import com.pj.renttracker.model.Location;
import com.pj.renttracker.service.LocationService;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LocationListController extends AbstractController {

	@Autowired private LocationService locationService;
	
	@FXML private TableView<Location> locationsTable;
	
	@Override
	public void updateDisplay() {
		stageController.setTitle("Location List");
		locationsTable.setItems(FXCollections.observableList(locationService.getAllLocations()));
		
		locationsTable.setOnMouseClicked(new DoubleClickEventHandler() {
			
			@Override
			protected void onDoubleClick(MouseEvent event) {
				if (!locationsTable.getSelectionModel().isEmpty()) {
					stageController.showUpdateLocationScreen(
							locationsTable.getSelectionModel().getSelectedItem());
				}
			}
		});
	}

	@FXML public void doOnBack() {
		stageController.showMainMenuScreen();
	}

	@FXML public void addLocation() {
		stageController.showAddLocationScreen();
	}

}
