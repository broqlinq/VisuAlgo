package dev.broqlinq.visualgo.ui.chart;

import dev.broqlinq.visualgo.ui.util.*;
import dev.broqlinq.visualgo.ui.util.DefaultSingleSelectionModel;
import dev.broqlinq.visualgo.ui.util.SingleSelectionModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.IndexedPropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static java.util.Objects.requireNonNull;

public class BarChart<N extends Number> extends JPanel {

    private static final String PROPERTY_NAME_MODEL = "model";
    private static final String PROPERTY_NAME_BAR_CHART_COLOR_MODEL = "barChartColorModel";

    private ObservableArray<N> model;
    private BarChartColorModel barChartColorModel;
    private SingleSelectionModel selectionModel;

    private ArrayListener<N> arrayListener;
    private PropertyChangeListener barChartColorModelListener;
    private PropertyChangeListener selectionModelListener;

    public BarChart(ObservableArray<N> model) {
        this(model, new DefaultBarChartColorModel(model.size()));
    }

    public BarChart(ObservableArray<N> model, BarChartColorModel barChartColorModel) {
        this.model = requireNonNull(model, "model cannot be 'null'");
        this.barChartColorModel = requireNonNull(barChartColorModel, "barChartColorModel cannot be null");
        this.selectionModel = new DefaultSingleSelectionModel();
        setLayout(new BarChartLayout());

        initListeners();
        updateBars();
    }

    @Override
    public Dimension getMinimumSize() {
        return getLayout().minimumLayoutSize(this);
    }

    private void initListeners() {
        arrayListener = _ -> revalidate();
        model.addArrayListener(arrayListener);

        barChartColorModelListener = e -> {
            System.out.println(e);
            switch (e.getPropertyName()) {
                case "baseColor" -> updateBars();
                case "selectionColor" -> {
                    if (selectionModel.isSelected()) {
                        updateBar(selectionModel.getSelectedIndex());
                    }
                }
                case "colors" -> {
                    var ev = (IndexedPropertyChangeEvent) e;
                    int index = ev.getIndex();
                    updateBar(index);
                }
            }
        };
        barChartColorModel.addPropertyChangeListener(barChartColorModelListener);

        selectionModelListener = e -> {
            int i = (int) e.getOldValue();
            int j = (int) e.getNewValue();
            if (i != -1) {
                updateBar(i);
            }
            if (j != -1) {
                updateBar(j);
            }
        };
        selectionModel.addPropertyChangeListener(selectionModelListener);

        PropertyChangeListener barChartPropertyHandler = e -> {
            switch (e.getPropertyName()) {
                case PROPERTY_NAME_MODEL,
                     PROPERTY_NAME_BAR_CHART_COLOR_MODEL -> updateBars();
            }
        };
        addPropertyChangeListener(barChartPropertyHandler);
    }

    public int getSelectedIndex() {
        return selectionModel.getSelectedIndex();
    }

    public void setSelectedIndex(int index) {
        selectionModel.setSelectedIndex(index);
    }

    private void updateBars() {
        final int size = getComponentCount();
        int diff = model.size() - size;
        if (diff >= 0) {
            for (int i = 0; i < diff; i++) {
                var bar = newBar();
                add(bar);
            }
        } else {
            diff = -diff;
            for (int i = 0; i < diff; i++) {
                remove(getComponentCount() - 1);
            }
        }

        if (size != model.size()) {
            selectionModel.clearSelection();
            setSelectionModel(new DefaultSingleSelectionModel());
        }

        for (int i = 0; i < model.size(); i++) {
            updateBar(i);
        }

        revalidate();
    }

    private JPanel newBar() {
        var bar = new JPanel();
        bar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = bar.getAccessibleContext().getAccessibleIndexInParent();
                if (selectionModel.getSelectedIndex() != index) {
                    selectionModel.setSelectedIndex(index);
                } else {
                    selectionModel.clearSelection();
                }
            }
        });
        return bar;
    }

    private void setSelectionModel(SingleSelectionModel selectionModel) {
        var oldModel = this.selectionModel;
        oldModel.removePropertyChangeListener(selectionModelListener);
        this.selectionModel = requireNonNull(selectionModel, "selection model cannot be 'null'");
        selectionModel.addPropertyChangeListener(selectionModelListener);
    }

    private void updateBar(int index) {
        var bar = getComponent(index);
        var selectedIndex = selectionModel.getSelectedIndex();
        Color color = selectedIndex != -1
                ? barChartColorModel.getColor(selectedIndex)
                : null;
        if (color == null && selectedIndex == index) {
            color = barChartColorModel.getSelectionColor();
        } else {
            color = barChartColorModel.getBaseColor();
        }
        bar.setBackground(color);
    }

    @Override
    protected void paintComponent(Graphics g) {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paintComponent(g);
    }

    public void setBarChartColorModel(BarChartColorModel colorModel) {
        var oldModel = this.barChartColorModel;
        if (oldModel != colorModel) {
            oldModel.removePropertyChangeListener(barChartColorModelListener);
            this.barChartColorModel = requireNonNull(colorModel, "colorModel cannot be 'null'");
            colorModel.addPropertyChangeListener(barChartColorModelListener);
            firePropertyChange(PROPERTY_NAME_BAR_CHART_COLOR_MODEL, oldModel, colorModel);
        }
    }

    public ObservableArray<N> getModel() {
        return model;
    }

    public void setModel(ObservableArray<N> model) {
        var oldModel = this.model;
        if (oldModel != model) {
            oldModel.removeArrayListener(arrayListener);
            this.model = requireNonNull(model, "model cannot be 'null'");
            model.addArrayListener(arrayListener);
            firePropertyChange(PROPERTY_NAME_MODEL, oldModel, model);
        }
    }

    public N getValue(int index) {
        return model.get(index);
    }

    public void clearSelection() {
        selectionModel.clearSelection();
    }

}
