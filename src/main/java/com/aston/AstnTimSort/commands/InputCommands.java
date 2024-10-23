package com.aston.AstnTimSort.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

import com.aston.AstnTimSort.repositories.DataRepository;

@Command
public class InputCommands {
	private final String CORRUPTED_LINE_MSG_TMPL = "Corrupted line %d \"%s\" was ignored: %s\n";

	private final DataRepository repository;

	@Autowired
	public InputCommands(DataRepository repository) {
		this.repository = repository;
	}

	@Command(command = "load", description = "Data loading")
	public void load(
			@Option(longNames = "interactive", shortNames = 'i', description = "Flag enter data by hands") boolean interactive,
			@Option(longNames = "random", shortNames = 'r', description = "Flag to load random data") boolean random,
			@Option(longNames = "file", shortNames = 'f', description = "Flag to load random from file") boolean fromFile,
			@Option(longNames = "type", shortNames = 't', defaultValue = "", description = "Name of the required data type") String type)
			throws IOException {
		if (interactive) {
			loadInteractevely(type);
		} else if (random) {
			loadRandomly(type);
		} else if (fromFile) {
			loadFromFile(type);
		} else {
			System.out.println("How do you want to load data?");
			System.out.println("Interactively (-i), randomly (-r), or from file (-f)");
			Scanner in = new Scanner(System.in);
			String input = in.nextLine();
			if ("-i".equals(input)) {
				loadInteractevely(type);
			} else if ("-r".equals(input)) {
				loadRandomly(type);
			} else if ("-f".equals(input)) {
				loadFromFile(type);
			} else {
				System.out.println("Unknown option");
			}
		}
	}

	private String loadInteractevely(String type) {
		System.out.println("Interactive mode");
		setRepositoryType(type);
		System.out.println("Use this pattern for choosen type:");
		System.out.println(repository.getPattern());
		System.out.println("For example:");
		System.out.println(repository.getInputExample());
		System.out.println("Enter a blank line to stop");
		Scanner in = new Scanner(System.in);
		String input = in.nextLine();
		while (!input.isBlank()) {
			try {
				repository.add(input);
			} catch (RuntimeException e) {
				System.out.println(e.getMessage());
			}
			input = in.nextLine();
		}
		return "Data has been loaded";
	}

	private String loadRandomly(String type) {
		System.out.println("Random mode");
		type = setRepositoryType(type);
		// TODO
		return "Data has been loaded";
	}

	private void loadFromFile(String type) {
		System.out.println("From file mode");
		try {
			setRepositoryType(type);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return;
		}
		System.out.println("Type the file path:");
		Scanner in = new Scanner(System.in);
		Path filePath = null;
		try {
			filePath = Path.of(in.nextLine());
			if (filePath == null | !Files.isRegularFile(filePath)) {
				System.out.println("This file doesn't exist");
				return;
			}
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
			return;
		}
		try (BufferedReader input = Files.newBufferedReader(filePath)) {
			long lineNumber = 1L;
			String line = input.readLine();
			while (line != null) {
				try {
					repository.add(line);
				} catch (RuntimeException e) {
					System.out.printf(CORRUPTED_LINE_MSG_TMPL, lineNumber, line, e.getMessage());
				}
				line = input.readLine();
				lineNumber += 1;
			}
		} catch (RuntimeException e1) {
			System.out.println(e1.getMessage());
			return;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return;
		}
		System.out.println("Data has been loaded");
	}

	private String setRepositoryType(String type) {
		if (type == null) {
			System.out
					.println("Which type of data do you want to load (Person, Animal, or Barrel)?");
			// TODO repository should give possible variants
			Scanner in = new Scanner(System.in);
			type = in.nextLine();
		}
		repository.setType(type);
		return type;
	}

}
