package dev.broqlinq.visualgo.ui.util;

import java.util.Collection;
import java.util.Comparator;

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

    public static <T extends Comparable<T>> DefaultObservableArray<T> ofComparable(T[] values) {
        return new DefaultObservableArray<>(values, Comparator.naturalOrder());
    }

    public static <T extends Comparable<T>> DefaultObservableArray<T> ofComparable(Collection<T> values) {
        return new DefaultObservableArray<>(values, Comparator.naturalOrder());
    }

}
