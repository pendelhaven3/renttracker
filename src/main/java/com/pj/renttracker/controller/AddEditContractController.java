package com.pj.renttracker.controller;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import com.pj.renttracker.Parameter;
import com.pj.renttracker.gui.component.AppDatePicker;
import com.pj.renttracker.gui.component.ShowDialog;
import com.pj.renttracker.model.Contract;
import com.pj.renttracker.model.Tenant;
import com.pj.renttracker.model.Unit;
import com.pj.renttracker.service.ContractService;
import com.pj.renttracker.service.TenantService;
import com.pj.renttracker.service.UnitService;
import com.pj.renttracker.util.DateUtil;
import com.pj.renttracker.util.FormatterUtil;
import com.pj.renttracker.util.NumberUtil;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AddEditContractController extends AbstractController {

	private static final int FIRST_DAY_OF_MONTH = 1;
	private static final int LAST_DAY_OF_MONTH = 31;

	private static final Logger logger = LoggerFactory.getLogger(AddEditContractController.class);
	
	@Autowired private TenantService tenantService;
	@Autowired private UnitService unitService;
	@Autowired private ContractService contractService;
	
	@FXML private ComboBox<Tenant> tenantComboBox;
	@FXML private ComboBox<Unit> unitComboBox;
	@FXML private TextField amountField;
	@FXML private TextField dueDateField;
	@FXML private AppDatePicker startDatePicker;
	@FXML private Button deleteButton;
	
	@Parameter private Contract contract;
	
	@Override
	public void updateDisplay() {
		tenantComboBox.setItems(FXCollections.observableList(tenantService.getAllTenants()));
		unitComboBox.setItems(FXCollections.observableList(unitService.getAllUnits()));
		
		if (contract != null) {
			stageController.setTitle("Update Contract");
			contract = contractService.getContract(contract.getId());
			tenantComboBox.setValue(contract.getTenant());
			unitComboBox.setValue(contract.getUnit());
			amountField.setText(FormatterUtil.formatAmount(contract.getRentalAmount()));
			dueDateField.setText(String.valueOf(contract.getDueDate()));
			startDatePicker.setValue(DateUtil.toLocalDate(contract.getStartDate()));
			deleteButton.setDisable(false);
		} else {
			stageController.setTitle("Add New Contract");
		}
		tenantComboBox.requestFocus();
	}

	@FXML public void doOnBack() {
		if (contract == null) {
			stageController.showContractListScreen();
		} else {
			stageController.showContractScreen(contract);
		}
	}

	@FXML public void deleteContract() {
		ShowDialog.info("Feature coming soon!");
		// TODO: Implement this
	}

	@FXML public void saveContract() {
		if (!validateFields()) {
			return;
		}
		
		if (contract == null) {
			contract = new Contract();
		}
		contract.setTenant(tenantComboBox.getValue());
		contract.setUnit(unitComboBox.getValue());
		contract.setRentalAmount(NumberUtil.toBigDecimal(amountField.getText()));
		contract.setDueDate(Integer.valueOf(dueDateField.getText()));
		contract.setStartDate(DateUtil.toDate(startDatePicker.getValue()));
		
		try {
			contractService.save(contract);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ShowDialog.unexpectedError();
			return;
		}
		
		ShowDialog.info("Contract saved");
		stageController.showContractScreen(contract);
	}

	private boolean validateFields() {
		if (tenantComboBox.getValue() == null) {
			ShowDialog.error("Tenant must be specified");
			tenantComboBox.requestFocus();
			return false;
		}

		if (unitComboBox.getValue() == null) {
			ShowDialog.error("Unit must be specified");
			unitComboBox.requestFocus();
			return false;
		}
		
		if (StringUtils.isEmpty(amountField.getText())) { 
			ShowDialog.error("Amount must be specified");
			amountField.requestFocus();
			return false;
		}
		
		if (!NumberUtil.isAmount(amountField.getText())) { 
			ShowDialog.error("Amount must be a valid amount");
			amountField.requestFocus();
			return false;
		}

		if (StringUtils.isEmpty(dueDateField.getText())) { 
			ShowDialog.error("Due Date must be specified");
			dueDateField.requestFocus();
			return false;
		}
		
		if (!NumberUtils.isDigits(dueDateField.getText())) { 
			ShowDialog.error("Due Date must be a valid number");
			dueDateField.requestFocus();
			return false;
		}
		
		if (!isValidDueDate(dueDateField.getText())) { 
			ShowDialog.error("Due Date must be between 1 and 31");
			dueDateField.requestFocus();
			return false;
		}

		if (startDatePicker.getValue() == null) {
			ShowDialog.error("Start Date must be specified");
			startDatePicker.requestFocus();
			return false;
		}
		
		// TODO: Check if unit is available
		
		return true;
	}

	private boolean isValidDueDate(String dueDateString) {
		int dueDate = Integer.parseInt(dueDateString);
		return dueDate >= FIRST_DAY_OF_MONTH && dueDate <= LAST_DAY_OF_MONTH;
	}

}
