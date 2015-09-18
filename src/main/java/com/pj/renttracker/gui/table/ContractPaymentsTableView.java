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
		TableColumn<ContractPayment, String> tenantColumn = new TableColumn<>("Payment Date");
		tenantColumn.setCellValueFactory(new StringCellValueFactory<ContractPayment>() {

			@Override
			protected String getValue(ContractPayment item) {
				return FormatterUtil.formatDate(item.getPaymentDate());
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

		getColumns().add(tenantColumn);
		getColumns().add(amountColumn);
		getColumns().add(remarksColumn);
	}
	
}
