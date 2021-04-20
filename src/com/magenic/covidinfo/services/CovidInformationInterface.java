package com.magenic.covidinfo.services;

import java.time.LocalDateTime;
import java.util.StringJoiner;

public interface CovidInformationInterface {
		
	default String format(String name, int cases, int deaths, int recoveries) {
		StringJoiner sj = new StringJoiner("\n");
		sj.add(String.format("Date: %s", LocalDateTime.now().toString()));
		sj.add(String.format("Country: %s", name));
		sj.add(String.format("Total Cases: %s", cases));
		sj.add(String.format("Total Deaths: %s", deaths));
		sj.add(String.format("Total Recoveries: %s", recoveries));
		
		return sj.toString();
	}
}
