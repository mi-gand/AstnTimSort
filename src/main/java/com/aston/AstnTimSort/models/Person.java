package com.aston.AstnTimSort.models;

import java.security.InvalidParameterException;

import ch.qos.logback.core.util.StringUtil;

public class Person implements Comparable<Person> {

	private final String lastName;
	private final Integer age;
	private final GenderEnum gender;

	public Person(String lastName, Integer age, GenderEnum gender) {
		this.lastName = lastName;
		this.age = age;
		this.gender = gender;
	}

	public String getLastName() {
		return lastName;
	}

	public Integer getAge() {
		return age;
	}

	public GenderEnum getGender() {
		return gender;
	}

	@Override
	public int compareTo(Person o) {
		int result = lastName.compareTo(o.getLastName());
		if (result == 0)
			result = age - o.getAge();
		if (result == 0)
			result = gender.compareTo(o.getGender());
		return result;
	}

	public static enum GenderEnum {
		MALE, FEMALE
	}

	@Override
	public String toString() {
		return "Person [lastName=" + lastName + ", age=" + age + ", gender=" + gender + "]";
	}

	static Builder getBuilder() {
		return new Builder();
	}

	static class Builder {
		public Person build(String fieldValues) {
			try {
				String[] substrings = fieldValues.trim().split("\\s+");
				int age = Integer.parseInt(substrings[1]);
				GenderEnum gender = GenderEnum
						.valueOf(StringUtil.capitalizeFirstLetter(substrings[2].toLowerCase()));
				return new Person(substrings[0], age, gender);
			} catch (Exception e) {
				throw new InvalidParameterException();
			}
		}
	}

	public static void main(String[] args) {
		System.out.println(GenderEnum.valueOf("Male".toUpperCase()));
	}

}
