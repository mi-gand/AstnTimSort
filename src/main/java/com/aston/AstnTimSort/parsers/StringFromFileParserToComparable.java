package com.aston.AstnTimSort.parsers;

import java.util.Collection;

public interface StringFromFileParserToComparable<T> {
	Comparable<T> parse(String input, Collection<String> errorMessages);

	String getParsableRepresentation(Comparable<?> obj);
}
