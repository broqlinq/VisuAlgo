package dev.broqlinq.visualgo.ui.sort;

import dev.broqlinq.visualgo.ui.chart.BarChart;

import java.util.concurrent.TimeUnit;

public class InsertionSortTask<N extends Number & Comparable<N>> extends SortingTask<N> {

    public InsertionSortTask(BarChart<N> chart, long pauseTime, TimeUnit unit) {
        super(chart, pauseTime, unit);
    }

    @Override
    protected Void doInBackground() throws Exception {
        final int len = getArraySize();
        for (int i = 0; i < len; i++) {
            final var n = getArrayValue(i);
            int j = i;
            for (; j > 0 && getArrayValue(j - 1).compareTo(n) > 0; j--) {
                setArrayValue(j, getArrayValue(j - 1));
                pause();
            }
            setArrayValue(j, n);
            pause();
        }
        return null;
    }
}
