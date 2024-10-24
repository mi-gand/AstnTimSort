package com.aston.AstnTimSort.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aston.AstnTimSort.parsers.ParserFactory;
import com.aston.AstnTimSort.parsers.StringParserToComparable;

@Repository
public class DataRepository {
	private List<Comparable<?>> data;
	private boolean sorted;
	private StringParserToComparable<?> parser;

	private final ParserFactory parserFactory;

	@Autowired
	public DataRepository(ParserFactory parserFactory) {
		this.parserFactory = parserFactory;
	}
	
	public String getPattern() {
		if (parser == null) return null;
		return parser.getPattern();
	}

	public void sort() {
		if (sorted)
			return;
		data.sort(null);
		sorted = true;
	}

	public boolean find(String input) {
		if (!sorted)
			throw new RuntimeException("Data is not sorted");
		// TODO
		return false;
	}

	public void clearData() {
		data = null;
	}

	public List<?> getData() {
		return data;
	}
	
	public void setType(String type) {
		parser = parserFactory.getParser(type);
		data = new ArrayList<>();
	}

	@SuppressWarnings("unchecked")
	public void add(List<?> data) {
		this.data = (List<Comparable<?>>) data;
		sorted = false;
	}

	public void add(String input) {
		if (parser == null)
			return;
		data.add(parser.parse(input));
	}

	public String getInputExample() {
		if (parser == null) return null;
		return parser.getInputExample();
	}

}
