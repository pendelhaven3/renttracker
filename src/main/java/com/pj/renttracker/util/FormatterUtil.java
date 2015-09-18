package com.pj.renttracker.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.pj.renttracker.Constants;

/**
 * 
 * @author PJ Miranda
 *
 */
public class FormatterUtil {

	private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
	
	public static String formatDate(Date date) {
		if (date == null) {
			return "";
		}
		return dateFormatter.format(DateUtil.toLocalDate(date));
	}

	public static String formatAmount(BigDecimal amount) {
		return new DecimalFormat(Constants.AMOUNT_FORMAT).format(amount);
	}
	
}