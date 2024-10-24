package com.aston.AstnTimSort.models;

public class Person implements Comparable<Person> {

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
				throw new IllegalArgumentException();
			this.age = age;
			return this;
		}

		public Builder setGender(GenderEnum gender) {
			this.gender = gender;
			return this;
		}

		public Person build() {
			return new Person(lastName, age, gender);
		}
	}

}
