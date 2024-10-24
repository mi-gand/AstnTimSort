package com.aston.AstnTimSort;

import com.aston.AstnTimSort.parsers.PersonParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.command.annotation.CommandScan;

@SpringBootApplication
@CommandScan
public class Application {

	public static void main(String[] args) {
		//SpringApplication.run(Application.class, args);
		for (int i = 0; i < 10; i++) {
			String random = new PersonParser().getInputExample();
			System.out.println(random);
		}
	}

}
