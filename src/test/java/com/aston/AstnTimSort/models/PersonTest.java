package com.aston.AstnTimSort.models;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.aston.AstnTimSort.models.Person.GenderEnum;

public class PersonTest {
	Person.Builder builder;

	@BeforeEach
	public void initializeBuilder() {
		builder = Person.getBuilder();
	}
	
	@Test
	public void builderWithIncompliteFieldsInitializationShouldThrowExceptionWhenBuild() {
		assertThrows(RuntimeException.class, () -> builder.build());
		builder.setLastName("Ivanov");
		assertThrows(RuntimeException.class, () -> builder.build());
		builder.setAge(30);
		assertThrows(RuntimeException.class, () -> builder.build());
		builder.setGender(GenderEnum.MALE);
		assertDoesNotThrow(() -> builder.build());
	}

	@Test
	public void builderShouldThrowExeptionIfAgeNotGreaterThanZero() {
		assertThrows(IllegalArgumentException.class, () -> builder.setAge(0));
	}

	@Test
	public void builderShouldThrowExeptionIfLastNameHasInvalidSymbols() {
		assertThrows(IllegalArgumentException.class, () -> builder.setLastName("Ivanov123"));
		assertThrows(IllegalArgumentException.class, () -> builder.setLastName("Iva nov"));
		assertThrows(IllegalArgumentException.class, () -> builder.setLastName("Iva&nov"));
		assertThrows(IllegalArgumentException.class, () -> builder.setLastName("Iva?nov"));
		assertThrows(IllegalArgumentException.class, () -> builder.setLastName("I/va/nov"));
		assertThrows(IllegalArgumentException.class, () -> builder.setLastName("d'Ivanov"));
	}

}
