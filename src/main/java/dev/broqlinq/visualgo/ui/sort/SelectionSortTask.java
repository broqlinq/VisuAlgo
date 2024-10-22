package dev.broqlinq.visualgo.ui.sort;

import dev.broqlinq.visualgo.ui.chart.BarChart;

import java.util.concurrent.TimeUnit;

public class SelectionSortTask<N extends Number & Comparable<N>> extends SortingTask<N> {

    public SelectionSortTask(BarChart<N> chart, long pauseTime, TimeUnit unit) {
        super(chart, pauseTime, unit);
    }

    @Override
    protected Void doInBackground() throws Exception {
        final int len = getArraySize();

        for (int i = 0; i < len - 1; i++) {
            int jMin = i;
            var min = getArrayValue(jMin);
            for (int j = i + 1; j < len; j++) {
                var x = getArrayValue(j);
                if (x.compareTo(min) < 0) {
                    pause();
                    jMin = j;
                    min = x;
                }
            }

            if (jMin != i) {
                swapArrayValues(jMin, i);
                pause();
            }
        }

        return null;
    }
}
