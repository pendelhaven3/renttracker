package com.pj.renttracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.pj.renttracker.gui.component.DoubleClickEventHandler;
import com.pj.renttracker.model.Tenant;
import com.pj.renttracker.service.TenantService;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class TenantListController extends AbstractController {

	@Autowired private TenantService tenantService;
	
	@FXML private TableView<Tenant> tenantsTable;
	
	@Override
	public void updateDisplay() {
		stageController.setTitle("Tenant List");
		tenantsTable.setItems(FXCollections.observableList(tenantService.getAllTenants()));
		
		tenantsTable.setOnMouseClicked(new DoubleClickEventHandler() {
			
			@Override
			protected void onDoubleClick(MouseEvent event) {
				if (!tenantsTable.getSelectionModel().isEmpty()) {
					stageController.showUpdateTenantScreen(
							tenantsTable.getSelectionModel().getSelectedItem());
				}
			}
		});
	}

	@FXML public void doOnBack() {
		stageController.showMainMenuScreen();
	}

	@FXML public void addTenant() {
		stageController.showAddTenantScreen();
	}

}
