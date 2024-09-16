package dev.broqlinq.visualgo.ui.util;

import java.beans.PropertyChangeListener;

public interface SingleSelectionModel {

    default boolean isSelected() {
        return getSelectedIndex() >= 0;
    }

    default void clearSelection() {
        setSelectedIndex(-1);
    }

    int getSelectedIndex();

    void setSelectedIndex(int index);

    void addPropertyChangeListener(PropertyChangeListener listener);

    void removePropertyChangeListener(PropertyChangeListener listener);
}
