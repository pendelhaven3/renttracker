package com.pj.renttracker.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.pj.renttracker.model.Contract;
import com.pj.renttracker.service.ContractService;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

@Controller
public class UpcomingRentsController extends AbstractController {

	@Autowired private ContractService contractService;
	
	@FXML private TableView<Contract> table;
	
	@Override
	public void updateDisplay() {
		stageController.setTitle("Upcoming Rents");
		table.setItems(FXCollections.observableList(getContractsWithUpcomingRent()));
	}

	private List<Contract> getContractsWithUpcomingRent() {
		List<Contract> contracts = contractService.findContractsWithUpcomingRent();
		Collections.sort(contracts, (o1, o2) -> o1.getNextRentalDate().compareTo(o2.getNextRentalDate()));
		return contracts;
	}

	@FXML public void doOnBack() {
		stageController.showMainMenuScreen();
	}
	
}
