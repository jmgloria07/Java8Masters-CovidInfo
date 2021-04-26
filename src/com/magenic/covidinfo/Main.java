package com.magenic.covidinfo;


import java.util.Comparator;
import java.util.Scanner;

import com.magenic.covidinfo.models.CovidInfo;
import com.magenic.covidinfo.services.CrudInfoService;

public class Main {

	private static CrudInfoService service = new CrudInfoService();
	private static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		
		
		startPage();
		
		
		  
		  service.add(new CovidInfo("US",
		  1000, 20, 552)); service.add(new CovidInfo("US", 1001, 20, 552));
		  service.add(new CovidInfo("US", 1002, 20, 552));
		  
		  //CrudInfoService.covidInfos.forEach(CovidInfo::display);
		  
		  service.delete("US");
		  
		  //CrudInfoService.covidInfos.forEach(CovidInfo::display);
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
			System.out.println("\nInvalid Input\n");
			startPage();
			break;
		}
	}
	
	public static void listAllCovidInfo() {
		System.out.println("\nChoose sort option\n[1] By Country\n[2] By Cases\n[3] By Deaths\n[4] By Recoveries\n[-1] Exit");
		System.out.print("Enter option type : ");
		String option = scan.nextLine();
		
		System.out.println("\n============================================================");
		System.out.println(String.format("%10s %10s %10s %15s", "Country", "Cases", "Deaths", "Recoveries"));
		System.out.println("============================================================");
		
		switch (option) {
		case "1": {
			CrudInfoService.covidInfos.stream()
		        .sorted(Comparator.comparing(CovidInfo::getName))
		        .forEach(info -> System.out.println(
		        		String.format("%10s %10s %10s %15s"
		        				,info.getName()
		        				,info.getCases()
		        				,info.getDeaths()
		        				,info.getRecoveries()
		        		)
			     )
	        );
			break;
		}
		
		case "2": {
			CrudInfoService.covidInfos.stream()
		        .sorted(Comparator.comparing(CovidInfo::getCases))
		        .forEach(info -> System.out.println(
		        		String.format("%10s %10s %10s %15s"
		        				,info.getName()
		        				,info.getCases()
		        				,info.getDeaths()
		        				,info.getRecoveries()
		        		)
			     )
	        );
			break;
		}
		
		case "3": {
			CrudInfoService.covidInfos.stream()
		        .sorted(Comparator.comparing(CovidInfo::getDeaths))
		        .forEach(info -> System.out.println(
		        		String.format("%10s %10s %10s %15s"
		        				,info.getName()
		        				,info.getCases()
		        				,info.getDeaths()
		        				,info.getRecoveries()
		        		)
			     )
	        );
			break;
		}

		case "4": {
			CrudInfoService.covidInfos.stream()
		        .sorted(Comparator.comparing(CovidInfo::getRecoveries))
		        .forEach(info -> System.out.println(
		        		String.format("%10s %10s %10s %15s"
		        				,info.getName()
		        				,info.getCases()
		        				,info.getDeaths()
		        				,info.getRecoveries()
		        		)
			     )
	        );
			break;
		}
		
		case "-1": {
			System.out.println("Returning to Homepage");
			break;
		}

		default:
			System.out.println("\nInvalid Input\n");
			break;
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
		System.out.println("\nChoose an option\n[1] By Country\n[2] By Cases\n[3] By Deaths\n[4] By Recoveries\n[-1] Exit");
		System.out.print("Enter option type : ");
		String option = scan.nextLine();
		
		switch (option) {
		case "1": {
			System.out.print("Enter Country Name : ");
			String name = scan.nextLine();

			System.out.println("\n============================================================");
			System.out.println(String.format("%10s %10s %10s %15s", "Country", "Cases", "Deaths", "Recoveries"));
			System.out.println("============================================================");
			
			CrudInfoService.covidInfos.stream()
				.filter(info -> info.getName() == name)
		        .sorted(Comparator.comparing(CovidInfo::getName))
		        .forEach(info -> System.out.println(
		        		String.format("%10s %10s %10s %15s"
		        				,info.getName()
		        				,info.getCases()
		        				,info.getDeaths()
		        				,info.getRecoveries()
		        		)
			     )
	        );
			break;
		}
		
		case "2": {
			System.out.print("Enter Number of Cases (greater than equal) : ");
			int count = scan.nextInt();

			System.out.println("\n============================================================");
			System.out.println(String.format("%10s %10s %10s %15s", "Country", "Cases", "Deaths", "Recoveries"));
			System.out.println("============================================================");
			
			CrudInfoService.covidInfos.stream()
				.filter(info -> info.getCases() >= count)
				.sorted(Comparator.comparing(CovidInfo::getCases))
		        .forEach(info -> System.out.println(
		        		String.format("%10s %10s %10s %15s"
		        				,info.getName()
		        				,info.getCases()
		        				,info.getDeaths()
		        				,info.getRecoveries()
		        		)
			     )
	        );
			break;
		}
		
		case "3": {
			System.out.print("Enter Number of Deaths (greater than equal) : ");
			int count = scan.nextInt();

			System.out.println("\n============================================================");
			System.out.println(String.format("%10s %10s %10s %15s", "Country", "Cases", "Deaths", "Recoveries"));
			System.out.println("============================================================");
			
			CrudInfoService.covidInfos.stream()
				.filter(info -> info.getDeaths() >= count)
		        .sorted(Comparator.comparing(CovidInfo::getDeaths))
		        .forEach(info -> System.out.println(
		        		String.format("%10s %10s %10s %15s"
		        				,info.getName()
		        				,info.getCases()
		        				,info.getDeaths()
		        				,info.getRecoveries()
		        		)
			     )
	        );
			break;
		}

		case "4": {
			System.out.print("Enter Number of Recoveries (greater than equal) : ");
			int count = scan.nextInt();

			System.out.println("\n============================================================");
			System.out.println(String.format("%10s %10s %10s %15s", "Country", "Cases", "Deaths", "Recoveries"));
			System.out.println("============================================================");
			
			CrudInfoService.covidInfos.stream()
				.filter(info -> info.getRecoveries() >= count)
		        .sorted(Comparator.comparing(CovidInfo::getRecoveries))
		        .forEach(info -> System.out.println(
		        		String.format("%10s %10s %10s %15s"
		        				,info.getName()
		        				,info.getCases()
		        				,info.getDeaths()
		        				,info.getRecoveries()
		        		)
			     )
	        );
			break;
		}
		
		case "-1": {
			System.out.println("Returning to Homepage");
			break;
		}

		default:
			System.out.println("\nInvalid Input\n");
			break;
		}
		
		System.out.println();
	}

}
