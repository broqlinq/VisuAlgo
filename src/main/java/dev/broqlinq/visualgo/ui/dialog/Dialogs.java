package dev.broqlinq.visualgo.ui.dialog;

import com.formdev.flatlaf.ui.FlatOptionPaneUI;

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

    public static <N extends Number> N numericInput(String title, String message, N initialValue, N minValue, N maxValue, Class<N> type) {
        NumberInputForm<N> form = new NumberInputForm<>(message, initialValue, minValue, maxValue);
        JDialog dialog = new JDialog((JFrame) null, true);
        dialog.setLocationRelativeTo(null);
        dialog.getContentPane().add(form);
        dialog.setVisible(true);
        JOptionPane pane = new JOptionPane();
        return null;
    }

}
