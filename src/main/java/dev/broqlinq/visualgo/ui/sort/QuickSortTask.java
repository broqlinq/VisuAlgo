package dev.broqlinq.visualgo.ui.sort;

import dev.broqlinq.visualgo.ui.chart.BarChart;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class QuickSortTask<N extends Number & Comparable<N>> extends SortingTask<N> {

    private final Random r = new Random();

    public QuickSortTask(BarChart<N> chart, long pauseTime, TimeUnit unit) {
        super(chart, pauseTime, unit);
    }

    @Override
    protected Void doInBackground() throws Exception {
        quickSort(0, getArraySize() - 1);
        return null;
    }

    private void quickSort(int lower, int upper) throws InterruptedException {
        if (lower >= upper || lower < 0) {
            return;
        }

        int index = partition(lower, upper);

        quickSort(lower, index - 1);
        quickSort(index + 1, upper);
    }

    private int partition(int lower, int upper) throws InterruptedException {
        int pivotIndex = (lower + upper) >>> 1;
        var pivot = getArrayValue(pivotIndex);

        int pi = lower;
        for (int i = lower; i <= upper; i++) {
            var x = getArrayValue(i);
            if (x.compareTo(pivot) < 0) {
                if (pi == pivotIndex) {
                    pivotIndex = i;
                }
                swapArrayValues(i, pi);
                pi++;
                pause();
            }
        }

        swapArrayValues(pi, pivotIndex);
        pause();

        return pi;
    }
}
