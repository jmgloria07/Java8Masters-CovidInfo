package com.magenic.covidinfo.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class DateAndLocalizationUtil {

	public  static String validateDate() {
		return null;
		
	}

	public static LocalDate parseDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate localDate = null;
		try {

			localDate = LocalDate.parse(date, formatter);
		} catch (DateTimeParseException e) {
			System.out.println("Please enter date in MM/dd/yyy format!");
		}

		return localDate;
	}
	
	public static String formatDate(LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		return formatter.format(date);

	}
	
	public static String getCountryName(String countryCode) {
		Locale loc = new Locale("",countryCode);
		
		return loc.getDisplayCountry();
	}
	
	
}
