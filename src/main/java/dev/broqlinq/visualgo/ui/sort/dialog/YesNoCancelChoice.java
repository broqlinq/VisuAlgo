package dev.broqlinq.visualgo.ui.sort.dialog;

import javax.swing.*;

public enum YesNoCancelChoice {

    YES(JOptionPane.YES_OPTION),
    NO(JOptionPane.NO_OPTION),
    CANCEL(JOptionPane.CANCEL_OPTION);

    final int swingValue;

    YesNoCancelChoice(int swingValue) {
        this.swingValue = swingValue;
    }

    public boolean yes() {
        return this == YES;
    }

    public boolean no() {
        return this == NO;
    }

    public boolean cancel() {
        return this == CANCEL;
    }

    static YesNoCancelChoice fromOrdinal(int ordinal) {
        return values()[ordinal];
    }
}
