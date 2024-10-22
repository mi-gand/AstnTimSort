package com.aston.AstnTimSort.commands;

import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

@Command
public class HelloCommand {
	

	@Command(command = "hello", description = "just hello:)")
	public String helloTo(@Option(defaultValue = "world") String name) {
		return "Hello, " + name + "!";
	}

}
