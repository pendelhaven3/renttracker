package com.pj.renttracker.gui.component;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public abstract class StringCellValueFactory <T>
		implements Callback<CellDataFeatures<T, String>, ObservableValue<String>> {

	@Override
	public ObservableValue<String> call(CellDataFeatures<T, String> param) {
		return new ReadOnlyStringWrapper(getValue(param.getValue()));
	}

	protected abstract String getValue(T item);

}
