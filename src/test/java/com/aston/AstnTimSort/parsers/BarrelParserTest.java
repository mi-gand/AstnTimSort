package com.aston.AstnTimSort.parsers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import com.aston.AstnTimSort.models.Barrel;
import com.aston.AstnTimSort.models.Barrel.MaterialEnum;

public class BarrelParserTest {

	private BarrelParser parser = new BarrelParser();

	@RepeatedTest(100)
	public void shouldGiveParsableInputExample() {
		String randomInputExample = parser.getInputExample();
		assertDoesNotThrow(() -> parser.parse(randomInputExample));
	}

	@Test
	public void shouldConvertObjectToStringThatCanBeParsedToInitialObject() {
		Barrel person = Barrel.getBuilder().setAmount(50.0).setStoredMaterial("ink")
				.setWhichItIsMade(MaterialEnum.METAL).build();
		String personAsParsableString = parser.getParsableRepresentation(person);
		Barrel restoredBarrel = (Barrel) parser.parse(personAsParsableString);
		assertThat(person.compareTo(restoredBarrel)).isEqualTo(0);
	}

	@Test
	public void shoulReturnRequiredPattern() {
		assertThat(parser.getPattern()).isEqualTo("<Amount> <Stored Material> <Which keg is made>");
	}

	@Test
	public void shouldThrowExceptionIfInputStringIsIncompleate() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> parser.parse("50.0 ink"));
		assertThat(thrown.getMessage()).isEqualTo("There must be 3 parameters");
	}

	@Test
	public void shouldThrowExceptionIfAmountFormatIsIncorrect() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> parser.parse("5.0.0 ink metal"));
		assertThat(thrown.getMessage()).isEqualTo("Amount format is incorrect");
	}

	@Test
	public void shouldThrowExceptionIfAmountIsNotGreaterThanZero() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> parser.parse("0.0 ink metal"));
		assertThat(thrown.getMessage()).isEqualTo("Amount must be greater than zero");
	}

	@Test
	public void shouldThrowExceptionIfWhichItIsMadeFormatIsIncorrect() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> parser.parse("50.0 ink asdasd"));
		assertThat(thrown.getMessage()).isEqualTo("Which keg is made format is incorrect");
	}
}
