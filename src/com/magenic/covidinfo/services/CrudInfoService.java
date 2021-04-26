package com.magenic.covidinfo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.magenic.covidinfo.models.CovidInfo;

public class CrudInfoService {
	CovidInformationInterface covidInformationInterface = new CovidInformationInterface() {};
	
	private static List<CovidInfo> covidInfos = new ArrayList<>();
	
	public void add(CovidInfo covidInfo) {
		Optional.of(covidInfo)
			.map(c -> {
				covidInfos.add(c);
				return "COVID-19 Information:\n" + covidInformationInterface.format(c.getName(), 
						c.getCases(), 
						c.getDeaths(), 
						c.getRecoveries()) + "\n";
			}).ifPresent(System.out::println);
	}
	
	public void delete(String name) {
		covidInfos.removeIf(c -> c.getName().equals(name));
		System.out.format("%s COVID-19 Information has been deleted\n", name);
	}
}
