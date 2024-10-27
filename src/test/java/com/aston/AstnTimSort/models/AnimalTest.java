package com.aston.AstnTimSort.models;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.aston.AstnTimSort.models.Animal.AnimalTypeEnum;
import com.aston.AstnTimSort.models.Animal.EyeColorEnum;

public class AnimalTest {
	Animal.Builder builder;
	
	@BeforeEach
	public void initializeBuilder() {
		builder = Animal.getBuilder();
	}
	
	@Test
	public void builderWithIncompliteFieldsInitializationShouldThrowExceptionWhenBuild() {
		assertThrows(RuntimeException.class, () -> builder.build());
		builder.setType(AnimalTypeEnum.MAMMAL);
		assertThrows(RuntimeException.class, () -> builder.build());
		builder.setEyeColor(EyeColorEnum.BLACK);
		assertThrows(RuntimeException.class, () -> builder.build());
		builder.setWool(true);
		assertDoesNotThrow(() -> builder.build());
	}
	
	@Test
	public void builderShouldNotThrowExeption() {
		builder.setType(AnimalTypeEnum.SHELLFISH).setEyeColor(EyeColorEnum.WITHOUT_EYE).setWool(false);
		assertDoesNotThrow(() -> builder.build());
	}
	
	@Test
	public void builderShouldThrowExeptionIfTypeAndWithWoolAreIncompatible() {
		builder.setType(AnimalTypeEnum.SHELLFISH).setEyeColor(EyeColorEnum.WITHOUT_EYE).setWool(true);
		assertThrows(IllegalArgumentException.class, () -> builder.build());
	}
	
	@Test
	public void builderShouldThrowExeptionIfTypeAndEyeColorAreIncompatible() {
		builder.setType(AnimalTypeEnum.SHELLFISH).setEyeColor(EyeColorEnum.BLACK).setWool(false);
		assertThrows(IllegalArgumentException.class, () -> builder.build());
	}
	
}
