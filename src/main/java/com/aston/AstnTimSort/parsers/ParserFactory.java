package com.aston.AstnTimSort.parsers;

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

	public StringParserToComparable<?> getParser(String className) {
		className = className.trim().toUpperCase();
		if (!map.keySet().contains(className)) {
			throw new IllegalArgumentException("This data type is not supported");
		}
		return map.get(className);
	}

}
