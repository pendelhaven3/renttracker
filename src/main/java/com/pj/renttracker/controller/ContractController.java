package com.pj.renttracker.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.pj.renttracker.Parameter;
import com.pj.renttracker.dialog.ContractPaymentDialog;
import com.pj.renttracker.gui.component.DoubleClickEventHandler;
import com.pj.renttracker.gui.component.ShowDialog;
import com.pj.renttracker.model.Contract;
import com.pj.renttracker.model.ContractPayment;
import com.pj.renttracker.model.ContractRent;
import com.pj.renttracker.service.ContractService;
import com.pj.renttracker.util.FormatterUtil;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ContractController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(ContractController.class);
	
	@Autowired private ContractService contractService;
	@Autowired private ContractPaymentDialog contractPaymentDialog;
	
	@FXML private Label tenantLabel;
	@FXML private Label unitLabel;
	@FXML private Label amountLabel;
	@FXML private Label startDateLabel;
	@FXML private Label rentalDateLabel;
	@FXML private Label balanceLabel;
	@FXML private Label advanceLabel;
	@FXML private Label depositLabel;
	@FXML private TableView<ContractPayment> paymentsTable;
	@FXML private TableView<ContractRent> rentsTable;
	@FXML private Button deleteButton;
	
	@Parameter private Contract contract;
	
	@Override
	public void updateDisplay() {
		stageController.setTitle("Contract");
		contract = contractService.getContract(contract.getId());
		tenantLabel.setText(contract.getTenant().toString());
		unitLabel.setText(contract.getUnit().toString());
		amountLabel.setText(FormatterUtil.formatAmount(contract.getRentalAmount()));
		startDateLabel.setText(FormatterUtil.formatDate(contract.getStartDate()));
		rentalDateLabel.setText(String.valueOf(contract.getRentalDate()));
		balanceLabel.setText(FormatterUtil.formatAmount(contract.getTotalUnpaidRents()));
		advanceLabel.setText(FormatterUtil.formatAmount(contract.getTotalAdvance()));
		depositLabel.setText(FormatterUtil.formatAmount(contract.getTotalDeposit()));
		deleteButton.setDisable(false);
		
		paymentsTable.getItems().clear(); // workaround since setItems() does not refresh
		paymentsTable.getItems().addAll(contract.getPayments());
		
		rentsTable.getItems().clear(); // workaround since setItems() does not refresh
		rentsTable.getItems().addAll(contract.getRents());
		
		paymentsTable.setOnMouseClicked(new DoubleClickEventHandler() {
			
			@Override
			protected void onDoubleClick(MouseEvent event) {
				if (!paymentsTable.getSelectionModel().isEmpty()) {
					updateContractPayment();
				}
			}
		});
		
	}

	@FXML public void doOnBack() {
		stageController.showContractListScreen();
	}

	@FXML public void deleteContract() {
		ShowDialog.info("Feature coming soon!");
		// TODO: Implement this
	}

	@FXML public void updateContract() {
		stageController.showUpdateContractScreen(contract);
	}

	@FXML public void addContractPayment() {
		Map<String, Object> model = new HashMap<>();
		model.put("contract", contract);
		
		contractPaymentDialog.showAndWait(model);
		
		updateDisplay();
	}

	@FXML public void deleteContractPayment() {
		if (!isPaymentSelected()) {
			ShowDialog.error("No payment selected");
			return;
		}
		
		if (!ShowDialog.confirm("Delete payment?")) {
			return;
		}
		
		try {
			contractService.delete(paymentsTable.getSelectionModel().getSelectedItem());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ShowDialog.unexpectedError();
			return;
		}
		
		ShowDialog.info("Payment deleted");
		updateDisplay();
	}
	
	private boolean isPaymentSelected() {
		return !paymentsTable.getSelectionModel().isEmpty();
	}

	public void updateContractPayment() {
		Map<String, Object> model = new HashMap<>();
		model.put("payment", paymentsTable.getSelectionModel().getSelectedItem());
		
		contractPaymentDialog.showAndWait(model);
		
		updateDisplay();
	}

	@FXML public void markRentAsPaid() {
		if (!isRentSelected()) {
			ShowDialog.error("No rent selected");
			return;
		}
		
		if (ShowDialog.confirm("Mark selected rent as paid?")) {
			contractService.markRentAsPaid(getSelectedRent());
			updateDisplay();
		}
	}
	
	@FXML public void markRentAsUnpaid() {
		if (!isRentSelected()) {
			ShowDialog.error("No rent selected");
			return;
		}
		
		if (ShowDialog.confirm("Mark selected rent as paid?")) {
			contractService.markRentAsUnpaid(getSelectedRent());
			updateDisplay();
		}
	}

	private ContractRent getSelectedRent() {
		return rentsTable.getSelectionModel().getSelectedItem();
	}
	
	private boolean isRentSelected() {
		return !rentsTable.getSelectionModel().isEmpty();
	}
	
}
