package com.aston.AstnTimSort.parsers;

import com.aston.AstnTimSort.models.Person;
import com.aston.AstnTimSort.models.Person.GenderEnum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PersonFromFileParser implements StringFromFileParserToComparable<Person> {

	private final String PATTERN = "Person [lastName=%s, age=%d, gender=%s]";
	private final Pattern typeNamePattern = Pattern.compile("Person[\\s\\[ | \\[]");
	private final Pattern lastNamePattern = Pattern.compile("lastName=(.*?)[\\,|\\]]");
	private final Pattern agePattern = Pattern.compile("age=(.*?)[\\,|\\]]");
	private final Pattern genderPattern = Pattern.compile("gender=(.*?)[\\,|\\]]");

	@Override
	public Comparable<Person> parse(String input, Collection<String> errorMessages) {
		if (errorMessages == null)
			errorMessages = new ArrayList<String>();

		Matcher typeMatcher = typeNamePattern.matcher(input);
		if (!typeMatcher.find()) {
			errorMessages.add("Incorrect object type");
			return null;
		}

		Matcher lastNameMatcher = lastNamePattern.matcher(input);
		Matcher ageMatcher = agePattern.matcher(input);
		Matcher genderMatcher = genderPattern.matcher(input);
		if (!lastNameMatcher.find() || !ageMatcher.find() || !genderMatcher.find()) {
			errorMessages.add("Incorrect format");
			return null;
		}

		String lastName = lastNameMatcher.group(1);
		String ageString = ageMatcher.group(1);
		String genderString = genderMatcher.group(1);

		Person.Builder builder = Person.getBuilder();
		setBuilderLastName(lastName, builder, errorMessages);
		setBuilderAge(ageString, builder, errorMessages);
		setBuilderGender(genderString, builder, errorMessages);
		return errorMessages.size() > 0 ? null : build(builder, errorMessages);
	}

	@Override
	public String getParsableRepresentation(Comparable<?> obj) {
		Person person = (Person) obj;
		return String.format(PATTERN, person.getLastName(), person.getAge(), person.getGender());
	}

	private void setBuilderLastName(String lastName, Person.Builder builder,
			Collection<String> errorMessages) {
		try {
			builder.setLastName(lastName.trim());
		} catch (IllegalArgumentException e) {
			errorMessages.add("Last name format is incorrect");
		}
	}

	private void setBuilderAge(String ageString, Person.Builder builder,
			Collection<String> errorMessages) {
		try {
			builder.setAge(Integer.parseInt(ageString));
		} catch (NumberFormatException e) {
			errorMessages.add("Age format is incorrect");
		} catch (IllegalArgumentException e) {
			errorMessages.add("Age must be greater than zero");
		}
	}

	private void setBuilderGender(String genderString, Person.Builder builder,
			Collection<String> errorMessages) {
		try {
			builder.setGender(GenderEnum.valueOf(genderString.trim().toUpperCase()));
		} catch (IllegalArgumentException e) {
			errorMessages.add("Gender format is incorrect");
		}
	}
	
	private Person build(Person.Builder builder, Collection<String> errorMessages) {
		try {
			return builder.build();
		} catch (RuntimeException e) {
			errorMessages.add(e.getMessage());
		}
		return null;
	}

}
