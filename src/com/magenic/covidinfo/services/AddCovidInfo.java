package com.magenic.covidinfo.services;

import java.util.ArrayList;

import com.magenic.covidinfo.models.CovidInfo;

public interface AddCovidInfo {
	default public CovidInfo add(CovidInfo covidInfo) {
		if (CovidInfoDataStore.covidInfos == null) 
			CovidInfoDataStore.covidInfos = new ArrayList<CovidInfo>(); 
		
		CovidInfoDataStore.covidInfos.add(covidInfo);
		return covidInfo;
	}
}
