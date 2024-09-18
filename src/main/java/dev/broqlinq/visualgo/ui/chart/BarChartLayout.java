package dev.broqlinq.visualgo.ui.chart;

import dev.broqlinq.visualgo.ui.util.BaseLayout;

import java.awt.*;

public class BarChartLayout extends BaseLayout {

    private static final int MIN_BAR_WIDTH = 1;
    private static final int MIN_BAR_HEIGHT = 100;

    private final int gap;

    public BarChartLayout() {
        this(0);
    }

    public BarChartLayout(int gap) {
        this.gap = gap;
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        var chart = (BarChart<?>) parent;
        synchronized (chart.getTreeLock()) {
            var insets = chart.getInsets();
            int barCount = chart.getComponentCount();
            double minWidth = insets.left + insets.right;
            double minHeight = insets.top + insets.bottom + MIN_BAR_HEIGHT;
            minWidth += barCount * MIN_BAR_WIDTH + (barCount - 1) * gap;
            return new Dimension(
                    (int) Math.round(minWidth),
                    (int) Math.round(minHeight)
            );
        }
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return minimumLayoutSize(parent);
    }

    @Override
    public void layoutContainer(Container parent) {
        var chart = (BarChart<?>) parent;
        var model = chart.getModel();
        synchronized (chart.getTreeLock()) {
            var insets = chart.getInsets();
            int chartWidth = chart.getWidth() - insets.left - insets.right;
            int chartHeight = Math.max(MIN_BAR_HEIGHT, chart.getHeight() - insets.top - insets.bottom);

            final int nBars = model.size();
            final double availableWidth = chartWidth - (nBars - 1) * gap;
            double barWidth = Math.max(1.0, availableWidth / model.size());
            float fHeight = chartHeight / model.max().floatValue();

            double x = insets.left;
            for (int i = 0; i < chart.getComponentCount(); i++) {
                var c = chart.getComponent(i);
                int barValue = model.get(i).intValue();
                int barHeight = Math.round(barValue * fHeight);
                c.setBounds((int) Math.floor(x),
                        chartHeight - barHeight + insets.top,
                        (int) Math.ceil(barWidth),
                        barHeight
                );
                x += barWidth + gap;
            }
        }
    }
}
