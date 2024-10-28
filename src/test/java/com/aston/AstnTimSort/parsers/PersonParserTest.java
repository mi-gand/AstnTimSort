package com.aston.AstnTimSort.parsers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import com.aston.AstnTimSort.models.Person;
import com.aston.AstnTimSort.models.Person.GenderEnum;

public class PersonParserTest {

	private PersonParser parser = new PersonParser();

	@RepeatedTest(100)
	public void shouldGiveParsableInputExample() {
		String randomInputExample = parser.getInputExample();
		assertDoesNotThrow(() -> parser.parse(randomInputExample));
	}

	@Test
	public void shouldConvertObjectToStringThatCanBeParsedToInitialObject() {
		Person person = Person.getBuilder().setLastName("Ivanov").setAge(30)
				.setGender(GenderEnum.MALE).build();
		String personAsParsableString = parser.getParsableRepresentation(person);
		Person restoredPerson = (Person) parser.parse(personAsParsableString);
		assertThat(person.compareTo(restoredPerson)).isEqualTo(0);
	}
	
	@Test
	public void shoulReturnRequiredPattern() {
		assertThat(parser.getPattern()).isEqualTo("<Last name> <age> <male/female>");
	}

	@Test
	public void shouldThrowExceptionIfInputStringIsIncompleate() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> parser.parse("Ivanov 30"));
		assertThat(thrown.getMessage()).isEqualTo("There must be 3 parameters");
	}

	@Test
	public void shouldThrowExceptionIfLastNameFormatIsIncorrect() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> parser.parse("Iva_nov 30 male"));
		assertThat(thrown.getMessage()).isEqualTo("Last name format is incorrect");
	}
	
	@Test
	public void shouldThrowExceptionIfAgeFormatIsIncorrect() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> parser.parse("Ivanov 3.5 male"));
		assertThat(thrown.getMessage()).isEqualTo("Age format is incorrect");
	}
	
	@Test
	public void shouldThrowExceptionIfAgeIsNotGreaterThanZero() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> parser.parse("Ivanov -5 male"));
		assertThat(thrown.getMessage()).isEqualTo("Age must be greater than zero");
	}
	
	@Test
	public void shouldThrowExceptionIfGenderFormalIsIncorrect() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> parser.parse("Ivanov 30 frog"));
		assertThat(thrown.getMessage()).isEqualTo("Gender format is incorrect");
	}
}
