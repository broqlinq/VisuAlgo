package dev.broqlinq.visualgo.ui.util;

public interface ObservableArray<T> {

    T set(int index, T value);

    T get(int index);

    T max();

    T min();

    int size();

    void swap(int i, int j);

    void addArrayListener(ArrayListener<T> listener);

    void removeArrayListener(ArrayListener<T> listener);
}
