package com.pj.renttracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.pj.renttracker.gui.component.DoubleClickEventHandler;
import com.pj.renttracker.model.Contract;
import com.pj.renttracker.service.ContractService;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ContractListController extends AbstractController {

	@Autowired private ContractService contractService;
	
	@FXML private TableView<Contract> contractsTable;
	
	@Override
	public void updateDisplay() {
		stageController.setTitle("Contract List");
		contractsTable.setItems(FXCollections.observableList(contractService.getAllContracts()));
		
		contractsTable.setOnMouseClicked(new DoubleClickEventHandler() {
			
			@Override
			protected void onDoubleClick(MouseEvent event) {
				if (!contractsTable.getSelectionModel().isEmpty()) {
					stageController.showUpdateContractScreen(
							contractsTable.getSelectionModel().getSelectedItem());
				}
			}
		});
	}

	@FXML public void doOnBack() {
		stageController.showMainMenuScreen();
	}

	@FXML public void addContract() {
		stageController.showAddContractScreen();
	}

}
