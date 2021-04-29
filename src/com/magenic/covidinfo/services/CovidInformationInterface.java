package com.magenic.covidinfo.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.StringJoiner;

import com.magenic.covidinfo.util.DateAndLocalizationUtil;

public interface CovidInformationInterface {
		
	default String format(LocalDate date,String name, int cases, int deaths, int recoveries) {
		StringJoiner sj = new StringJoiner("\n");
		sj.add(String.format("Date: %s", DateAndLocalizationUtil.formatDate(date)));
		sj.add(String.format("Country: %s", DateAndLocalizationUtil.getCountryName(name)));
		sj.add(String.format("Total Cases: %s", cases));
		sj.add(String.format("Total Deaths: %s", deaths));
		sj.add(String.format("Total Recoveries: %s", recoveries));
		
		return sj.toString();
	}
}
