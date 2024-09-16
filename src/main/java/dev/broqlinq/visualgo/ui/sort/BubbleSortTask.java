package dev.broqlinq.visualgo.ui.sort;

import dev.broqlinq.visualgo.ui.chart.BarChart;

import java.util.concurrent.TimeUnit;

public class BubbleSortTask<N extends Number & Comparable<N>> extends SortingTask<N> {

    public BubbleSortTask(BarChart<N> chart, long pauseTime, TimeUnit unit) {
        super(chart, pauseTime, unit);
    }

    @Override
    protected Void doInBackground() throws InterruptedException {
        final int len = chart.getModel().size();
        for (int i = 0; i < len - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < len - i - 1; j++) {
//                setSelectedIndex(j);
                pause();
                var x = chart.getValue(j);
                var y = chart.getValue(j + 1);
                if (x.compareTo(y) > 0) {
                    swapArrayValues(j, j + 1);
//                    setSelectedIndex(j + 1);
                    swapped = true;
                    pause();
                }
            }

            clearSelection();

            if (!swapped) {
                break;
            }
        }

        for (int i = 0; i < len; i++) {
            setSelectedIndex(i);
            pause(15);
        }
        clearSelection();

        return null;
    }
}
