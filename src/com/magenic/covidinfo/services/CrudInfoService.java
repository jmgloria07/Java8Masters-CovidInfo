package com.magenic.covidinfo.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import com.magenic.covidinfo.models.CovidInfo;

public class CrudInfoService {
	CovidInformationInterface covidInformationInterface = new CovidInformationInterface() {
	};

	private Map<String, Comparator<CovidInfo>> sortBy = null;


	public CrudInfoService() {
		sortBy = new HashMap<>();
		sortBy.put("1", Comparator.comparing(CovidInfo::getName));
		sortBy.put("2", Comparator.comparing(CovidInfo::getCases));
		sortBy.put("3", Comparator.comparing(CovidInfo::getDeaths));
		sortBy.put("4", Comparator.comparing(CovidInfo::getRecoveries));
	}

	public static List<CovidInfo> covidInfos = new ArrayList<>();

	public void add(CovidInfo covidInfo) {
		Optional.of(covidInfo).map(c -> {
			covidInfos.add(c);
			return "COVID-19 Information:\n"
					+ covidInformationInterface.format(c.getName(), c.getCases(), c.getDeaths(), c.getRecoveries())
					+ "\n";
		}).ifPresent(System.out::println);
	}

	public void delete(String name) {
		covidInfos.removeIf(c -> c.getName().equals(name));
		System.out.format("%s COVID-19 Information has been deleted\n", name);
	}

	public void display(String actionType) {

		Collections.sort(covidInfos, sortBy.getOrDefault(actionType, Comparator.comparing(CovidInfo::getName)));

		covidInfos.forEach(info -> System.out.println(String.format("%10s %10s %10s %15s", info.getName(),
				info.getCases(), info.getDeaths(), info.getRecoveries())));

	}
	
	public void search(String name) {

		covidInfos.stream().filter(info -> info.getName().equalsIgnoreCase(name))
			.sorted(Comparator.comparing(CovidInfo::getName))
			.forEach(info -> System.out.println(String.format("%10s %10s %10s %15s", info.getName(),
						info.getCases(), info.getDeaths(), info.getRecoveries())));
	}
	
	public void search(String count, String option) {
		Map<String, Predicate<CovidInfo>> searchFilterMap = new HashMap<>();
		Integer num = Integer.parseInt(count);
		
		
		searchFilterMap.put("2", (p) -> (p.getCases() >= num));
		searchFilterMap.put("3", (p) -> (p.getDeaths() >= num));
		searchFilterMap.put("4", (p) -> (p.getRecoveries() >= num));

		covidInfos.stream().filter(searchFilterMap.get(option)).sorted(sortBy.get(option))
			.forEach(info -> System.out.println(String.format("%10s %10s %10s %15s", info.getName(),
						info.getCases(), info.getDeaths(), info.getRecoveries())));
	}

}
