package com.aston.AstnTimSort.parsers;

import static java.util.stream.Collectors.joining;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ParserFactory {
	private final Map<String, StringParserToComparable<?>> map = new HashMap<>();
	{
		map.put("PERSON", new PersonParser());
		map.put("ANIMAL", new AnimalParser());
		map.put("BARREL", new BarrelParser());
	}

	private final String namesOfSupportedTypesAsOneLine;
	{
		namesOfSupportedTypesAsOneLine = map.keySet().stream().map(str -> str.toLowerCase())
				.collect(joining(", "));
	}

	public StringParserToComparable<?> getParser(String className) {
		className = className.trim().toUpperCase();
		if (!map.keySet().contains(className)) {
			throw new IllegalArgumentException("This data type is not supported");
		}
		return map.get(className);
	}

	public String getNamesOfSupportedTypesAsOneLine() {
		return namesOfSupportedTypesAsOneLine;
	}

	public String[] getNamesOfSupportedTypesAsArray() {
		return (String[]) map.keySet().stream().toArray();
	}

}
