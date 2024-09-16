package dev.broqlinq.visualgo.ui.sort;

import dev.broqlinq.visualgo.ui.chart.BarChart;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MergeSortTask<N extends Number & Comparable<N>> extends SortingTask<N> {

    public MergeSortTask(BarChart<N> chart, long pauseTime, TimeUnit unit) {
        super(chart, pauseTime, unit);
    }

    @Override
    protected Void doInBackground() throws InterruptedException {
        final int len = chart.getModel().size();
        if (len != 0) {
            splitAndMerge(0, chart.getModel().size() - 1);
        }

        for (int i = 0; i < len; i++) {
            setSelectedIndex(i);
            pause(12);
        }

        return null;
    }

    private List<N> splitAndMerge(int lower, int upper) throws InterruptedException {
        if (lower > upper) {
            return List.of();
        }
        if (lower == upper) {
            return List.of(chart.getValue(lower));
        }

        final int mid = (lower + upper) >>> 1;
        var left = splitAndMerge(lower, mid);
        var right = splitAndMerge(mid + 1, upper);
        List<N> sorted = new ArrayList<>(left.size() + right.size());
        var leftIndex = 0;
        var rightIndex = 0;
        while (leftIndex < left.size() && rightIndex < right.size()) {
            var x = left.get(leftIndex);
            var y = right.get(rightIndex);
            if (x.compareTo(y) <= 0) {
                sorted.add(x);
                leftIndex++;
            } else {
                sorted.add(y);
                rightIndex++;
            }
        }

        while (leftIndex < left.size()) {
            sorted.add(left.get(leftIndex));
            leftIndex++;
        }

        while (rightIndex < right.size()) {
            sorted.add(right.get(rightIndex));
            rightIndex++;
        }

        for (int i = 0; i < sorted.size(); i++) {
            var x = sorted.get(i);
            setArrayValue(lower + i, x);
            pause();
        }
        clearSelection();

        return sorted;
    }
}
