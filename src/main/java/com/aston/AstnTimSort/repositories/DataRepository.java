package com.aston.AstnTimSort.repositories;

import static java.util.stream.Collectors.joining;

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
import com.aston.AstnTimSort.parsers.ParserFromFileFactory;
import com.aston.AstnTimSort.parsers.StringFromFileParserToComparable;
import com.aston.AstnTimSort.parsers.StringParserToComparable;
import com.aston.AstnTimSort.utils.DataUtils;

@Repository
public class DataRepository {
	private List<Comparable<?>> data;
	private boolean sorted;
	private StringParserToComparable<?> parser;
	private StringFromFileParserToComparable<?> parserFromFile;

	private final ParserFactory parserFactory;
	private final ParserFromFileFactory parserFromFileFactory;
	private Comparable<?> searchResult;

	@Autowired
	public DataRepository(ParserFactory parserFactory,
			ParserFromFileFactory parserFromFileFactory) {
		this.parserFactory = parserFactory;
		this.parserFromFileFactory = parserFromFileFactory;
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
		searchResult = null;
	}

	public String getDataRepresentation() {
		return data.stream().map(Object::toString).collect(Collectors.joining("\n"));
	}

	public void setType(String type) {
		parser = parserFactory.getParser(type);
		parserFromFile = parserFromFileFactory.getParser(type);
		data = new ArrayList<>();
	}

	public void add(String input) {
		if (parser == null)
			return;
		data.add(parser.parse(input));
		sorted = false;
	}
	
	public void addFromFile(String input) {
		if (parserFromFile == null)
			return;
		List<String> errorMessages = new ArrayList<>();
		Comparable<?> parseResult = parserFromFile.parse(input, errorMessages);
		if (errorMessages.size() > 0) {
			String message = errorMessages.stream().collect(joining("; "));
			throw new IllegalArgumentException(message);
		}
		data.add(parseResult);
		sorted = false;
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
				input.write(parserFromFile.getParsableRepresentation(obj) + "\n");
			}
		}
	}

	public void exportToFileSearchResult(Path path, boolean append) throws IOException {
		if (searchResult == null)
			return;
		OpenOption openOption = append ? StandardOpenOption.APPEND : StandardOpenOption.CREATE;
		try (BufferedWriter input = Files.newBufferedWriter(path, openOption)) {
			for (Comparable<?> obj : data) {
				input.write(parserFromFile.getParsableRepresentation(obj) + "\n");
			}
		}
	}
	
	public String getSupportedDataTypesAsOneLine() {
		return parserFactory.getNamesOfSupportedTypesAsOneLine();
	}
}
