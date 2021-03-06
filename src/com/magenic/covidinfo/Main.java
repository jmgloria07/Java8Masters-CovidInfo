package com.magenic.covidinfo;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringJoiner;

import com.magenic.covidinfo.models.CovidInfo;
import com.magenic.covidinfo.services.CovidInfoCrud;
import com.magenic.covidinfo.util.DateAndLocalizationUtil;

public class Main {

	private static CovidInfoCrud service = new CovidInfoCrud();
	private static Scanner scan = new Scanner(System.in);

	
	public static void main(String[] args) {		
		startPage();
	}
	
	public static void startPage(){
		System.out.println("Choose an Action to Perform:");
		System.out.println("[1] List All Covid Info");
		System.out.println("[2] Add New Covid Info");
		System.out.println("[3] Delete Covid Info");
		System.out.println("[4] Search for Covid Info");
		System.out.println("[5] List All Info from file");
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
		
		case "5": {
			listAllInfoFromFile();
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
	
	public static void listAllInfoFromFile() {
		System.out.println("\nCOVID files in the directory: ");
		
		service.listFiles().stream()
			.forEach(System.out::println);
		
		System.out.print("Choose file to view: ");
		String choice = scan.nextLine();
		
		System.out.printf("Displaying info from %s\n\n",choice);
		service.getInfo(choice).stream()
			.forEach(System.out::println);
	}

	public static void listAllCovidInfo() {
		System.out.println("\nChoose sort option\n[1] By Country\n[2] By Cases\n[3] By Deaths\n[4] By Recoveries\n[-1] Exit");
		System.out.print("Enter option type : ");
		String option = scan.nextLine();
		
		String[] values = {"1","2","3","4"};
		
		
		if(Arrays.stream(values).anyMatch(option::equals)) {			
			String display = getHeader() + service.display(option);
			System.out.println(display);
			saveResultToFile(display);
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
		
		scan.nextLine();
		 String date = null;
		 LocalDate localDate = null;
		 
		do {
			System.out.print("Enter Date (MM/dd/yyyy): ");
			date = scan.nextLine();
			System.out.println();
			localDate = DateAndLocalizationUtil.parseDate(date);
		}while(localDate == null);
		
		
		service.add(new CovidInfo(country, casesCount, deathsCount, recoveriesCount,localDate));
	}
	
	public static void deleteCovidInfo() {
		System.out.print("Enter Country Name: ");
		String country = scan.nextLine();
		service.delete(country);
		System.out.println("\n" + getHeader());
		CovidInfoCrud.covidInfos.forEach(CovidInfo::display);
		System.out.println();
		
	}
	
	public static void searchCovidInfo() {	
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
			
			String display = getHeader() + service.search(name);
			System.out.println(display);
			saveResultToFile(display);
		}else if(searchOptions.keySet().stream().anyMatch(option::equals)) {			
			System.out.printf("Enter Number of %s (greater than equal) : ",searchOptions.get(option));
			String count = scan.nextLine();
		
			String display = getHeader() + service.search(count, option);
			System.out.println(display);
			saveResultToFile(display);
		}else if("-1".equals(option)) {
			System.out.println("Returning to Homepage");
		} else {
			System.out.println("\nInvalid Input\n");
		}
		
		System.out.println();
	}
	
	private static void saveResultToFile(String info) {
		System.out.print("\nWould you like to save the result to a file? (Y/N): ");
		String choice = scan.nextLine();
		
		if ("Y".equals(choice)) {
			System.out.printf("Information saved in: %s\n", service.saveToFile(info));
		}
	}
	
	private static String getHeader() {
		StringJoiner sj = new StringJoiner("\n");
		sj.add("============================================================");
		sj.add(String.format("%10s %10s %10s %10s %15s", "Country", "Cases", "Deaths", "Recoveries", "Date"));
		sj.add("============================================================\n");	
		return sj.toString();
	}

}
