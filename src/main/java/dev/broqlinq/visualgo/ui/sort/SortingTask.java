package dev.broqlinq.visualgo.ui.sort;

import dev.broqlinq.visualgo.ui.chart.BarChart;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;
import java.util.function.IntFunction;

public abstract class SortingTask<N extends Number & Comparable<N>> extends SwingWorker<Void, Void> {

    protected final BarChart<N> chart;

    protected final long pauseTimeMillis;

    public SortingTask(BarChart<N> chart, long pauseTime, TimeUnit unit) {
        this.chart = chart;
        this.pauseTimeMillis = unit.toMillis(pauseTime);
    }

    protected final void setSelectedIndex(int index) {
//        EventQueue.invokeLater(() -> chart.setSelectedIndex(index));
        try {
            EventQueue.invokeAndWait(() -> chart.setSelectedIndex(index));
        } catch (InterruptedException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
//        chart.setSelectedIndex(index);
    }

    protected final void updateArrayRange(int fromIndex, int toIndex, IntFunction<N> mapper) {
        chart.getModel().update(fromIndex, toIndex, mapper);
    }

    protected final void setArrayValue(int index, N value) {
//        EventQueue.invokeLater(() -> chart.getModel().set(index, value));
        chart.getModel().set(index, value);
    }

    protected final void swapArrayValues(int i, int j) {
//        EventQueue.invokeLater(() -> chart.getModel().swap(i, j));
        chart.getModel().swap(i, j);
    }

    protected final void clearSelection() {
//        EventQueue.invokeLater(chart::clearSelection);
        try {
            EventQueue.invokeAndWait(chart::clearSelection);
        } catch (InterruptedException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
//        chart.clearSelection();
    }

    protected final void pause() throws InterruptedException {
        pause(pauseTimeMillis, TimeUnit.MILLISECONDS);
    }

    protected static void pause(long timeMillis) throws InterruptedException {
        pause(timeMillis, TimeUnit.MILLISECONDS);
    }

    protected static void pause(long time, TimeUnit unit) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(unit.toMillis(time));
    }
}
