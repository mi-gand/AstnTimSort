package com.aston.AstnTimSort.parsers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import com.aston.AstnTimSort.models.Animal;
import com.aston.AstnTimSort.models.Animal.AnimalTypeEnum;
import com.aston.AstnTimSort.models.Animal.EyeColorEnum;

public class AnimalParserTest {

	private AnimalParser parser = new AnimalParser();

	@RepeatedTest(100)
	public void shouldGiveParsableInputExample() {
		String randomInputExample = parser.getInputExample();
		assertDoesNotThrow(() -> parser.parse(randomInputExample));
	}

	@Test
	public void shouldConvertObjectToStringThatCanBeParsedToInitialObject() {
		Animal animal = Animal.getBuilder().setType(AnimalTypeEnum.MAMMAL)
				.setEyeColor(EyeColorEnum.GREEN).setWool(true).build();
		String animalAsParsableString = parser.getParsableRepresentation(animal);
		Animal restoredAnimal = (Animal) parser.parse(animalAsParsableString);
		assertThat(animal.compareTo(restoredAnimal)).isEqualTo(0);
	}

	@Test
	public void shoulReturnRequiredPattern() {
		assertThat(parser.getPattern()).isEqualTo("<Animal type> <Eye color> <Have fur>");
	}

	@Test
	public void shouldThrowExceptionIfInputStringIsIncompleate() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> parser.parse("fish grey"));
		assertThat(thrown.getMessage()).isEqualTo("There must be 3 parameters");
	}

	@Test
	public void shouldThrowExceptionIfTypeFormatIsIncorrect() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> parser.parse("fist grey false"));
		assertThat(thrown.getMessage()).isEqualTo("Animal type format is incorrect");
	}

	@Test
	public void shouldThrowExceptionIfEyeColorFormatIsIncorrect() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> parser.parse("fish grass false"));
		assertThat(thrown.getMessage()).isEqualTo("Eye color format is incorrect");
	}

	@Test
	public void shouldThrowExceptionIfWithWoolFormatIsIncorrect() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> parser.parse("fish gray asdasd"));
		assertThat(thrown.getMessage()).isEqualTo("\"Animal with fur?\" format is incorrect");
	}
}
