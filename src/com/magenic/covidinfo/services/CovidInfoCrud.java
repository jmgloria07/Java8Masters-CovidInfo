package com.magenic.covidinfo.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.magenic.covidinfo.models.CovidInfo;
import com.magenic.covidinfo.util.FileUtils;

public class CovidInfoCrud {
	CovidInformationInterface covidInformationInterface = new CovidInformationInterface() {};
	FileUtils fileUtils = new FileUtils();	

	private Map<String, Comparator<CovidInfo>> sortBy = null;
	
	private static final Function<CovidInfo, String> formatDisplay = info -> String.format("%10s %10s %10s %10s %15s", info.getName(),
			info.getCases(), info.getDeaths(), info.getRecoveries(), info.getDate());

	public CovidInfoCrud() {
		sortBy = new HashMap<>();
		sortBy.put("1", Comparator.comparing(CovidInfo::getName));
		sortBy.put("2", Comparator.comparing(CovidInfo::getCases));
		sortBy.put("3", Comparator.comparing(CovidInfo::getDeaths));
		sortBy.put("4", Comparator.comparing(CovidInfo::getRecoveries));
	}

	public static List<CovidInfo> covidInfos = new ArrayList<>();

	public void add(CovidInfo covidInfo) {
		Optional.of(covidInfo).map(c -> {
			if (!isInputValid(covidInfo)) return null;
			covidInfos.add(c);
			return "COVID-19 Information:\n"
					+ covidInformationInterface.format(c.getDate(),c.getName(), c.getCases(), c.getDeaths(), c.getRecoveries())
					+ "\n";
		}).ifPresent(System.out::println);
	}

	public void delete(String name) {
		covidInfos.removeIf(c -> c.getName().equals(name));
		System.out.format("%s COVID-19 Information has been deleted\n", name);
	}

	public String display(String actionType) {

		Collections.sort(covidInfos, sortBy.getOrDefault(actionType, Comparator.comparing(CovidInfo::getName)));

		return covidInfos.stream().map(formatDisplay::apply)
				.collect(Collectors.joining("\n"));
	}
	
	public String search(String name) {

		return covidInfos.stream().filter(info -> info.getName().equalsIgnoreCase(name))
			.sorted(Comparator.comparing(CovidInfo::getName))
			.map(formatDisplay::apply)
			.collect(Collectors.joining("\n"));
	}
	
	public String search(String count, String option) {
		Map<String, Predicate<CovidInfo>> searchFilterMap = new HashMap<>();
		Integer num = Integer.parseInt(count);		
		
		searchFilterMap.put("2", (p) -> (p.getCases() >= num));
		searchFilterMap.put("3", (p) -> (p.getDeaths() >= num));
		searchFilterMap.put("4", (p) -> (p.getRecoveries() >= num));
		return covidInfos.stream()
				.filter(searchFilterMap.get(option))
				.sorted(sortBy.get(option))
				.map(formatDisplay::apply)
				.collect(Collectors.joining("\n"));
	}
	
	public String saveToFile() {
		return fileUtils.saveInfoToFile();
	}
	
	private static boolean isInputValid(CovidInfo covidInfo) {
		if (covidInfo.getDeaths() + covidInfo.getRecoveries() > covidInfo.getCases()) {
			System.out.println("Number of recoveries plus deaths cannot be greater than total number of cases.");
		} else if (covidInfos.stream().anyMatch(c -> covidInfo.getName().equals(c.getName()))) {
			System.out.println("Country already exists.");
		} else {
			return true;
		}
		return false;
	}

	

}
