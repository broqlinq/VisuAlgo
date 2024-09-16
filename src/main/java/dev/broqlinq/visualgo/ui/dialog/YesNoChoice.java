package dev.broqlinq.visualgo.ui.dialog;

import javax.swing.*;

public enum YesNoChoice {

    YES(JOptionPane.YES_OPTION),
    NO(JOptionPane.NO_OPTION);

    final int swingValue;

    YesNoChoice(int swingValue) {
        this.swingValue = swingValue;
    }

    static YesNoChoice fromOrdinal(int ordinal) {
        return values()[ordinal];
    }

    static YesNoChoice fromSwingValue(int swingValue) {
        return switch (swingValue) {
            case JOptionPane.YES_OPTION -> YES;
            case JOptionPane.NO_OPTION,
                 JOptionPane.CANCEL_OPTION,
                 JOptionPane.CLOSED_OPTION -> NO;
            default -> throw new IllegalArgumentException("Invalid swing value: " + swingValue);
        };
    }

    public boolean yes() {
        return this == YES;
    }

    public boolean no() {
        return this == NO;
    }
}
