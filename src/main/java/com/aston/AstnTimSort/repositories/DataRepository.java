package com.aston.AstnTimSort.repositories;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aston.AstnTimSort.parsers.ParserFactory;
import com.aston.AstnTimSort.parsers.StringParserToComparable;
import com.aston.AstnTimSort.utils.DataUtils;

@Repository
public class DataRepository {
	private List<Comparable<?>> data;
	private boolean sorted;
	private StringParserToComparable<?> parser;

	private final ParserFactory parserFactory;
	private Comparable<?> searchResult;

	@Autowired
	public DataRepository(ParserFactory parserFactory) {
		this.parserFactory = parserFactory;
	}

	public String getPattern() {
		if (parser == null)
			return null;
		return parser.getPattern();
	}

	public void sort() {
		if (sorted)
			return;
		data.sort(null);
		// DataUtils.timSort(data); //TODO
		sorted = true;
	}

	public boolean find(String input) {
		if (!sorted)
			throw new RuntimeException("Data is not sorted");
		Optional<Comparable<?>> result = DataUtils.binarySearch(parser.parse(input));
		if (result.isPresent()) {
			searchResult = result.get();
			return true;
		} else {
			return false;
		}
	}

	public void clearData() {
		data = null;
	}

	public String getDataRepresentation() {
		return data.stream().map(Object::toString).collect(Collectors.joining("\n"));
	}

	public void setType(String type) {
		parser = parserFactory.getParser(type);
		data = new ArrayList<>();
	}

	public void add(String input) {
		if (parser == null)
			return;
		data.add(parser.parse(input));
	}

	public String getInputExample() {
		if (parser == null)
			return null;
		return parser.getInputExample();
	}

	public void exportToFile(Path path, boolean append) throws IOException {
		if (data == null)
			return;
		OpenOption openOption = append ? StandardOpenOption.APPEND : StandardOpenOption.CREATE;
		try (BufferedWriter input = Files.newBufferedWriter(path, openOption)) {
			for (Comparable<?> obj : data) {
				input.write(parser.getParsableRepresentation(obj) + "\n");
			}
		}
	}
	
	public void exportToFileSearchResult(Path path, boolean append) throws IOException {
		if (data == null)
			return;
		OpenOption openOption = append ? StandardOpenOption.APPEND : StandardOpenOption.CREATE;
		try (BufferedWriter input = Files.newBufferedWriter(path, openOption)) {
			for (Comparable<?> obj : data) {
				input.write(parser.getParsableRepresentation(obj) + "\n");
			}
		}
	}

}
