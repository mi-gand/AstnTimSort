package com.aston.AstnTimSort.parsers;

import com.aston.AstnTimSort.models.Animal;
import com.aston.AstnTimSort.models.Person;
import com.aston.AstnTimSort.models.Person.GenderEnum;

import java.util.Random;

public class PersonParser implements StringParserToComparable<Person> {

	private final String PATTERN = "<Last name> <age> <male/female>";
	private static final Random random = new Random();

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
			throw new IllegalArgumentException("Age must be greater than zero");
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

	@Override
	public String getInputExample() {
		String gender = random.nextBoolean() ? "male":"female";
		String surname = null;
		if (gender.equals("male")) {
			String surnames = Person.surnameMale.get(random.nextInt(Person.surnameMale.size()));
			surname = surnames;
		}if (gender.equals("female")){
			String surnames = Person.surnameFemale.get(random.nextInt(Person.surnameFemale.size()));
			surname = surnames;
		}
		int age = random.nextInt(90);
		return String.format("%s %d %s",surname,age,gender);
	}

	@Override
	public String getParsableRepresentation(Comparable<?> obj) {
		Person person = (Person) obj;
		return person.getLastName() + " " + person.getAge() + " " + person.getGender();
	}

}
