package com.aston.AstnTimSort.parsers;

import com.aston.AstnTimSort.models.Barrel;
import com.aston.AstnTimSort.models.Barrel.MaterialEnum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BarrelFromFileParser implements StringFromFileParserToComparable<Barrel> {

	private final String PATTERN = "Barrel [amount=%.2f, storedMaterial=%s, whichItIsMade=%s]";
	private Pattern typeNamePattern = Pattern.compile("Barrel[\\s\\[ | \\[]");
	private Pattern amountPattern = Pattern.compile("amount=(.*?)[\\,|\\]]");
	private Pattern storedMaterialPattern = Pattern.compile("storedMaterial=(.*?)[\\,|\\]]");
	private Pattern whichItIsMadePattern = Pattern.compile("whichItIsMade=(.*?)[\\,|\\]]");

	@Override
	public Comparable<Barrel> parse(String input, Collection<String> errorMessages) {
		if (errorMessages == null)
			errorMessages = new ArrayList<String>();

		Matcher typeNameMatcher = typeNamePattern.matcher(input);
		if (!typeNameMatcher.find()) {
			errorMessages.add("Incorrect object type");
			return null;
		}

		Matcher amountMatcher = amountPattern.matcher(input);
		Matcher storedMaterialMatcher = storedMaterialPattern.matcher(input);
		Matcher whichItIsMadeMatcher = whichItIsMadePattern.matcher(input);
		if (!amountMatcher.find() | !storedMaterialMatcher.find() | !whichItIsMadeMatcher.find()) {
			errorMessages.add("Incorrect format");
			return null;
		}

		String amountString = amountMatcher.group(1);
		String storedMaterialString = storedMaterialMatcher.group(1);
		String whichItIsMadeString = whichItIsMadeMatcher.group(1);

		Barrel.Builder builder = Barrel.getBuilder();
		setAmount(amountString, builder, errorMessages);
		setStoredMaterial(storedMaterialString, builder, errorMessages);
		setWhichItIsMade(whichItIsMadeString, builder, errorMessages);
		return errorMessages.size() > 0 ? null : builder.build();
	}

	@Override
	public String getParsableRepresentation(Comparable<?> obj) {
		Barrel barrel = (Barrel) obj;
		return String.format(PATTERN, barrel.getAmount(), barrel.getStoredMaterial(),
				barrel.getWhichItIsMade());
	}

	private void setAmount(String amountString, Barrel.Builder builder,
			Collection<String> errorMessages) {
		try {
			builder.setAmount(Double.parseDouble(amountString));
		} catch (NumberFormatException e) {
			errorMessages.add("Amount format is incorrect");
		} catch (IllegalArgumentException e) {
			errorMessages.add("Amount must be greater than zero");
		}
	}

	private void setStoredMaterial(String storedMaterialString, Barrel.Builder builder,
			Collection<String> errorMessages) {
		builder.setStoredMaterial(storedMaterialString.trim());
	}

	private void setWhichItIsMade(String whichItIsMadeString, Barrel.Builder builder,
			Collection<String> errorMessages) {
		try {
			builder.setWhichItIsMade(
					MaterialEnum.valueOf(whichItIsMadeString.trim().toUpperCase()));
		} catch (IllegalArgumentException e) {
			errorMessages.add("Which-it-is-made format is incorrect");
		}
	}

}
