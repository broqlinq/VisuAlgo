package dev.broqlinq.visualgo.ui.chart;

import javax.swing.*;
import javax.swing.event.SwingPropertyChangeSupport;
import java.awt.*;
import java.beans.PropertyChangeListener;

public class DefaultBarChartColorModel implements BarChartColorModel {

    private static final Color DEFAULT_BASE_COLOR = Color.DARK_GRAY;
    private static final Color DEFAULT_SELECTION_COLOR = Color.GREEN;

    private final SwingPropertyChangeSupport propertySupport;

    private Color baseColor;
    private Color selectionColor;
    private final Color[] colors;

    public DefaultBarChartColorModel(int size) {
        propertySupport = new SwingPropertyChangeSupport(this);
        colors = new Color[size];

        baseColor = loadColor("baseColor", DEFAULT_BASE_COLOR);
        selectionColor = loadColor("selectionColor", DEFAULT_SELECTION_COLOR);
    }

    private static Color loadColor(String key, Color defaultColor) {
        var c = UIManager.getDefaults().getColor("BarChartColorModel." + key);
        return c != null ? c : defaultColor;
    }

    @Override
    public Color getBaseColor() {
        return baseColor;
    }

    @Override
    public void setBaseColor(Color baseColor) {
        var old = this.baseColor;
        if (old == null || old != baseColor) {
            this.baseColor = baseColor;
            propertySupport.firePropertyChange("baseColor", old, baseColor);
        }
    }

    @Override
    public Color getSelectionColor() {
        return selectionColor;
    }

    @Override
    public void setSelectionColor(Color selectionColor) {
        var old = this.selectionColor;
        if (old == null || old != selectionColor) {
            this.selectionColor = selectionColor;
            propertySupport.firePropertyChange("selectionColor", old, selectionColor);
        }
    }

    @Override
    public Color getColor(int index) {
        return colors[index];
    }

    @Override
    public void setColor(int index, Color color) {
        var old = colors[index];
        if (old == null || old != color) {
            colors[index] = color;
            propertySupport.fireIndexedPropertyChange("colors", index, old, color);
        }
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }

    @Override
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(propertyName, listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(propertyName, listener);
    }
}
