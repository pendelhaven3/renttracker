package com.pj.renttracker.gui.table;

import com.pj.renttracker.gui.component.StringCellValueFactory;
import com.pj.renttracker.model.ContractPayment;
import com.pj.renttracker.util.FormatterUtil;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ContractPaymentsTableView extends TableView<ContractPayment> {

	public ContractPaymentsTableView() {
		initializeColumns();
	}

	private void initializeColumns() {
		TableColumn<ContractPayment, String> paymentDateColumn = new TableColumn<>("Payment Date");
		paymentDateColumn.setCellValueFactory(new StringCellValueFactory<ContractPayment>() {

			@Override
			protected String getValue(ContractPayment item) {
				return FormatterUtil.formatDate(item.getPaymentDate());
			}
		});

		TableColumn<ContractPayment, String> paymentTypeColumn = new TableColumn<>("Payment Type");
		paymentTypeColumn.setCellValueFactory(new StringCellValueFactory<ContractPayment>() {

			@Override
			protected String getValue(ContractPayment item) {
				return item.getPaymentType().toString();
			}
		});

		TableColumn<ContractPayment, String> amountColumn = new TableColumn<>("Amount");
		amountColumn.setCellValueFactory(new StringCellValueFactory<ContractPayment>() {

			@Override
			protected String getValue(ContractPayment item) {
				return FormatterUtil.formatAmount(item.getAmount());
			}
		});
		amountColumn.getStyleClass().add("right");

		TableColumn<ContractPayment, String> remarksColumn = new TableColumn<>("Remarks");
		remarksColumn.setCellValueFactory(new StringCellValueFactory<ContractPayment>() {

			@Override
			protected String getValue(ContractPayment item) {
				return item.getRemarks();
			}
		});

		getColumns().add(paymentDateColumn);
		getColumns().add(paymentTypeColumn);
		getColumns().add(amountColumn);
		getColumns().add(remarksColumn);
	}
	
}
