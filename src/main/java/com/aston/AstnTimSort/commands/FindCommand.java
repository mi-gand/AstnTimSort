package com.aston.AstnTimSort.commands;

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
	public void find(String input) {
		if (repository.find(input)) {
			System.out.println("Object has been found");
			// TODO
			System.out.print("Do you want to export it? (Y/N):");
			Scanner in = new Scanner(System.in);
			String answer = in.nextLine();
			System.out.print("Nevermind");
			// TODO expert to file
			
		} else {
			System.out.println("There is no such object");
		}
	}

}
