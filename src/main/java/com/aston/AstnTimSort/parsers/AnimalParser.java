package com.aston.AstnTimSort.parsers;

import com.aston.AstnTimSort.models.Animal;
import java.util.Random;

public class AnimalParser implements StringParserToComparable<Animal>{

    private final String PATTERN = "<Animal type> <Eye color> <Have wool>";

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
            throw new IllegalArgumentException('"' + "Animal with wool?" + '"' + " format is incorrect");
        }
        return builder.build();
    }

    @Override
    public String getPattern() {
        return PATTERN;
    }

    @Override
    public String getInputExample() {
        Random random = new Random();
        String animalToString = "";

        Integer numberOfAnimalTypeEnum = random.nextInt(7);
        Animal.AnimalTypeEnum type = Animal.AnimalTypeEnum.values()[numberOfAnimalTypeEnum];
        animalToString += type + " ";

        Integer numberOfEyeColorEnum= random.nextInt(6);
        Animal.EyeColorEnum eyeColor = Animal.EyeColorEnum.values()[numberOfEyeColorEnum];
        animalToString += eyeColor + " ";

        Boolean isWithWool = random.nextBoolean();
        animalToString += isWithWool;

        return animalToString;
    }

    @Override
    public String getParsableRepresentation(Comparable<?> obj) {
        Animal animal = (Animal) obj;
        return animal.getType() + " " + animal.getEyeColor() + " " + animal.isWithWool();
    }
}
