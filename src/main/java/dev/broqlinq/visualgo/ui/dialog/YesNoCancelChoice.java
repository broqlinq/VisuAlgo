package dev.broqlinq.visualgo.ui.dialog;

import javax.swing.*;

public enum YesNoCancelChoice {

    YES(JOptionPane.YES_OPTION),
    NO(JOptionPane.NO_OPTION),
    CANCEL(JOptionPane.CANCEL_OPTION);

    final int swingValue;

    YesNoCancelChoice(int swingValue) {
        this.swingValue = swingValue;
    }

    public static YesNoCancelChoice fromSwingValue(int swingValue) {
        return switch (swingValue) {
            case JOptionPane.YES_OPTION -> YES;
            case JOptionPane.NO_OPTION -> NO;
            case JOptionPane.CANCEL_OPTION,
                 JOptionPane.CLOSED_OPTION-> CANCEL;
            default -> throw new IllegalArgumentException("Invalid swing value: " + swingValue);
        };
    }

    static YesNoCancelChoice fromOrdinal(int ordinal) {
        return values()[ordinal];
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
}
