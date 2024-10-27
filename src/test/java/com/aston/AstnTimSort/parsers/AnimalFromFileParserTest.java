package com.aston.AstnTimSort.parsers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.aston.AstnTimSort.models.Animal;
import com.aston.AstnTimSort.models.Animal.AnimalTypeEnum;
import com.aston.AstnTimSort.models.Animal.EyeColorEnum;

public class AnimalFromFileParserTest {

	AnimalFromFileParser parser = new AnimalFromFileParser();

	@Test
	public void parserShouldConvertObjectToStringThatCanBeParsedToInitialObject() {
		Animal animal = Animal.getBuilder().setType(AnimalTypeEnum.AMPHIBIAN).setEyeColor(EyeColorEnum.BLACK)
				.setWool(false).build();
		String animalAsParsableString = parser.getParsableRepresentation(animal);
		List<String> errorMessages = new ArrayList<>();
		Animal restoredAnimal = (Animal) parser.parse(animalAsParsableString, errorMessages);
		assertThat(errorMessages.size()).isEqualTo(0);
		assertThat(animal.compareTo(restoredAnimal)).isEqualTo(0);
	}

	@Test
	public void parserShouldAddErrorMessageIfInputStringDescribeInvalidObjectType() {
		String input = "Barrel [amount=30.0, storedMaterial=ink, whichItIsMade=METAL]";
		List<String> errorMessages = new ArrayList<>();
		Animal animal = (Animal) parser.parse(input, errorMessages);
		assertThat(animal).isEqualTo(null);
		assertThat(errorMessages.size() > 0).isEqualTo(true);
		assertThat(errorMessages.get(0)).isEqualTo("Incorrect object type");
	}

	@Test
	public void parserShouldAddErrorMessageIfInputStringDoesntHaveTypeField() {
		String input = "Animal [tip=drink water, eyeColor=BLACK, withWool=true]";
		List<String> errorMessages = new ArrayList<>();
		Animal animal = (Animal) parser.parse(input, errorMessages);
		assertThat(animal).isEqualTo(null);
		assertThat(errorMessages.size() > 0).isEqualTo(true);
		assertThat(errorMessages.get(0)).isEqualTo("Incorrect format");
	}

	@Test
	public void parserShouldAddErrorMessageIfInputStringDoesntHaveEyeColorField() {
		String input = "Animal [type=MAMMAL, eyeSize=BIG, withWool=true]";
		List<String> errorMessages = new ArrayList<>();
		Animal animal = (Animal) parser.parse(input, errorMessages);
		assertThat(animal).isEqualTo(null);
		assertThat(errorMessages.size() > 0).isEqualTo(true);
		assertThat(errorMessages.get(0)).isEqualTo("Incorrect format");
	}

	@Test
	public void parserShouldAddErrorMessageIfInputStringDoesntHaveWithWoolField() {
		String input = "Animal [type=MAMMAL, eyeColor=BLACK, withCool=true]";
		List<String> errorMessages = new ArrayList<>();
		Animal animal = (Animal) parser.parse(input, errorMessages);
		assertThat(animal).isEqualTo(null);
		assertThat(errorMessages.size() > 0).isEqualTo(true);
		assertThat(errorMessages.get(0)).isEqualTo("Incorrect format");
	}
	
	@Test
	public void parserShouldAddErrorMessageIfInputStringHasInvalidTypeFormat() {
		String input = "Animal [type=Car, eyeColor=BLACK, withWool=true]";
		List<String> errorMessages = new ArrayList<>();
		Animal animal = (Animal) parser.parse(input, errorMessages);
		assertThat(animal).isEqualTo(null);
		assertThat(errorMessages.size() > 0).isEqualTo(true);
		assertThat(errorMessages.get(0)).isEqualTo("Type format is incorrect");
	}

	@Test
	public void parserShouldAddErrorMessageIfInputStringHasInvalidEyeColorFormat() {
		String input = "Animal [type=MAMMAL, eyeColor=Grass, withWool=true]";
		List<String> errorMessages = new ArrayList<>();
		Animal animal = (Animal) parser.parse(input, errorMessages);
		assertThat(animal).isEqualTo(null);
		assertThat(errorMessages.size() > 0).isEqualTo(true);
		assertThat(errorMessages.get(0)).isEqualTo("Eye color format is incorrect");
	}
	
	@Test
	public void parserShouldAddErrorMessageIfInputStringHasInvalidWithWoolFormat() {
		String input = "Animal [type=MAMMAL, eyeColor=BLACK, withWool=asd]";
		List<String> errorMessages = new ArrayList<>();
		Animal animal = (Animal) parser.parse(input, errorMessages);
		assertThat(animal).isEqualTo(null);
		assertThat(errorMessages.size() > 0).isEqualTo(true);
		assertThat(errorMessages.get(0)).isEqualTo("Wool format is incorrect");
	}
	
	@Test
	public void parserShouldAddErrorMessageIfTypeAndWithWoolAreIncompatible() {
		String input = "Animal [type=SHELLFISH, eyeColor=WITHOUT_EYE, withWool=true]";
		List<String> errorMessages = new ArrayList<>();
		Animal animal = (Animal) parser.parse(input, errorMessages);
		assertThat(animal).isEqualTo(null);
		assertThat(errorMessages.size() > 0).isEqualTo(true);
		assertThat(errorMessages.get(0)).isEqualTo("This type cannot have fur");
	}
	
	@Test
	public void parserShouldAddErrorMessageIfTypeAndEyeColorAreIncompatible() {
		String input = "Animal [type=SHELLFISH, eyeColor=BLACK, withWool=false]";
		List<String> errorMessages = new ArrayList<>();
		Animal animal = (Animal) parser.parse(input, errorMessages);
		assertThat(animal).isEqualTo(null);
		assertThat(errorMessages.size() > 0).isEqualTo(true);
		assertThat(errorMessages.get(0)).isEqualTo("This type cannot have eyes");
	}

}
