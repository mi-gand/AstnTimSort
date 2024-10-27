package com.aston.AstnTimSort.commands;

import static com.aston.AstnTimSort.utils.ConsoleUtils.askGeneralQuestion;
import static com.aston.AstnTimSort.utils.ConsoleUtils.getFilePathFromUser;

import java.nio.file.Files;
import java.nio.file.Path;

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
			@Option(longNames = "path", shortNames = 'p', description = "Path to export file") String file)
			throws Exception {
		if (force & append) {
			System.out.println("Force and append options are incompatible");
			return;
		}

		Path filePath;
		try {
			filePath = getFilePathFromUser(file);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return;
		}

		boolean isFileExists = Files.exists(filePath);

		if (isFileExists && !force && !append) {
			System.out.println("This file already exists");
			force = askGeneralQuestion("Do you want to rewrite file?");
			if (force == null) {
				System.out.println("Unclear answer");
				return;
			}
		}

		if (isFileExists && !force && !append) {
			append = askGeneralQuestion("Do you want to append to the file?");
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
			System.out.println("Something went wrong:(");
			throw e;
		}
		System.out.println("Data has been exported");
	}

}
