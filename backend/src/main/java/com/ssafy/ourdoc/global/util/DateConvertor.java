package com.ssafy.ourdoc.global.util;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;

public class DateConvertor {
	public static LocalDate convertDate(String date) {
		if (date == null || date.isEmpty()) {
			return null;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		return LocalDate.parse(date, formatter);
	}

	public static Year convertYear(String date) {
		if (date == null || date.isEmpty()) {
			return null;
		}
		return Year.parse(date);
	}
}
