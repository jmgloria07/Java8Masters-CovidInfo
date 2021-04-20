package com.magenic.covidinfo.services;

import java.util.List;

import com.magenic.covidinfo.models.CovidInfo;

public class CovidInfoDataStore {
	private static List<CovidInfo> covidInfos;
	
	public void add(CovidInfo covidInfo) {
		
	}
	
	public List<CovidInfo> getAll() {
		return covidInfos;
	}
	
	public void deleteCovidInfo(String name) {
		//removeIf
	}
}
