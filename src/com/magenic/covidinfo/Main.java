package com.magenic.covidinfo;


import com.magenic.covidinfo.models.CovidInfo;
import com.magenic.covidinfo.services.CrudInfoService;

public class Main {

	public static void main(String[] args) {
		CrudInfoService service = new CrudInfoService();
		
		service.add(new CovidInfo("PH", 100, 2, 55));
		service.add(new CovidInfo("US", 1000, 20, 552));
		service.add(new CovidInfo("US", 1001, 20, 552));
		service.add(new CovidInfo("US", 1002, 20, 552));

		CrudInfoService.covidInfos.forEach(CovidInfo::display);
		
		service.delete("US");
		System.out.println("after");
		
		CrudInfoService.covidInfos.forEach(CovidInfo::display);
	}

}
