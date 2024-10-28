package com.aston.AstnTimSort.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public final class ConsoleUtils {
	private ConsoleUtils() {
		throw new UnsupportedOperationException();
	}
	
	public static Boolean askGeneralQuestion(String question) {
		System.out.printf("%s (Y/N): ", question);
		Scanner in = new Scanner(System.in);
		String answer = in.nextLine().trim().toUpperCase();
		if ("Y".equals(answer)) {
			return true;
		} else if ("N".equals(answer)) {
			return false;
		} else {
			return null;
		}
	}
	
	public static Path getFilePathFromUser(String file) {
		if (file == null) {
			System.out.println("Type the file path:");
			Scanner in = new Scanner(System.in);
			file = in.nextLine();
		}
		Path filePath = Path.of(file);
		if (filePath == null) {
			throw new RuntimeException("Incorrect path");
		}
		if (Files.isDirectory(filePath)) {
			throw new RuntimeException("This is a directory");
		}
		return filePath;
	}
}
