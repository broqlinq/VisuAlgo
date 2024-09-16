package dev.broqlinq.visualgo.ui.util;

import javax.swing.event.EventListenerList;
import java.util.Comparator;

public abstract class AbstractObservableArray<T> implements ObservableArray<T> {

    protected final Comparator<? super T> comparator;
    protected EventListenerList listenerList = new EventListenerList();
    protected T min;
    protected T max;

    protected AbstractObservableArray(Comparator<? super T> comparator) {
        this.comparator = comparator;
    }

    protected void findMinMax() {
        min = null;
        max = null;
        for (int i = 0; i < size(); i++) {
            T value = get(i);
            updateMin(value);
            updateMax(value);
        }
    }

    protected void updateMin(T value) {
        if (min == null || comparator.compare(min, value) > 0) {
            min = value;
        }
    }

    protected void updateMax(T value) {
        if (max == null || comparator.compare(max, value) < 0) {
            max = value;
        }
    }

    @Override
    public T max() {
        if (max == null) {
            findMinMax();
        }
        return max;
    }

    @Override
    public T min() {
        if (min == null) {
            findMinMax();
        }
        return min;
    }

    @Override
    public void swap(int i, int j) {
        if (i != j) {
            T x = set(i, get(j));
            set(j, x);
            var e = new ArrayEvent.ValuesSwapped<T>(this, i, j);
            fireArrayEvent(e);
        }
    }

    @SuppressWarnings("unchecked")
    protected void fireArrayEvent(ArrayEvent<T> e) {
        Object[] listeners = listenerList.getListenerList();

        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ArrayListener.class) {
                ArrayListener<T> listener = (ArrayListener<T>) listeners[i + 1];
                listener.onArrayChanged(e);
            }
        }
    }

    @Override
    public void addArrayListener(ArrayListener<T> listener) {
        listenerList.add(ArrayListener.class, listener);
    }

    @Override
    public void removeArrayListener(ArrayListener<T> listener) {
        listenerList.remove(ArrayListener.class, listener);
    }
}
