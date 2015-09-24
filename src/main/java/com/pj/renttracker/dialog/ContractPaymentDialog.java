package com.pj.renttracker.dialog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.pj.renttracker.Parameter;
import com.pj.renttracker.gui.component.ShowDialog;
import com.pj.renttracker.model.Contract;
import com.pj.renttracker.model.ContractPayment;
import com.pj.renttracker.model.PaymentType;
import com.pj.renttracker.service.ContractService;
import com.pj.renttracker.util.DateUtil;
import com.pj.renttracker.util.FormatterUtil;
import com.pj.renttracker.util.NumberUtil;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

@Component
public class ContractPaymentDialog extends AbstractDialog {

	private static final Logger logger = LoggerFactory.getLogger(ContractPaymentDialog.class);
	
	@Autowired private ContractService contractService;
	
	@FXML private DatePicker paymentDatePicker;
	@FXML private TextField amountField;
	@FXML private ComboBox<PaymentType> paymentTypeComboBox;
	@FXML private TextField remarksField;
	
	@Parameter private Contract contract;
	@Parameter private ContractPayment payment;
	
	@Override
	public void updateDisplay() {
		paymentTypeComboBox.getItems().addAll(PaymentType.values());
		
		if (payment != null) {
			paymentDatePicker.setValue(DateUtil.toLocalDate(payment.getPaymentDate()));
			amountField.setText(FormatterUtil.formatAmount(payment.getAmount()));
			paymentTypeComboBox.setValue(payment.getPaymentType());
			remarksField.setText(payment.getRemarks());
		}
	}
	
	@FXML public void saveContractPayment() {
		if (!validateFields()) {
			return;
		}
		
		if (payment == null) {
			payment = new ContractPayment();
			payment.setParent(contract);
		}
		payment.setPaymentDate(DateUtil.toDate(paymentDatePicker.getValue()));
		if (!StringUtils.isEmpty(amountField.getText())) {
			payment.setAmount(NumberUtil.toBigDecimal(amountField.getText().trim()));
		} else {
			payment.setAmount(null);
		}
		payment.setPaymentType(paymentTypeComboBox.getValue());
		if (!StringUtils.isEmpty(remarksField.getText())) {
			payment.setRemarks(remarksField.getText().trim());
		} else {
			payment.setRemarks(null);
		}
		
		try {
			contractService.save(payment);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ShowDialog.unexpectedError();
			return;
		}
		
		ShowDialog.info("Contract Payment saved");
		hide();
	}
	
	private boolean validateFields() {
		if (paymentDatePicker.getValue() == null) {
			ShowDialog.error("Payment Date must be specified");
			paymentDatePicker.requestFocus();
			return false;
		}
		
		if (amountField.getText().isEmpty()) {
			ShowDialog.error("Amount must be specified");
			amountField.requestFocus();
			return false;
		}
		
		if (!NumberUtil.isAmount(amountField.getText())) {
			ShowDialog.error("Amount must be a valid amount");
			amountField.requestFocus();
			return false;
		}
		
		if (paymentTypeComboBox.getValue() == null) {
			ShowDialog.error("Payment Type must be specified");
			paymentTypeComboBox.requestFocus();
			return false;
		}
		
		return true;
	}

	@FXML public void cancel() {
		hide();
	}

	@Override
	protected String getDialogTitle() {
		if (contract != null) {
			return "Add Contract Payment";
		} else {
			return "Edit Contract Payment";
		}
	}

	@Override
	protected String getSceneName() {
		return "contractPayment";
	}

}