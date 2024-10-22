package com.aston.AstnTimSort.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

import com.aston.AstnTimSort.repositories.DataRepository;

@Command
public class LoadCommands {

	private final DataRepository repository;

	@Autowired
	public LoadCommands(DataRepository repository) {
		this.repository = repository;
	}

	/*
	 * Возможные команды: load -i load -r load -f load -i -t <type> load -r -t
	 * <type> load -t -t <type> На данный момент <type> только person
	 */
	@Command(command = "load", description = "Data loading")
	public void load(@Option(longNames = "interactive", shortNames = 'i') Boolean interactive,
			@Option(longNames = "random", shortNames = 'r') Boolean random,
			@Option(longNames = "file", shortNames = 'f') Boolean fromFile,
			@Option(longNames = "type", shortNames = 't', defaultValue = "") String type)
			throws IOException {
		if (interactive) {
			loadInteractevely(type);
		} else if (random) {
			loadRandomly(type);
		} else if (fromFile) {
			loadFromFile(type);
		} else {
			System.out.println("Unknown option");
		}
	}

	private String loadInteractevely(String type) {
		System.out.println("Interactive mode");
		setRepositoryType(type);
		System.out.println("Use this pattern for choosen type:");
		System.out.println(repository.getPattern());
		Scanner in = new Scanner(System.in);
		String input = in.nextLine();
		while(!input.isBlank()) {
			repository.add(input);
			input = in.nextLine();
		}
		return "Data has been loaded";
	}

	private String loadRandomly(String type) {
		System.out.println("Random mode");
		setRepositoryType(type);
		// TODO
		return "Data has been loaded";
	}

	private void loadFromFile(String type) throws IOException {
		System.out.println("From file mode");
		setRepositoryType(type);
		System.out.println("Type the file path:");
		try {
			Scanner in = new Scanner(System.in);
			String filePath = in.nextLine();
			Path path = Path.of(filePath);
			Files.lines(path).forEach(repository::add);
			System.out.println("Data has been loaded");
		} catch (NoSuchFileException e) {
			System.out.println("File not found");
		}
	}

	private void setRepositoryType(String type) {
		if (type == null) {
			System.out
					.println("Which type of data do you want to load (Person, Animal, or Barrel)?");
			Scanner in = new Scanner(System.in);
			type = in.nextLine();
		}
		repository.setType(type);
	}

}
