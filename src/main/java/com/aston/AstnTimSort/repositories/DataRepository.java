package com.aston.AstnTimSort.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class DataRepository { 
	private List<Comparable<?>> data;
	private boolean sorted;
	
	public void sort() {
		data.sort(null);
		sorted = true;
	}
	
	public boolean isSorted() {
		return sorted;
	}
	
	public void find() {
		// TODO
	}
	
	public void clearData() {
		data.clear();
	}
	
	@SuppressWarnings("unchecked")
	public void add(List<?> data) {
		this.data = (List<Comparable<?>>) data;
		sorted = false;
	}
	
	public List<?> getData(){
		return data;
	}

}
