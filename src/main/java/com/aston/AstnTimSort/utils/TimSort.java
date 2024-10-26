package com.aston.AstnTimSort.utils;

import java.util.Arrays;
import java.util.List;

public class TimSort {
    private static final int RUN_SIZE = 32;

    public static void sort(List<Comparable<?>> data) {
        int n = data.size();
        Comparable[] arr = data.toArray(new Comparable[0]);

        for (int i = 0; i < n; i += RUN_SIZE) {
            insertionSort(arr, i, Math.min((i + RUN_SIZE - 1), (n - 1)));
        }

        for (int size = RUN_SIZE; size < n; size = 2 * size) {
            for (int left = 0; left < n; left += 2 * size) {
                int mid = left + size - 1;
                int right = Math.min((left + 2 * size - 1), (n - 1));

                if (mid < right) {
                    merge(arr, left, mid, right);
                }
            }
        }

        for (int i = 0; i < n; i++) {
            data.set(i, arr[i]);
        }
    }

    private static void insertionSort(Comparable[] arr, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            Comparable temp = arr[i];
            int j = i - 1;
            while (j >= left && arr[j].compareTo(temp) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = temp;
        }
    }

    private static void merge(Comparable[] arr, int left, int mid, int right) {
        int len1 = mid - left + 1, len2 = right - mid;
        Comparable[] leftArr = Arrays.copyOfRange(arr, left, mid + 1);
        Comparable[] rightArr = Arrays.copyOfRange(arr, mid + 1, right + 1);

        int i = 0, j = 0, k = left;
        while (i < len1 && j < len2) {
            if (leftArr[i].compareTo(rightArr[j]) <= 0) {
                arr[k++] = leftArr[i++];
            } else {
                arr[k++] = rightArr[j++];
            }
        }

        while (i < len1) {
            arr[k++] = leftArr[i++];
        }

        while (j < len2) {
            arr[k++] = rightArr[j++];
        }
    }
}