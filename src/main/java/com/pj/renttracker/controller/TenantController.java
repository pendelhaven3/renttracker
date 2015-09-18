package com.pj.renttracker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.pj.renttracker.Parameter;
import com.pj.renttracker.gui.component.ShowDialog;
import com.pj.renttracker.model.Tenant;
import com.pj.renttracker.service.TenantService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class TenantController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(TenantController.class);
	
	@Autowired private TenantService tenantService;
	
	@FXML private TextField nameField;
	@FXML private Button deleteButton;
	
	@Parameter private Tenant tenant;
	
	@Override
	public void updateDisplay() {
		if (tenant != null) {
			stageController.setTitle("Update Tenant");
			tenant = tenantService.getTenant(tenant.getId());
			nameField.setText(tenant.getName());
			deleteButton.setDisable(false);
		} else {
			stageController.setTitle("Add New Tenant");
		}
		nameField.requestFocus();
	}

	@FXML public void doOnBack() {
		stageController.showTenantListScreen();
	}

	@FXML public void deleteTenant() {
		ShowDialog.info("Feature coming soon!");
		// TODO: Implement this
	}

	@FXML public void saveTenant() {
		if (!validateFields()) {
			return;
		}
		
		if (tenant == null) {
			tenant = new Tenant();
		}
		tenant.setName(nameField.getText());
		
		try {
			tenantService.save(tenant);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ShowDialog.unexpectedError();
			return;
		}
		
		ShowDialog.info("Tenant saved");
		stageController.showTenantListScreen();
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
