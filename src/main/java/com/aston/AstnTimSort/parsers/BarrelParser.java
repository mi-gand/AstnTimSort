package com.aston.AstnTimSort.parsers;

import com.aston.AstnTimSort.models.Barrel;
import java.text.DecimalFormat;
import java.util.Random;

public class BarrelParser implements StringParserToComparable<Barrel>{

    private final String PATTERN = "<Amount> <Stored Material> <Which keg is made>";
    private final String EXAMPLE = "216,5 wine wooden";

    @Override
    public Comparable<Barrel> parse(String input) {
        String[] substrings = input.trim().split("\\s+");
        if (substrings.length != 3)
            throw new IllegalArgumentException("There must be 3 parameters");
        Barrel.Builder builder = Barrel.getBuilder();
        String amount = substrings[0];
        try {
            builder.setAmount(Double.valueOf(amount));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Amount format is incorrect");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Amount must be greater then zero");
        }
        try {
            builder.setStoredMaterial(String.valueOf(substrings[1]));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Stored material format is incorrect");
        }
        try {
            builder.setWhichItIsMade(Barrel.MaterialEnum.valueOf(substrings[2].toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Which keg is made format is incorrect");
        }
        return builder.build();
    }

    @Override
    public String getPattern() { return PATTERN; }

    @Override
    public String getInputExample() {
        Random random = new Random();
        String barrelToString = "";
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        Double amount = 0.3 + (400 - 0.3) * random.nextDouble();
        barrelToString += decimalFormat.format(amount) + " ";

        Integer numberOfMaterial = random.nextInt(50) - 1;
        barrelToString += Barrel.getMaterial(numberOfMaterial) + " ";

        Integer numberOfMaterialEnum = random.nextInt(3);
        Barrel.MaterialEnum material = Barrel.MaterialEnum.values()[numberOfMaterialEnum];
        barrelToString += Barrel.MaterialEnum.valueOf(String.valueOf(material));

        return barrelToString;
    }

    @Override
    public String getParsableRepresentation(Comparable<?> obj) {
        Barrel barrel = (Barrel) obj;
        return barrel.getAmount() + " " + barrel.getStoredMaterial() + " " + barrel.getWhichItIsMade();
    }
}