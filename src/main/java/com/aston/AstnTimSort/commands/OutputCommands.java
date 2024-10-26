package com.aston.AstnTimSort.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

import com.aston.AstnTimSort.repositories.DataRepository;

@Command
public class OutputCommands {

	private final DataRepository repository;

	@Autowired
	public OutputCommands(DataRepository repository) {
		this.repository = repository;
	}

	@Command(command = "show", description = "show data")
	public void show() {
		System.out.println(repository.getDataRepresentation());
	}

	@Command(command = "export", description = "Export data to file")
	public void export(
			@Option(longNames = "append", shortNames = 'a', description = "Flag to append export") Boolean append,
			@Option(longNames = "force", shortNames = 'f', description = "Flag to rewrite file") Boolean force,
			@Option(longNames = "path", shortNames = 'p', description = "Path to export file") String file) {
		if (force & append) {
			System.out.println("Force and append options are incompatible");
			return;
		}
		if (file == null) {
			System.out.println("Type the file path:");
			Scanner in = new Scanner(System.in);
			file = in.nextLine();
		}
		Path filePath = null;
		try {
			filePath = Path.of(file);
			if (filePath == null) {
				System.out.println("Incorrect path");
				return;
			}
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
			return;
		}
		boolean isFileExists = Files.exists(filePath);

		if (isFileExists & !force & !append) {
			force = askIfUserWantsToRewriteFile();
			if (force == null) {
				System.out.println("Unclear answer");
				return;
			}
		}

		if (isFileExists & !force & !append) {
			append = askIfUserWantsToAppendToFile();
			if (append == null) {
				System.out.println("Unclear answer");
				return;
			}
		}

		try {
			if (!isFileExists) {
				repository.exportToFile(filePath, false);
			} else {
				if (force) {
					repository.exportToFile(filePath, false);
				} else if (append) {
					repository.exportToFile(filePath, true);
				}
			}
		} catch (Exception e) {
			System.out.println("Something went wrong(");
//			e.printStackTrace();
		}

	}

	private Boolean askIfUserWantsToRewriteFile() {
		System.out.println("This file already exists");
		System.out.print("Do you want to rewrite file? (Y/N): ");
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

	private Boolean askIfUserWantsToAppendToFile() {
		System.out.print("Do you want to append to the file? (Y/N): ");
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
}
