package com.magenic.covidinfo.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {

	private static final String SALT = "_covid";
	private static final String OUTPUT_DIRECTORY = "output";
	
	public String saveInfoToFile() {
		
		try {
			Files.list(Paths.get(OUTPUT_DIRECTORY)).forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void readInfoFromFile() {
		try {
			Files.list(Paths.get(OUTPUT_DIRECTORY)).forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		FileUtils fileUtils = new FileUtils();
		fileUtils.saveInfoToFile();
	}
}
