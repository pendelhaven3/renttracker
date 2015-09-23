package com.pj.renttracker.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.pj.renttracker.gui.component.StringCellValueFactory;
import com.pj.renttracker.model.Contract;
import com.pj.renttracker.service.ContractService;
import com.pj.renttracker.util.FormatterUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

@Controller
public class UpcomingRentalDuesController extends AbstractController {

	@Autowired private ContractService contractService;
	
	@FXML private TableView<Contract> table;
	
	@Override
	public void updateDisplay() {
		stageController.setTitle("Upcoming Rental Dues");
		initializeTable();
		table.setItems(FXCollections.observableList(getContractsWithUpcomingDues()));
	}

	private List<Contract> getContractsWithUpcomingDues() {
		List<Contract> contracts = contractService.findContractsWithUpcomingDues();
		Collections.sort(contracts, (o1, o2) -> o1.getNextRentalDate().compareTo(o2.getNextRentalDate()));
		return contracts;
	}

	private void initializeTable() {
		TableColumn<Contract, String> dueDateColumn = new TableColumn<>("Due Date");
		dueDateColumn.setCellValueFactory(new StringCellValueFactory<Contract>() {

			@Override
			protected String getValue(Contract item) {
				return FormatterUtil.formatDate(item.getNextRentalDate());
			}
		});
		dueDateColumn.getStyleClass().add("center");

		TableColumn<Contract, String> tenantColumn = new TableColumn<>("Tenant");
		tenantColumn.setCellValueFactory(new StringCellValueFactory<Contract>() {

			@Override
			protected String getValue(Contract item) {
				return item.getUnit().getName();
			}
		});
		
		TableColumn<Contract, String> unitColumn = new TableColumn<>("Unit");
		unitColumn.setCellValueFactory(new StringCellValueFactory<Contract>() {

			@Override
			protected String getValue(Contract item) {
				return item.getUnit().getName();
			}
		});

		TableColumn<Contract, String> amountColumn = new TableColumn<>("Amount");
		amountColumn.setCellValueFactory(new StringCellValueFactory<Contract>() {

			@Override
			protected String getValue(Contract item) {
				return FormatterUtil.formatAmount(item.getRentalAmount());
			}
		});
		amountColumn.getStyleClass().add("right");

		ObservableList<TableColumn<Contract, ?>> columns = table.getColumns();
		columns.add(dueDateColumn);
		columns.add(tenantColumn);
		columns.add(unitColumn);
		columns.add(amountColumn);
	}

	@FXML public void doOnBack() {
		stageController.showMainMenuScreen();
	}
	
}
