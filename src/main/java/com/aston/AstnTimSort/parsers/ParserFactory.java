package com.aston.AstnTimSort.parsers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ParserFactory {
	private final Map<String, StringParserToComparable<?>> map = new HashMap<>();
	{
		map.put("PERSON", new PersonParser());
	}

	public StringParserToComparable<?> getParser(String className) {
		className = className.trim().toUpperCase();
		if (!map.keySet().contains(className)) {
			return null;
		}
		return map.get(className);
	}

}
