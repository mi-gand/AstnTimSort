package com.aston.AstnTimSort.parsers;

import com.aston.AstnTimSort.models.Animal;
import com.aston.AstnTimSort.models.Animal.AnimalTypeEnum;
import com.aston.AstnTimSort.models.Animal.EyeColorEnum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnimalFromFileParser implements StringFromFileParserToComparable<Animal> {

	private final String PATTERN = "Animal [type=%s, eyeColor=%s, withWool=%s]";
	private Pattern typeNamePattern = Pattern.compile("Animal[\\s\\[ | \\[]");
	private Pattern typePattern = Pattern.compile("type=(.*?)[\\,|\\]]");
	private Pattern eyeColorPattern = Pattern.compile("eyeColor=(.*?)[\\,|\\]]");
	private Pattern withWoolPattern = Pattern.compile("withWool=(.*?)[\\,|\\]]");

	@Override
	public Comparable<Animal> parse(String input, Collection<String> errorMessages) {
		if (errorMessages == null)
			errorMessages = new ArrayList<String>();

		Matcher typeNameMatcher = typeNamePattern.matcher(input);
		if (!typeNameMatcher.find()) {
			errorMessages.add("Incorrect object type");
			return null;
		}

		Matcher typeMatcher = typePattern.matcher(input);
		Matcher eyeColorMatcher = eyeColorPattern.matcher(input);
		Matcher withWoolMatcher = withWoolPattern.matcher(input);
		if (!typeMatcher.find() || !eyeColorMatcher.find() || !withWoolMatcher.find()) {
			errorMessages.add("Incorrect format");
			return null;
		}

		String typeString = typeMatcher.group(1);
		String eyeColorString = eyeColorMatcher.group(1);
		String withWoolString = withWoolMatcher.group(1);

		Animal.Builder builder = Animal.getBuilder();
		setType(typeString, builder, errorMessages);
		setEyeColor(eyeColorString, builder, errorMessages);
		setWithWool(withWoolString, builder, errorMessages);
		return errorMessages.size() > 0 ? null : builder.build();
	}

	@Override
	public String getParsableRepresentation(Comparable<?> obj) {
		Animal animal = (Animal) obj;
		return String.format(PATTERN, animal.getType(), animal.getEyeColor(), animal.isWithWool());
	}

	private void setType(String typeSting, Animal.Builder builder,
			Collection<String> errorMessages) {
		try {
			builder.setType(AnimalTypeEnum.valueOf(typeSting.trim().toUpperCase()));
		} catch (IllegalArgumentException e) {
			errorMessages.add("Type format is incorrect");
		}
	}

	private void setEyeColor(String eyeColorString, Animal.Builder builder,
			Collection<String> errorMessages) {
		try {
			builder.setEyeColor(EyeColorEnum.valueOf(eyeColorString.trim().toUpperCase()));
		} catch (NumberFormatException e) {
			errorMessages.add("Eye color format is incorrect");
		}
	}

	private void setWithWool(String withWoolString, Animal.Builder builder,
			Collection<String> errorMessages) {
		try {
			builder.setWool(Boolean.parseBoolean(withWoolString.trim()));
		} catch (IllegalArgumentException e) {
			errorMessages.add("Wool format is incorrect");
		}
	}

}
