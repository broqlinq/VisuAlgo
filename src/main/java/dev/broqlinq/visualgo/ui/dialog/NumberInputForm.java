package dev.broqlinq.visualgo.ui.dialog;

import javax.swing.*;

public class NumberInputForm<N extends Number> extends JPanel {
    private JPanel contentPanel;
    private JFormattedTextField inputField;
    private JLabel inputMessageLabel;

    NumberInputForm(String message, N initialValue, N minValue, N maxValue) {
        add(contentPanel);

        inputMessageLabel.setText(message);

        inputField.setText(String.valueOf(initialValue));
    }
}
