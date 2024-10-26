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
		if(!repository.hasData()) {
			System.out.println("There are no data");
			return;
		}
		repository.sort();
		System.out.println("Data has been sorted");
	}
	
	@Command(command = "strangeSort", description = "strangely sort data")
	public void strangeSort() {
		if(!repository.hasData()) {
			System.out.println("There are no data");
			return;
		}
		try {
		repository.strangeSort();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return;
		}
		System.out.println("Data has been strangely sorted");
	}

}
