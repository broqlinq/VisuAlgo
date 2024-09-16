package dev.broqlinq.visualgo.ui.chart;

import dev.broqlinq.visualgo.ui.util.BaseLayout;

import java.awt.*;

public class BarChartLayout extends BaseLayout {

    private static final int MIN_BAR_WIDTH = 5;

    private final int gap;

    public BarChartLayout() {
        this(1);
    }

    public BarChartLayout(int gap) {
        this.gap = gap;
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        var chart = (BarChart<?>) parent;
        synchronized (chart.getTreeLock()) {
            var insets = chart.getInsets();
            double minWidth = insets.left + insets.right;
            double minHeight = insets.top + insets.bottom + 100f;
            for (int i = 0; i < chart.getComponentCount(); i++) {
                if (i > 0) {
                    minWidth += gap;
                }
                minWidth += MIN_BAR_WIDTH;
            }
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
            int chartHeight = chart.getHeight() - insets.top - insets.bottom;

            final int nBars = model.size();
            final double availableWidth = chartWidth - (nBars - 1) * gap;
            double barWidth = availableWidth / model.size();
            double fHeight = ((double) chartHeight) / model.max().floatValue();

            double x = insets.left;
            for (int i = 0; i < chart.getComponentCount(); i++) {
                var c = chart.getComponent(i);
                double barValue = model.get(i).floatValue();
                double barHeight = barValue * fHeight;
                c.setBounds(
                        (int) Math.floor(x),
                        (int) Math.ceil(chartHeight - barHeight + insets.top),
                        (int) Math.floor(barWidth),
                        (int) Math.floor(barHeight)
                );
                x += barWidth + gap;
            }
        }
    }
}
