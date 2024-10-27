package com.aston.AstnTimSort.parsers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.aston.AstnTimSort.models.Person;
import com.aston.AstnTimSort.models.Person.GenderEnum;

public class PersonFromFileParserTest {

	PersonFromFileParser parser= new PersonFromFileParser();

	@Test
	public void parserShouldConvertObjectToStringThatCanBeParsedToInitialObject() {
		Person person = Person.getBuilder().setLastName("Ivanov").setAge(30)
				.setGender(GenderEnum.MALE).build();
		String personAsParsableString = parser.getParsableRepresentation(person);
		List<String> errorMessages = new ArrayList<>();
		Person restoredPerson = (Person) parser.parse(personAsParsableString, errorMessages);
		assertThat(errorMessages.size()).isEqualTo(0);
		assertThat(person.compareTo(restoredPerson)).isEqualTo(0);
	}

	@Test
	public void parserShouldAddErrorMessageIfInputStringDescribeInvalidObjectType() {
		String input = "Animal [lastName=Ivanov, age=30, gender=MALE]";
		List<String> errorMessages = new ArrayList<>();
		Person person = (Person) parser.parse(input, errorMessages);
		assertThat(person).isEqualTo(null);
		assertThat(errorMessages.size() > 0).isEqualTo(true);
		assertThat(errorMessages.get(0)).isEqualTo("Incorrect object type");
	}

	@Test
	public void parserShouldAddErrorMessageIfInputStringDoesntHaveLastNameField() {
		String input = "Person [firstName=Ivanov, age=30, gender=MALE]";
		List<String> errorMessages = new ArrayList<>();
		Person person = (Person) parser.parse(input, errorMessages);
		assertThat(person).isEqualTo(null);
		assertThat(errorMessages.size() > 0).isEqualTo(true);
		assertThat(errorMessages.get(0)).isEqualTo("Incorrect format");
	}

	@Test
	public void parserShouldAddErrorMessageIfInputStringDoesntHaveAgeField() {
		String input = "Person [lastName=Ivanov, aj=30, gender=MALE]";
		List<String> errorMessages = new ArrayList<>();
		Person person = (Person) parser.parse(input, errorMessages);
		assertThat(person).isEqualTo(null);
		assertThat(errorMessages.size() > 0).isEqualTo(true);
		assertThat(errorMessages.get(0)).isEqualTo("Incorrect format");
	}

	@Test
	public void parserShouldAddErrorMessageIfInputStringDoesntHaveGenderField() {
		String input = "Person [lastName=Ivanov, age=30, ganger=MALE]";
		List<String> errorMessages = new ArrayList<>();
		Person person = (Person) parser.parse(input, errorMessages);
		assertThat(person).isEqualTo(null);
		assertThat(errorMessages.size() > 0).isEqualTo(true);
		assertThat(errorMessages.get(0)).isEqualTo("Incorrect format");
	}
	
	@Test
	public void parserShouldAddErrorMessageIfInputStringHasInvalidLastNameFormat() {
		String input = "Person [lastName=Iva nov, age=30, gender=MALE]";
		List<String> errorMessages = new ArrayList<>();
		Person person = (Person) parser.parse(input, errorMessages);
		assertThat(person).isEqualTo(null);
		assertThat(errorMessages.size() > 0).isEqualTo(true);
		assertThat(errorMessages.get(0)).isEqualTo("Last name format is incorrect");
	}
	
	@Test
	public void parserShouldAddErrorMessageIfInputStringHasInvalidAgeFormat() {
		String input = "Person [lastName=Ivanov, age=2.5, gender=MALE]";
		List<String> errorMessages = new ArrayList<>();
		Person person = (Person) parser.parse(input, errorMessages);
		assertThat(person).isEqualTo(null);
		assertThat(errorMessages.size() > 0).isEqualTo(true);
		assertThat(errorMessages.get(0)).isEqualTo("Age format is incorrect");
	}

	@Test
	public void parserShouldAddErrorMessageIfInputStringHasAgeNotGreaterThanZero() {
		String input = "Person [lastName=Ivanov, age=-30, gender=MALE]";
		List<String> errorMessages = new ArrayList<>();
		Person person = (Person) parser.parse(input, errorMessages);
		assertThat(person).isEqualTo(null);
		assertThat(errorMessages.size() > 0).isEqualTo(true);
		assertThat(errorMessages.get(0)).isEqualTo("Age must be greater than zero");
	}
	
	@Test
	public void parserShouldAddErrorMessageIfInputStringHasInvalidGenderFormat() {
		String input = "Person [lastName=Ivanov, age=30, gender=non-binary]";
		List<String> errorMessages = new ArrayList<>();
		Person person = (Person) parser.parse(input, errorMessages);
		assertThat(person).isEqualTo(null);
		assertThat(errorMessages.size() > 0).isEqualTo(true);
		assertThat(errorMessages.get(0)).isEqualTo("Gender format is incorrect");
	}

}
