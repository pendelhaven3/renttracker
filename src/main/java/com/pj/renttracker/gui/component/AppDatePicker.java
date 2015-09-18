package com.pj.renttracker.gui.component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.pj.renttracker.Constants;

import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;

public class AppDatePicker extends DatePicker {

	public AppDatePicker() {
		setConverter(new StringConverter<LocalDate>() {

			private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);

			@Override
			public String toString(LocalDate localDate) {
				if (localDate == null)
					return "";
				return dateTimeFormatter.format(localDate);
			}

			@Override
			public LocalDate fromString(String dateString) {
				if (dateString == null || dateString.trim().isEmpty()) {
					return null;
				}
				return LocalDate.parse(dateString, dateTimeFormatter);
			}
		});
	}

}
