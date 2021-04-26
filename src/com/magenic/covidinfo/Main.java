package com.magenic.covidinfo;


import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.magenic.covidinfo.models.CovidInfo;
import com.magenic.covidinfo.services.CrudInfoService;

public class Main {

	private static CrudInfoService service = new CrudInfoService();
	private static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		service.add(new CovidInfo("PH", 100, 123, 123));
		service.add(new CovidInfo("US", 89, 213, 131));
		service.add(new CovidInfo("JP", 45, 341, 12));
		service.add(new CovidInfo("US", 23, 20, 12));
		
		startPage();
	}
	
	public static void startPage(){
		System.out.println("Choose an Action to Perform:");
		System.out.println("[1] List All Covid Info");
		System.out.println("[2] Add New Covid Info");
		System.out.println("[3] Delete Covid Info");
		System.out.println("[4] Search for Covid Info");
		System.out.println("[-1] Exit");
		System.out.print("Enter action type : ");
		String choice = scan.nextLine();
		
		switch (choice) {
		case "1": {
			listAllCovidInfo();
			startPage();
			break;
		}
		
		case "2": {
			addNewCovidInfo();
			String skip = scan.nextLine();
			startPage();
			break;
		}
		
		case "3": {
			deleteCovidInfo();
			startPage();
			break;
		}

		case "4": {
			searchCovidInfo();
			startPage();
			break;
		}
		
		case "-1": {
			System.out.println("Exiting Application");
			break;
		}

		default:
			if( choice != null && !"".equals(choice)) {
				System.out.println("\nInvalid Input\n");
				
			}
			startPage();
			break;
		}
		System.out.println();
	}
	
	public static void listAllCovidInfo() {
		System.out.println("\nChoose sort option\n[1] By Country\n[2] By Cases\n[3] By Deaths\n[4] By Recoveries\n[-1] Exit");
		System.out.print("Enter option type : ");
		String option = scan.nextLine();
		
		System.out.println("\n============================================================");
		System.out.println(String.format("%10s %10s %10s %15s", "Country", "Cases", "Deaths", "Recoveries"));
		System.out.println("============================================================");
		
		String[] values = {"1","2","3","4"};
		
		
		if(Arrays.stream(values).anyMatch(option::equals)) {
			
			service.display(option);
			
		}else if("-1".equals(option)) {
			
			System.out.println("Returning to Homepage");
			
		}else {
			
			System.out.println("\nInvalid Input\n");
		}
		
		System.out.println();
	}
	
	public static void addNewCovidInfo() {
		System.out.print("Enter Country Name: ");
		String country = scan.nextLine();
		System.out.print("Enter Number of Cases: ");
		int casesCount = scan.nextInt();
		System.out.print("Enter Number of Deaths: ");
		int deathsCount = scan.nextInt();
		System.out.print("Enter Number of Recoveries: ");
		int recoveriesCount = scan.nextInt();
		System.out.println();
		service.add(new CovidInfo(country, casesCount, deathsCount, recoveriesCount));
	}
	
	public static void deleteCovidInfo() {
		System.out.print("Enter Country Name: ");
		String country = scan.nextLine();
		service.delete(country);
		System.out.println("\n============================================================");
		System.out.println(String.format("%10s %10s %10s %15s", "Country", "Cases", "Deaths", "Recoveries"));
		System.out.println("============================================================");
		CrudInfoService.covidInfos.forEach(CovidInfo::display);
		System.out.println();
		
	}
	
	public static void searchCovidInfo() {
		String[] values = {"2","3","4"};
		Map<String,String> searchOptions = new HashMap<String, String>();
		searchOptions.put("2", "Cases");
		searchOptions.put("3", "Deaths");
		searchOptions.put("4", "Recoveries");
		
		System.out.println("\nChoose an option\n[1] By Country\n[2] By Cases\n[3] By Deaths\n[4] By Recoveries\n[-1] Exit");
		System.out.print("Enter option type : ");
		String option = scan.nextLine();
		
		
		if("1".equals(option)) {
			System.out.print("Enter Country Name : ");
			String name = scan.nextLine();

			System.out.println("\n============================================================");
			System.out.println(String.format("%10s %10s %10s %15s", "Country", "Cases", "Deaths", "Recoveries"));
			System.out.println("============================================================");	
			service.search(name);
		}else if(searchOptions.keySet().stream().anyMatch(option::equals)) {
			
			System.out.printf("Enter Number of %s (greater than equal) : ",searchOptions.get(option));
			String count = scan.nextLine();

			System.out.println("\n============================================================");
			System.out.println(String.format("%10s %10s %10s %15s", "Country", "Cases", "Deaths", "Recoveries"));
			System.out.println("============================================================");		
		
			service.search(count, option);
			
		}else if("-1".equals(option)) {
			System.out.println("Returning to Homepage");
		} else {
			System.out.println("\nInvalid Input\n");
		}
		
		System.out.println();
	}

}
