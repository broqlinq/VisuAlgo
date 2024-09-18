package dev.broqlinq.visualgo.ui.sort;

import dev.broqlinq.visualgo.ui.chart.BarChart;

import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

public enum Algorithm {

    BUBBLE_SORT("Bubble Sort") {
        @Override
        public <N extends Number & Comparable<N>> SortingTask<N> newTask(BarChart<N> barChart, int pause, TimeUnit unit) {
            return new BubbleSortTask<>(barChart, pause, unit);
        }
    },

    MERGE_SORT("Merge Sort") {
        @Override
        public <N extends Number & Comparable<N>> SortingTask<N> newTask(BarChart<N> barChart, int pause, TimeUnit unit) {
            return new MergeSortTask<>(barChart, pause, unit);
        }
    },

    INSERTION_SORT("Insertion Sort") {
        @Override
        public <N extends Number & Comparable<N>> SortingTask<N> newTask(BarChart<N> barChart, int pause, TimeUnit unit) {
            return new InsertionSortTask<>(barChart, pause, unit);
        }
    },

    SELECTION_SORT("Selection Sort") {
        @Override
        public <N extends Number & Comparable<N>> SortingTask<N> newTask(BarChart<N> barChart, int pause, TimeUnit unit) {
            return new SelectionSortTask<>(barChart, pause, unit);
        }
    },

    QUICK_SORT("Quick Sort") {
        @Override
        public <N extends Number & Comparable<N>> SortingTask<N> newTask(BarChart<N> barChart, int pause, TimeUnit unit) {
            return new QuickSortTask<>(barChart, pause, unit);
        }
    };

    private final String name;

    Algorithm(String name) {
        this.name = name;
    }

    public abstract <N extends Number & Comparable<N>> SortingTask<N> newTask(BarChart<N> barChart, int pause, TimeUnit unit);

    public String getAlgorithmName() {
        return name;
    }

    @Override
    public String toString() {
        return getAlgorithmName();
    }

    public static Algorithm[] values(Comparator<Algorithm> comparator) {
        var values = values();
        Arrays.sort(values, comparator);
        return values;
    }

}
