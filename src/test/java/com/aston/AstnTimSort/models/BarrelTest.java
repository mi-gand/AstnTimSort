package com.aston.AstnTimSort.models;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.aston.AstnTimSort.models.Barrel.MaterialEnum;

public class BarrelTest {
	Barrel.Builder builder;
	
	@BeforeEach
	public void initializeBuilder() {
		builder = Barrel.getBuilder();
	}
	
	@Test
	public void builderWithIncompliteFieldsInitializationShouldThrowExceptionWhenBuild() {
		assertThrows(RuntimeException.class, () -> builder.build());
		builder.setAmount(50.0);
		assertThrows(RuntimeException.class, () -> builder.build());
		builder.setStoredMaterial("ink");
		assertThrows(RuntimeException.class, () -> builder.build());
		builder.setWhichItIsMade(MaterialEnum.METAL);
		assertDoesNotThrow(() -> builder.build());
	}
	
	@Test
	public void builderShouldThrowExeptionIfAmountNotGreaterThanZero() {
		assertThrows(IllegalArgumentException.class, () -> builder.setAmount(0.0));
		assertThrows(IllegalArgumentException.class, () -> builder.setAmount(-5.0));
	}
	
}
