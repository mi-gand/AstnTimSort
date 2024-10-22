package com.aston.AstnTimSort.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.command.annotation.Command;

import com.aston.AstnTimSort.repositories.DataRepository;

@Command
public class ShowCommands {

	private final DataRepository repository;

	@Autowired
	public ShowCommands(DataRepository repository) {
		this.repository = repository;
	}

	@Command(command = "show", description = "show data")
	public void show() {
		repository.getData().stream().forEach(System.out::println);
	}

}
