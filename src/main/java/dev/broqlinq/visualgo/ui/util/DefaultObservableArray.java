package dev.broqlinq.visualgo.ui.util;

import java.util.*;
import java.util.function.IntFunction;

public class DefaultObservableArray<T> extends AbstractObservableArray<T> {

    private final T[] values;

    public DefaultObservableArray(T[] values, Comparator<? super T> comparator) {
        super(comparator);
        this.values = values;
    }

    @SuppressWarnings("unchecked")
    public DefaultObservableArray(Collection<T> values, Comparator<? super T> comparator) {
        super(comparator);
        this.values = (T[]) values.toArray();
    }

    @Override
    public void update(int fromIndex, int toIndex, IntFunction<T> mapper) {
        if (fromIndex > toIndex)
            throw new IllegalArgumentException("'from' index cannot be greater than 'to' index: " + fromIndex + " > " + toIndex);

        boolean anyValueChanged = false;
        for (int i = fromIndex; i < toIndex; i++) {
            T newValue = mapper.apply(i);
            if (newValue == null || newValue != values[i]) {
                values[i] = mapper.apply(i);
                anyValueChanged = true;
            }
        }

        if (anyValueChanged) {
            var e = new ArrayEvent.RangeChanged<T>(this, fromIndex, toIndex);
            fireArrayEvent(e);
        }
    }

    @Override
    public T set(int index, T value) {
        var old = values[index];
        if (old != value) {
            values[index] = value;
            findMinMax();
            var e = new ArrayEvent.ValueChanged<>(this, index, old, value);
            fireArrayEvent(e);
        }
        return old;
    }

    @Override
    public T get(int index) {
        return values[index];
    }

    @Override
    public int size() {
        return values.length;
    }

    @Override
    public void shuffle() {
        List<T> temp = Arrays.asList(values);
        Collections.shuffle(temp);
        var e = new ArrayEvent.RangeChanged<T>(this, 0, values.length);
        fireArrayEvent(e);
    }

    public static <T extends Comparable<T>> DefaultObservableArray<T> ofComparable(T[] values) {
        return new DefaultObservableArray<>(values, Comparator.naturalOrder());
    }

    public static <T extends Comparable<T>> DefaultObservableArray<T> ofComparable(Collection<T> values) {
        return new DefaultObservableArray<>(values, Comparator.naturalOrder());
    }

}
