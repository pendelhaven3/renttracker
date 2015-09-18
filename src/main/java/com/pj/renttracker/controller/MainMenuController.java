package com.pj.renttracker.controller;

import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;
import javafx.fxml.FXML;

@Controller
public class MainMenuController extends AbstractController {

	private ResourceBundle bundle = ResourceBundle.getBundle("application");
	
	@Override
	public void updateDisplay() {
		stageController.setTitle(bundle.getString("application.title"));
	}

	@FXML public void goToTenantListScreen() {
		stageController.showTenantListScreen();
	}

	@FXML public void goToUnitListScreen() {
		stageController.showUnitListScreen();
	}

	@FXML public void goToContractListScreen() {
		stageController.showContractListScreen();
	}

}
