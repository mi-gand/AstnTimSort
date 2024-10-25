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
<<<<<<< HEAD
		map.put("BARREL", new AnimalParser());

=======
		map.put("BARREL", new BarrelParser());
>>>>>>> 115b28070784fbf1d1de414ad69baeda596420aa
	}

	public StringParserToComparable<?> getParser(String className) {
		className = className.trim().toUpperCase();
		if (!map.keySet().contains(className)) {
			throw new IllegalArgumentException("This data type is not supported");
		}
		return map.get(className);
	}

}
