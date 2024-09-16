package dev.broqlinq.visualgo.ui.util;

import javax.swing.event.SwingPropertyChangeSupport;
import java.beans.PropertyChangeListener;

public class DefaultSingleSelectionModel implements SingleSelectionModel {

    public static final String PROPERTY_SELECTED_INDEX = "selectedIndex";

    private final SwingPropertyChangeSupport propertySupport;

    private int selectedIndex = -1;

    public DefaultSingleSelectionModel() {
        this(false);
    }

    public DefaultSingleSelectionModel(boolean notifyOnEDT) {
        propertySupport = new SwingPropertyChangeSupport(this, notifyOnEDT);
    }

    @Override
    public int getSelectedIndex() {
        return selectedIndex;
    }

    @Override
    public void setSelectedIndex(int index) {
        int old = this.selectedIndex;
        if (old != index) {
            this.selectedIndex = index;
            propertySupport.firePropertyChange(PROPERTY_SELECTED_INDEX, old, index);
        }
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }
}
