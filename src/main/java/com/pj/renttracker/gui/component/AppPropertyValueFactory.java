package com.pj.renttracker.gui.component;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.StringTokenizer;

import org.apache.commons.lang.WordUtils;

import com.pj.renttracker.util.FormatterUtil;

import javafx.beans.NamedArg;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class AppPropertyValueFactory <T>
		implements Callback<CellDataFeatures<T, String>, ObservableValue<String>> {

	private static final String GET_PREFIX = "get";
	
	private String property;

	public AppPropertyValueFactory(@NamedArg("property") String property) {
		this.property = property;
	}

	@Override
	public ObservableValue<String> call(CellDataFeatures<T, String> param) {
		Object item = param.getValue();
		StringTokenizer tokenizer = new StringTokenizer(property, ".");
		while (tokenizer.hasMoreTokens()) {
			try {
				Method method = item.getClass()
						.getMethod(GET_PREFIX + WordUtils.capitalize(tokenizer.nextToken()));
				item = method.invoke(item);
			} catch (Exception e) {
				throw new RuntimeException("Error getting property " + property, e);
			}
		}
		
		String value = null;
		if (item instanceof String) {
			value = (String)item;
		} else if (item instanceof Date) {
			value = FormatterUtil.formatDate((Date)item);
		} else if (item instanceof BigDecimal) {
			value = FormatterUtil.formatAmount((BigDecimal)item);
		} else {
			value = item.toString();
		}
		return new ReadOnlyStringWrapper(value);
	}

}
