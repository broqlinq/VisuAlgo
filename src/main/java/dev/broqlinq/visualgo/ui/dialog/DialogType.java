package dev.broqlinq.visualgo.ui.dialog;

import javax.swing.*;

public enum DialogType {

    PLAIN(JOptionPane.PLAIN_MESSAGE),
    ERROR(JOptionPane.ERROR_MESSAGE),
    INFO(JOptionPane.INFORMATION_MESSAGE),
    WARNING(JOptionPane.WARNING_MESSAGE);

    final int swingValue;

    DialogType(int swingValue) {
        this.swingValue = swingValue;
    }

}
