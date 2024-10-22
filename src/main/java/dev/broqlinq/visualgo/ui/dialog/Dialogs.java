package dev.broqlinq.visualgo.ui.dialog;

import javax.swing.*;

public final class Dialogs {

    private Dialogs() {}

    public static void inform(String title, String message, DialogType type) {
        JOptionPane.showMessageDialog(
                null,
                message,
                title,
                type.swingValue
        );
    }

    public static YesNoChoice confirm(String title, String message, DialogType type) {
        int choice = JOptionPane.showConfirmDialog(
                null,
                message,
                title,
                JOptionPane.YES_NO_OPTION,
                type.swingValue
        );
        return YesNoChoice.fromSwingValue(choice);
    }

    public static YesNoCancelChoice confirmOrCancel(String title, String message, DialogType type) {
        int choice = JOptionPane.showConfirmDialog(
                null,
                message,
                title,
                JOptionPane.YES_NO_CANCEL_OPTION,
                type.swingValue
        );
        return YesNoCancelChoice.fromSwingValue(choice);
    }

}
