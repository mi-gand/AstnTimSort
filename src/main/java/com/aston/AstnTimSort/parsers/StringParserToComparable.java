package com.aston.AstnTimSort.parsers;

public interface StringParserToComparable<T> {
	Comparable<T> parse(String input);
	String getPattern();
}