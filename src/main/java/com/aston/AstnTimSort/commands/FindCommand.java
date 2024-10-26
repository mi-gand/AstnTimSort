package com.aston.AstnTimSort.commands;

import static com.aston.AstnTimSort.utils.ConsoleUtils.askGeneralQuestion;
import static com.aston.AstnTimSort.utils.ConsoleUtils.getFilePathFromUser;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.command.annotation.Command;

import com.aston.AstnTimSort.repositories.DataRepository;

@Command
public class FindCommand {

	private final DataRepository repository;

	@Autowired
	public FindCommand(DataRepository repository) {
		this.repository = repository;
	}

	@Command(command = "find", description = "find object")
	public void find() throws Exception {
		if(!repository.hasData()) {
			System.out.println("There are no data");
			return;
		}
		if(!repository.isSorted()) {
			System.out.println("Data is not sorted. Please, run \"sort\" before find query");
			return;
		}
		System.out.println("Use this pattern for query:");
		System.out.println(repository.getPattern());
		System.out.println("For example:");
		System.out.println(repository.getInputExample());
		System.out.println("Your query:");
		Scanner in = new Scanner(System.in);
		String input = in.nextLine();
		boolean isPresent = false;
		try {
			isPresent = repository.find(input);
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
			return;
		}

		if (!isPresent) {
			System.out.println("There is no such object");
			return;
		}
		System.out.println("Object has been found");
		Boolean answer = askGeneralQuestion("Do you want to export it?");
		if (answer == null) {
			System.out.println("Unclear answer");
			return;
		}
		if (!answer)
			return;

		Path filePath;
		try {
			filePath = getFilePathFromUser(null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return;
		}

		boolean isFileExists = Files.exists(filePath);
		Boolean force = false;
		Boolean append = false;

		if (isFileExists) {
			System.out.println("This file already exists");
			force = askGeneralQuestion("Do you want to rewrite file?");
			if (force == null) {
				System.out.println("Unclear answer");
				return;
			}
		}

		if (isFileExists && !force) {
			append = askGeneralQuestion("Do you want to append to the file?");
			if (append == null) {
				System.out.println("Unclear answer");
				return;
			}
		}

		try {
			if (!isFileExists) {
				repository.exportToFileSearchResult(filePath, false);
			} else {
				if (force) {
					repository.exportToFileSearchResult(filePath, false);
				} else if (append) {
					repository.exportToFileSearchResult(filePath, true);
				}
			}
		} catch (Exception e) {
			System.out.println("Something went wrong:(");
			throw e;
		}
		System.out.println("Search result has been exported");
	}
}
