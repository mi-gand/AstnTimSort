package com.aston.AstnTimSort.parsers;

import java.security.InvalidParameterException;

import com.aston.AstnTimSort.models.Person;
import com.aston.AstnTimSort.models.Person.GenderEnum;


public class PersonParser implements StringParserToComparable<Person> {
	
	private final String PATTERN = "<Last name> <age> <male/female>";

	@Override
	public Comparable<Person> parse(String input) {
		try {
		String[] substrings = input.trim().split("\\s+");
		int age = Integer.parseInt(substrings[1]);
		GenderEnum gender = GenderEnum.valueOf(substrings[2].toUpperCase());
		return new Person(substrings[0], age, gender);
		} catch (Exception e) {
			throw new InvalidParameterException();
		}
	}

	@Override
	public String getPattern() {
		return PATTERN;
	}

}
