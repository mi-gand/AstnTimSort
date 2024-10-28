package com.aston.AstnTimSort.models;

import java.util.Arrays;
import java.util.List;

public class Person implements Comparable<Person>, HasIntField {
	
	public static List<String> surnameMale = Arrays.asList(
			"Ivanov", "Petrov", "Sidorov", "Kuznetsov", "Smirnov",
			"Vasiliev", "Popov", "Kovalev", "Lebedev", "Mikhailov",
			"Sorokin", "Nikolaev", "Stepanov", "Frolov", "Solovyev",
			"Fyodorov", "Volkov", "Baranov", "Vorobyov", "Orlov",
			"Sidorenko", "Grigoryev", "Kolesnikov", "Tikhonov", "Pavlenko",
			"Kotenko", "Zakharov");
	public static List<String> surnameFemale = Arrays.asList(
			"Nikiforova", "Danilova", "Dorofeeva", "Selezneva", "Ilyina",
			"Vlasova", "Zotova", "Klementyeva", "Paskova", "Kharchenkova",
			"Makarevichna", "Romanova", "Kramova", "Koroleva", "Kasyanova",
			"Bogdanova", "Dementyeva", "Panteleeva", "Yumasheva", "Shtyrkova",
			"Sveshnikova"
	);

	private final String lastName;
	private final Integer age;
	private final GenderEnum gender;

	private Person(String lastName, Integer age, GenderEnum gender) {
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

	public enum GenderEnum {
		MALE, FEMALE
	}

	@Override
	public String toString() {
		return "Person [lastName=" + lastName + ", age=" + age + ", gender=" + gender + "]";
	}

	public static Builder getBuilder() {
		return new Builder();
	}

	public static class Builder {
		private String lastName;
		private Integer age;
		private GenderEnum gender;

		public Builder setLastName(String lastName) {
			if (!lastName.chars().allMatch(Character::isLetter))
				throw new IllegalArgumentException();
			this.lastName = lastName;
			return this;
		}

		public Builder setAge(Integer age) {
			if (age <= 0)
				throw new IllegalArgumentException("Age value must be greater than zero");
			this.age = age;
			return this;
		}

		public Builder setGender(GenderEnum gender) {
			this.gender = gender;
			return this;
		}

		public Person build() {
			if (lastName == null || age == null || gender == null) {
				throw new RuntimeException("Incomplete object fields initialization");
			}
			return new Person(lastName, age, gender);
		}
	}

	@Override
	public int getIntField() {
		return age;
	}

}
