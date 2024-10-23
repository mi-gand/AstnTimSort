package com.aston.AstnTimSort.parsers;

import com.aston.AstnTimSort.models.Person;
import com.aston.AstnTimSort.models.Person.GenderEnum;

public class PersonParser implements StringParserToComparable<Person> {

	private final String PATTERN = "<Last name> <age> <male/female>";
	private final String EXAMPLE = "Ivanov 35 male";

	@Override
	public Comparable<Person> parse(String input) {
		String[] substrings = input.trim().split("\\s+");
		if (substrings.length != 3)
			throw new IllegalArgumentException("There must be 3 parameters");
		Person.Builder builder = Person.getBuilder();
		String lastName = substrings[0];
		try {
			builder.setLastName(lastName);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Last name format is incorrect");
		}
		try {
			builder.setAge(Integer.parseInt(substrings[1]));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Age format is incorrect");
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Age must be greater then zero");
		}
		try {
			builder.setGender(GenderEnum.valueOf(substrings[2].toUpperCase()));
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Gender format is incorrect");
		}
		return builder.build();
	}

	@Override
	public String getPattern() {
		return PATTERN;
	}

	public String getInputExample() {
		return EXAMPLE;
	}

}
