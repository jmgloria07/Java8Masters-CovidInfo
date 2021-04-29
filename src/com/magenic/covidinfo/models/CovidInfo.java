package com.magenic.covidinfo.models;

import java.time.LocalDate;

public class CovidInfo {
	private String name;
	private int cases;
	private int deaths;
	private int recoveries;
	private LocalDate date;
	
	public CovidInfo(String name, int cases, int deaths, int recoveries, LocalDate date) {
		super();
		this.name = name;
		this.cases = cases;
		this.deaths = deaths;
		this.recoveries = recoveries;
		this.date = date;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCases() {
		return cases;
	}
	public void setCases(int cases) {
		this.cases = cases;
	}
	public int getDeaths() {
		return deaths;
	}
	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}
	public int getRecoveries() {
		return recoveries;
	}
	public void setRecoveries(int recoveries) {
		this.recoveries = recoveries;
	}
	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public void display(){
		System.out.println(String.format("%10s %10s %10s %15s", this.name, this.cases, this.deaths, this.recoveries));
	}

	
	
	/*
	 * public void displayCreated() {
	 * System.out.println(String.format("\n\nDate: %s", this.date.toString()));
	 * System.out.println(String.format("Country: %s", this.name));
	 * System.out.println(String.format("Total Cases: %s", this.cases));
	 * System.out.println(String.format("Total Deaths: %s", this.deaths));
	 * System.out.println(String.format("Total Recoveries: %s", this.recoveries)); }
	 */
}
