package dev.broqlinq.visualgo.ui.sort.dialog;

import javax.swing.*;

public final class Dialogs {

    private Dialogs() {}

    public static YesNoChoice confirm(String title, String message, DialogType type) {
        int choice = JOptionPane.showConfirmDialog(
                null,
                message,
                title,
                JOptionPane.YES_NO_OPTION,
                type.swingValue
        );
        return YesNoChoice.fromOrdinal(choice);
    }

    public static YesNoCancelChoice confirmOrCancel(String title, String message, DialogType type) {
        int choice = JOptionPane.showConfirmDialog(
                null,
                message,
                title,
                JOptionPane.YES_NO_CANCEL_OPTION,
                type.swingValue
        );
        return YesNoCancelChoice.fromOrdinal(choice);
    }
}
