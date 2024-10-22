package com.aston.AstnTimSort.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.command.annotation.Command;

import com.aston.AstnTimSort.repositories.DataRepository;

@Command
public class SortCommands {

	private final DataRepository repository;

	@Autowired
	public SortCommands(DataRepository repository) {
		this.repository = repository;
	}

	@Command(command = "sort", description = "sort data")
	public void sort() {
		if (!repository.isSorted())
			repository.sort();
		System.out.println("Data has been sorted");
	}

}
