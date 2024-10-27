package com.aston.AstnTimSort.utils;

import java.util.List;
import java.util.Optional;

public final class BinSearch {

	private BinSearch() {
		throw new UnsupportedOperationException();
	}

	public static Optional<Comparable<?>> search(List<Comparable<?>> data, Comparable<?> key) {
		int left = 0;
		int right = data.size() - 1;

		while (left <= right) {
			int mid = left + (right - left) / 2;
			Comparable<?> midValue = data.get(mid);
			int cmp = ((Comparable<Object>) midValue).compareTo(key);
			if (cmp == 0) {
				return Optional.of(midValue);
			} else if (cmp < 0) {
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}

		return Optional.empty();
	}
}
