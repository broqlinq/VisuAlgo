package dev.broqlinq.visualgo.ui.util;

import java.util.EventObject;

public abstract sealed class ArrayEvent<T> extends EventObject {

    private ArrayEvent(Object source) {
        super(source);
    }

    public static final class ValueChanged<T> extends ArrayEvent<T> {
        private final int index;
        private final T oldValue;
        private final T newValue;

        public ValueChanged(Object source, int index, T oldValue, T newValue) {
            super(source);
            this.index = index;
            this.oldValue = oldValue;
            this.newValue = newValue;
        }

        public int getIndex() {
            return index;
        }

        public T getOldValue() {
            return oldValue;
        }

        public T getNewValue() {
            return newValue;
        }

        @Override
        public String toString() {
            return "ArrayEvent.ValueChanged [source=" + source
                    + ", index=" + index
                    + ", oldValue=" + oldValue
                    + ", newValue=" + newValue
                    + ']';
        }
    }

    public static final class ValuesSwapped<T> extends ArrayEvent<T> {
        private final int firstIndex;
        private final int secondIndex;

        public ValuesSwapped(Object source, int firstIndex, int secondIndex) {
            super(source);
            this.firstIndex = firstIndex;
            this.secondIndex = secondIndex;
        }

        public int getFirstIndex() {
            return firstIndex;
        }

        public int getSecondIndex() {
            return secondIndex;
        }

        @Override
        public String toString() {
            return "ArrayChanged.ValuesSwapped [source=" + source
                    + ", firstIndex=" + firstIndex
                    + ", secondIndex=" + secondIndex
                    + ']';
        }
    }

    public static final class RangeChanged<T> extends ArrayEvent<T> {

        private final int fromIndex;
        private final int toIndex;

        public RangeChanged(Object source, int fromIndex, int toIndex) {
            super(source);
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
        }

        public int getFromIndex() {
            return fromIndex;
        }

        public int getToIndex() {
            return toIndex;
        }

        @Override
        public String toString() {
            return "ArrayChanged.RangeChanged [source=" + source
                    + ", fromIndex=" + fromIndex
                    + ", toIndex=" + toIndex
                    + ']';
        }
    }
}
