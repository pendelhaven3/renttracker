package com.pj.renttracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.pj.renttracker.gui.component.DoubleClickEventHandler;
import com.pj.renttracker.model.Unit;
import com.pj.renttracker.service.UnitService;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UnitListController extends AbstractController {

	@Autowired private UnitService unitService;
	
	@FXML private TableView<Unit> unitsTable;
	
	@Override
	public void updateDisplay() {
		stageController.setTitle("Unit List");
		unitsTable.setItems(FXCollections.observableList(unitService.getAllUnits()));
		
		unitsTable.setOnMouseClicked(new DoubleClickEventHandler() {
			
			@Override
			protected void onDoubleClick(MouseEvent event) {
				if (!unitsTable.getSelectionModel().isEmpty()) {
					stageController.showUpdateUnitScreen(
							unitsTable.getSelectionModel().getSelectedItem());
				}
			}
		});
	}

	@FXML public void doOnBack() {
		stageController.showMainMenuScreen();
	}

	@FXML public void addUnit() {
		stageController.showAddUnitScreen();
	}

}
