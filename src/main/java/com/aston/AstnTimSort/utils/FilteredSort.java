package com.aston.AstnTimSort.utils;

import com.aston.AstnTimSort.models.HasIntField;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

//название подхода - сортировка с фильтрацией
public final class FilteredSort {
    private FilteredSort() {
        throw new UnsupportedOperationException();
    }

    public static void sort(List<Comparable<?>> data) {
        // находим четные элементы и индексы этих элементов
        List<Integer> evenIndexes = new ArrayList<>();
        List<HasIntField> evenElements = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            HasIntField hasIntField = (HasIntField) data.get(i);
            if (hasIntField.getIntField() % 2 == 0) {
                evenIndexes.add(i);
                evenElements.add(hasIntField);
            }
        }

        // сортируем четные элементы
        evenElements.sort(Comparator.comparingInt(HasIntField::getIntField));

        // вставляем элементы в их позиции
        for (int i = 0; i < evenIndexes.size(); i++) {
            data.set(evenIndexes.get(i), (Comparable<?>) evenElements.get(i));
        }
    }
}