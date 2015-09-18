package com.pj.renttracker.gui.table;

import com.pj.renttracker.gui.component.StringCellValueFactory;
import com.pj.renttracker.model.Contract;
import com.pj.renttracker.util.FormatterUtil;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ContractsTableView extends TableView<Contract> {

	public ContractsTableView() {
		initializeColumns();
	}

	private void initializeColumns() {
		TableColumn<Contract, String> tenantColumn = new TableColumn<>("Tenant");
		tenantColumn.setCellValueFactory(new StringCellValueFactory<Contract>() {

			@Override
			protected String getValue(Contract item) {
				return item.getTenant().getName();
			}
		});

		TableColumn<Contract, String> unitColumn = new TableColumn<>("Unit");
		unitColumn.setCellValueFactory(new StringCellValueFactory<Contract>() {

			@Override
			protected String getValue(Contract item) {
				return item.getUnit().getName();
			}
		});

		TableColumn<Contract, String> startDateColumn = new TableColumn<>("Start Date");
		startDateColumn.setCellValueFactory(new StringCellValueFactory<Contract>() {

			@Override
			protected String getValue(Contract item) {
				return FormatterUtil.formatDate(item.getStartDate());
			}
		});

		TableColumn<Contract, String> amountColumn = new TableColumn<>("Amount");
		amountColumn.setCellValueFactory(new StringCellValueFactory<Contract>() {

			@Override
			protected String getValue(Contract item) {
				return FormatterUtil.formatAmount(item.getRentalAmount());
			}
		});

		getColumns().add(tenantColumn);
		getColumns().add(unitColumn);
		getColumns().add(startDateColumn);
		getColumns().add(amountColumn);
	}
	
}
