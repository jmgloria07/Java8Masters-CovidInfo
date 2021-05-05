package com.magenic.covidinfo.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtils {

	private static final Base64.Encoder ENCODER = Base64.getMimeEncoder();
	private static final Base64.Decoder DECODER = Base64.getMimeDecoder();
	
	private static final String SUFFIX = "_covid";
	private static final String TXT_EXTENSION = ".txt";
	private static final String OUTPUT_DIRECTORY = "output/";
	
	private static final DateTimeFormatter FILENAME_DATE_FORMAT = DateTimeFormatter.ofPattern("ddMMyy'-'hhmmss");
	
	private Supplier<String> filenameSupplier = () -> {
	    String filename = LocalDateTime.now().format(FILENAME_DATE_FORMAT) + SUFFIX;
	    filename = ENCODER.encodeToString(filename.getBytes()); 
	    return filename + TXT_EXTENSION;
	};
	
	private BiPredicate<Path, BasicFileAttributes> extensionAndSuffixFilter = (p, basicFileAttributes) -> {
		boolean isValid = false;
		String filename = p.getFileName().toString();
		if (filename.endsWith(TXT_EXTENSION)) {
			try {
				byte[] decodedBytes = DECODER.decode(filename.substring(0, filename.lastIndexOf(".")));
				filename = new String(decodedBytes);
				isValid = filename.endsWith(SUFFIX);
			} catch (IllegalArgumentException e) {
				isValid = false;
			}				
		}
		return isValid;
	};
	
	public String writeFile(String info) {		
		String filename = filenameSupplier.get();
		Path newFilePath = Paths.get(OUTPUT_DIRECTORY + filename);
		
		try (BufferedWriter writer = Files.newBufferedWriter(newFilePath, StandardCharsets.UTF_8)) {
			writer.write(info);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return newFilePath.toString();
	}
	
	public List<String> listFiles() {		
		try (Stream<Path> pathStream = Files.find(Paths.get(OUTPUT_DIRECTORY), 
				Integer.MAX_VALUE, 
				extensionAndSuffixFilter)) {
			return pathStream.map(p -> p.getFileName().toString())
					.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<String> getLines(String filename) {		
		if (!filename.toLowerCase().endsWith(TXT_EXTENSION)) {
			filename += TXT_EXTENSION;
		}
		
		if (isFilenameValid(filename)) {
			Path path = Paths.get(OUTPUT_DIRECTORY + filename);
			
			try (Stream<String> contents = Files.lines(path)) { 
				return contents.collect(Collectors.toList());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;		
	}
		
	private boolean isFilenameValid(String filename) {
		Path path = Paths.get(OUTPUT_DIRECTORY + filename);
		boolean isValid = extensionAndSuffixFilter.test(path, basicFileAttributes());
		if (!isValid) {
			System.out.printf("Filename %s is not valid.", filename);
		}
		return isValid;
	}
	
	private static BasicFileAttributes basicFileAttributes() {
		return new BasicFileAttributes() {			
			@Override
			public long size() {
				return 0;
			}
			
			@Override
			public FileTime lastModifiedTime() {
				return null;
			}
			
			@Override
			public FileTime lastAccessTime() {
				return null;
			}
			
			@Override
			public boolean isSymbolicLink() {
				return false;
			}
			
			@Override
			public boolean isRegularFile() {
				return false;
			}
			
			@Override
			public boolean isOther() {
				return false;
			}
			
			@Override
			public boolean isDirectory() {
				return false;
			}
			
			@Override
			public Object fileKey() {
				return null;
			}
			
			@Override
			public FileTime creationTime() {
				return null;
			}
		};
	}
}
