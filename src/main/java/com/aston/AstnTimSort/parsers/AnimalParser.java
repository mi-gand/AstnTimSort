package com.aston.AstnTimSort.parsers;

import com.aston.AstnTimSort.models.Animal;
import com.aston.AstnTimSort.models.Animal.AnimalTypeEnum;
import com.aston.AstnTimSort.models.Animal.EyeColorEnum;

import java.util.Random;

public class AnimalParser implements StringParserToComparable<Animal> {

	private final String PATTERN = "<Animal type> <Eye color> <Have fur>";
	private static final Random random = new Random();

	@Override
	public Comparable<Animal> parse(String input) {
		String[] substrings = input.trim().split("\\s+");
		if (substrings.length != 3)
			throw new IllegalArgumentException("There must be 3 parameters");
		Animal.Builder builder = Animal.getBuilder();
		String type = substrings[0];
		try {
			builder.setType(Animal.AnimalTypeEnum.valueOf(type.toUpperCase()));
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Animal type format is incorrect");
		}
		try {
			builder.setEyeColor(Animal.EyeColorEnum.valueOf(substrings[1].toUpperCase()));
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Eye color format is incorrect");
		}
		try {
			builder.setWool(Boolean.valueOf(substrings[2]));
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(
					'"' + "Animal with fur?" + '"' + " format is incorrect");
		}
		return builder.build();
	}

	@Override
	public String getPattern() {
		return PATTERN;
	}

	@Override
	public String getInputExample() {
		int typeVariantsCount = AnimalTypeEnum.values().length;
		Animal.AnimalTypeEnum type = AnimalTypeEnum.values()[random.nextInt(typeVariantsCount)];

		int eyeColorVariantsCount = EyeColorEnum.values().length;
		EyeColorEnum eyeColor;
		if (type.canHaveEyes()) {
			if (type.canBeWithoutEyes()) {
				eyeColor = EyeColorEnum.values()[random.nextInt(eyeColorVariantsCount)];
			} else {
				eyeColor = EyeColorEnum.values()[random.nextInt(1, eyeColorVariantsCount)];
			}
		} else {
			eyeColor = EyeColorEnum.WITHOUT_EYE;
		}

		boolean withFur;
		if (!type.canHasFur()) {
			withFur = false;
		} else if (!type.canBeWithoutFur()) {
			withFur = true;
		} else {
			withFur = random.nextBoolean();
		}

		return String.format("%s %s %s", type, eyeColor, withFur);
	}

	@Override
	public String getParsableRepresentation(Comparable<?> obj) {
		Animal animal = (Animal) obj;
		return animal.getType() + " " + animal.getEyeColor() + " " + animal.isWithWool();
	}
}
