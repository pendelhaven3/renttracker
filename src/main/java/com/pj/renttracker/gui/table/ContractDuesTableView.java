package com.pj.renttracker.gui.table;

import com.pj.renttracker.gui.component.StringCellValueFactory;
import com.pj.renttracker.model.ContractDue;
import com.pj.renttracker.util.FormatterUtil;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ContractDuesTableView extends TableView<ContractDue> {

	public ContractDuesTableView() {
		initializeColumns();
	}

	private void initializeColumns() {
		TableColumn<ContractDue, String> dueDateColumn = new TableColumn<>("Due Date");
		dueDateColumn.setCellValueFactory(new StringCellValueFactory<ContractDue>() {

			@Override
			protected String getValue(ContractDue item) {
				return FormatterUtil.formatDate(item.getDueDate());
			}
		});

		TableColumn<ContractDue, String> amountColumn = new TableColumn<>("Amount");
		amountColumn.setCellValueFactory(new StringCellValueFactory<ContractDue>() {

			@Override
			protected String getValue(ContractDue item) {
				return FormatterUtil.formatAmount(item.getParent().getRentalAmount());
			}
		});
		amountColumn.getStyleClass().add("right");

		getColumns().add(dueDateColumn);
		getColumns().add(amountColumn);
	}
	
}
