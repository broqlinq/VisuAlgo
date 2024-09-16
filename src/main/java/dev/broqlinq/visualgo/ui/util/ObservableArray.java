package dev.broqlinq.visualgo.ui.util;

import java.util.function.IntFunction;

public interface ObservableArray<T> {

    void update(int fromIndex, int toIndex, IntFunction<T> mapper);

    T set(int index, T value);

    T get(int index);

    T max();

    T min();

    int size();

    void swap(int i, int j);

    void addArrayListener(ArrayListener<T> listener);

    void removeArrayListener(ArrayListener<T> listener);
}
