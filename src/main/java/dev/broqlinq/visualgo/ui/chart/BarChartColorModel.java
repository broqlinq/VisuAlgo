package dev.broqlinq.visualgo.ui.chart;

import java.awt.*;
import java.beans.PropertyChangeListener;

public interface BarChartColorModel {

    Color getBaseColor();

    void setBaseColor(Color baseColor);

    Color getSelectionColor();

    void setSelectionColor(Color selectedColor);

    Color getColor(int index);

    void setColor(int index, Color color);

    void addPropertyChangeListener(PropertyChangeListener listener);

    void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

    void removePropertyChangeListener(PropertyChangeListener listener);

    void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);
}
