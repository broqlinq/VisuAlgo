package dev.broqlinq.visualgo.ui.sort.dialog;

import javax.swing.*;

public enum YesNoChoice {

    YES(JOptionPane.YES_OPTION),
    NO(JOptionPane.NO_OPTION);

    final int swingValue;

    YesNoChoice(int swingValue) {
        this.swingValue = swingValue;
    }

    public boolean yes() {
        return this == YES;
    }

    public boolean no() {
        return this == NO;
    }

    static YesNoChoice fromOrdinal(int ordinal) {
        return values()[ordinal];
    }
}
