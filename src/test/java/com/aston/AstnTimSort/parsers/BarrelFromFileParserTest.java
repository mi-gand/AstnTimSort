package com.aston.AstnTimSort.parsers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.aston.AstnTimSort.models.Barrel;
import com.aston.AstnTimSort.models.Barrel.MaterialEnum;

public class BarrelFromFileParserTest {

	BarrelFromFileParser parser = new BarrelFromFileParser();

	@Test
	public void parserShouldConvertObjectToStringThatCanBeParsedToInitialObject() {
		Barrel barrel = Barrel.getBuilder().setAmount(30.0).setStoredMaterial("ink")
				.setWhichItIsMade(MaterialEnum.METAL).build();
		String barrelAsParsableString = parser.getParsableRepresentation(barrel);
		List<String> errorMessages = new ArrayList<>();
		Barrel restoredBarrel = (Barrel) parser.parse(barrelAsParsableString, errorMessages);
		assertThat(errorMessages.size()).isEqualTo(0);
		assertThat(barrel.compareTo(restoredBarrel)).isEqualTo(0);
	}

	@Test
	public void parserShouldAddErrorMessageIfInputStringDescribeInvalidObjectType() {
		String input = "Animal [amount=30.0, storedMaterial=ink, whichItIsMade=METAL]";
		List<String> errorMessages = new ArrayList<>();
		Barrel barrel = (Barrel) parser.parse(input, errorMessages);
		assertThat(barrel).isEqualTo(null);
		assertThat(errorMessages.size() > 0).isEqualTo(true);
		assertThat(errorMessages.get(0)).isEqualTo("Incorrect object type");
	}

	@Test
	public void parserShouldAddErrorMessageIfInputStringDoesntHaveAmountField() {
		String input = "Barrel [aloud=30.0, storedMaterial=ink, whichItIsMade=METAL]";
		List<String> errorMessages = new ArrayList<>();
		Barrel barrel = (Barrel) parser.parse(input, errorMessages);
		assertThat(barrel).isEqualTo(null);
		assertThat(errorMessages.size() > 0).isEqualTo(true);
		assertThat(errorMessages.get(0)).isEqualTo("Incorrect format");
	}

	@Test
	public void parserShouldAddErrorMessageIfInputStringDoesntHaveStoredMaterialField() {
		String input = "Barrel [amount=30.0, stored=ink, whichItIsMade=METAL]";
		List<String> errorMessages = new ArrayList<>();
		Barrel barrel = (Barrel) parser.parse(input, errorMessages);
		assertThat(barrel).isEqualTo(null);
		assertThat(errorMessages.size() > 0).isEqualTo(true);
		assertThat(errorMessages.get(0)).isEqualTo("Incorrect format");
	}

	@Test
	public void parserShouldAddErrorMessageIfInputStringDoesntHaveWhichItIsMadeField() {
		String input = "Barrel [amount=30.0, storedMaterial=ink, asd=METAL]";
		List<String> errorMessages = new ArrayList<>();
		Barrel barrel = (Barrel) parser.parse(input, errorMessages);
		assertThat(barrel).isEqualTo(null);
		assertThat(errorMessages.size() > 0).isEqualTo(true);
		assertThat(errorMessages.get(0)).isEqualTo("Incorrect format");
	}
	
	@Test
	public void parserShouldAddErrorMessageIfInputStringHasInvalidAmountFormat() {
		String input = "Barrel [amount=3.0.0, storedMaterial=ink, whichItIsMade=METAL]";
		List<String> errorMessages = new ArrayList<>();
		Barrel barrel = (Barrel) parser.parse(input, errorMessages);
		assertThat(barrel).isEqualTo(null);
		assertThat(errorMessages.size() > 0).isEqualTo(true);
		assertThat(errorMessages.get(0)).isEqualTo("Amount format is incorrect");
	}

	@Test
	public void parserShouldAddErrorMessageIfInputStringHasAmountNotGreaterThanZero() {
		String input = "Barrel [amount=-5.0, storedMaterial=ink, whichItIsMade=METAL]";
		List<String> errorMessages = new ArrayList<>();
		Barrel barrel = (Barrel) parser.parse(input, errorMessages);
		assertThat(barrel).isEqualTo(null);
		assertThat(errorMessages.size() > 0).isEqualTo(true);
		assertThat(errorMessages.get(0)).isEqualTo("Amount must be greater than zero");
	}
	
	@Test
	public void parserShouldAddErrorMessageIfInputStringHasInvalidWhichItIsMadeFormat() {
		String input = "Barrel [amount=30.0, storedMaterial=ink, whichItIsMade=AIR]";
		List<String> errorMessages = new ArrayList<>();
		Barrel barrel = (Barrel) parser.parse(input, errorMessages);
		assertThat(barrel).isEqualTo(null);
		assertThat(errorMessages.size() > 0).isEqualTo(true);
		assertThat(errorMessages.get(0)).isEqualTo("Which-it-is-made format is incorrect");
	}

}
