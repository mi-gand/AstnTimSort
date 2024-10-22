package com.aston.AstnTimSort.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

import com.aston.AstnTimSort.parsers.ParserFactory;
import com.aston.AstnTimSort.parsers.StringParserToComparable;
import com.aston.AstnTimSort.repositories.DataRepository;

@Command
public class LoadCommands {

	private final DataRepository repository;
	private final ParserFactory parserFactory;

	@Autowired
	public LoadCommands(DataRepository repository, ParserFactory parserFactory) {
		this.repository = repository;
		this.parserFactory = parserFactory;
	}

	/*
	 * Возможные команды:
	 * load -i
	 * load -r
	 * load -f
	 * load -i -t <type>
	 * load -r -t <type>
	 * load -t -t <type>
	 * На данный момент <type> только person
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
		StringParserToComparable<?> parser = getParserFromUser(type);
		if (parser == null)
			return "Incorrect data type";
		// TODO
		return "Data has been loaded";
	}

	private String loadRandomly(String type) {
		System.out.println("Random mode");
		StringParserToComparable<?> parser = getParserFromUser(type);
		if (parser == null)
			return "Incorrect data type";
		// TODO
		return "Data has been loaded";
	}

	private void loadFromFile(String type) throws IOException {
		System.out.println("From file mode");
		StringParserToComparable<?> parser = getParserFromUser(type);
		if (parser == null) {
			System.out.println("Incorrect data type");
			return;
		}
		System.out.println("Type the file path:");
		try {
			Scanner in = new Scanner(System.in);
			String filePath = in.nextLine();
			Path path = Path.of(filePath);
			repository.add(Files.lines(path).map(parser::parse).collect(Collectors.toList()));
			System.out.println("Data has been loaded");
		} catch (NoSuchFileException e) {
			System.out.println("File not found");
		}
	}

	private StringParserToComparable<?> getParserFromUser(String type) {
		if (type == null) {
			System.out
					.println("Which type of data do you want to load (Person, Animal, or Barrel)?");
			Scanner in = new Scanner(System.in);
			type = in.nextLine();
		}
		return parserFactory.getParser(type);
	}

}
