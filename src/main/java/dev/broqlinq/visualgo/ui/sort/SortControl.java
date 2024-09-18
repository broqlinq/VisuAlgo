package dev.broqlinq.visualgo.ui.sort;

import com.formdev.flatlaf.FlatClientProperties;
import dev.broqlinq.visualgo.ui.chart.BarChart;
import dev.broqlinq.visualgo.ui.dialog.DialogType;
import dev.broqlinq.visualgo.ui.dialog.Dialogs;
import dev.broqlinq.visualgo.ui.util.DefaultObservableArray;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.InputEvent;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SortControl<N extends Number & Comparable<N>> {
    private final BarChart<N> barChart;
    private final IntFunction<N> supplier;
    private final SortManager sortManager;
    private JPanel contentPanel;
    private JLabel algorithmLabel;
    private JComboBox<Algorithm> algorithmComboBox;
    private JLabel sortSpeedLabel;
    private JSlider sortSpeedSlider;
    private JButton startButton;
    private JButton cancelButton;
    private JButton randomizeButton;
    private JLabel sampleSizeLabel;
    private JFormattedTextField currentSampleSizeTextField;
    private JButton setSampleSizeButton;

    private Action randomizeAction;
    private Action startAction;
    private Action cancelAction;
    private Action setSampleSizeAction;

    public SortControl(BarChart<N> barChart, IntFunction<N> supplier) {
        this.barChart = barChart;
        this.supplier = supplier;
        this.sortManager = new SortManager();
        sortManager.addPropertyChangeListener(e -> {
            if (e.getPropertyName().equals("status")) {
                boolean taskIsRunning = e.getNewValue() == SortManager.Status.RUNNING;
                setTaskIsRunning(taskIsRunning);
            }
        });

        algorithmLabel.setText("Algorithm");
        algorithmComboBox.setModel(new DefaultComboBoxModel<>(Algorithm.values(Comparator.comparing(Algorithm::getAlgorithmName))));

        sortSpeedLabel.setText("Sort Speed");

        sampleSizeLabel.setText("Sample Size");
        currentSampleSizeTextField.setText(String.valueOf(barChart.getModel().size()));
//        currentSampleSizeTextField.putClientProperty(FlatClientProperties.OUTLINE, FlatClientProperties.OUTLINE_WARNING);

        bindActions();
    }

    private void setTaskIsRunning(boolean running) {
        algorithmLabel.setEnabled(!running);
        algorithmComboBox.setEnabled(!running);
        sortSpeedLabel.setEnabled(!running);
        sortSpeedSlider.setEnabled(!running);
        randomizeAction.setEnabled(!running);
        startAction.setEnabled(!running);
        cancelAction.setEnabled(running);
        currentSampleSizeTextField.setEnabled(!running);
        setSampleSizeAction.setEnabled(!running);
    }

    private void bindActions() {
        setSampleSizeAction = new SetSampleSizeAction();
        randomizeAction = new RandomizeAction();
        startAction = new StartAction();
        cancelAction = new CancelAction();
        cancelAction.setEnabled(false);

        randomizeButton.setAction(randomizeAction);
        startButton.setAction(startAction);
        cancelButton.setAction(cancelAction);
        setSampleSizeButton.setAction(setSampleSizeAction);

        getInputMap().put(getKeyStroke('R'), "randomize");
        getActionMap().put("randomize", randomizeAction);

        getInputMap().put(getKeyStroke('S'), "start");
        getActionMap().put("start", startAction);

        getInputMap().put(getKeyStroke('C'), "cancel");
        getActionMap().put("cancel", cancelAction);
    }

    private InputMap getInputMap() {
        return contentPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    private static KeyStroke getKeyStroke(char character) {
        return KeyStroke.getKeyStroke(character, InputEvent.SHIFT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK);
    }

    private ActionMap getActionMap() {
        return contentPanel.getActionMap();
    }

    private static void informSortIsRunning() {
        Dialogs.inform("Sort Is Running", "Sort algorithm is already running.", DialogType.ERROR);
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }

    private void createUIComponents() {
        var format = NumberFormat.getCompactNumberInstance();
        currentSampleSizeTextField = new JFormattedTextField(NumberFormat.getIntegerInstance());
        currentSampleSizeTextField.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
        currentSampleSizeTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                currentSampleSizeTextField.selectAll();
            }
        });
        currentSampleSizeTextField.setInputVerifier(new InputVerifier() {

            @Override
            public boolean shouldYieldFocus(JComponent source, JComponent target) {
                boolean shouldYield = verify(source);
                if (!shouldYield) {
                    source.putClientProperty(FlatClientProperties.OUTLINE, FlatClientProperties.OUTLINE_ERROR);
                } else {
                    source.putClientProperty(FlatClientProperties.OUTLINE, null);
                }
                return shouldYield;
            }

            @Override
            public boolean verify(JComponent input) {
                if (input != currentSampleSizeTextField)
                    return true;

                try {
                    format.parseObject(currentSampleSizeTextField.getText());
                    return true;
                } catch (ParseException _) {
                    return false;
                }
            }
        });
    }

    public void cancelRunningTask() {
        sortManager.cancel();
    }

    private class RandomizeAction extends AbstractAction {

        public RandomizeAction() {
            super("Randomize");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            barChart.getModel().shuffle();
        }

    }

    private class StartAction extends AbstractAction {

        StartAction() {
            super("Start");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            var choice = Dialogs.confirm("Start Sort", "Do you want to run sort?", DialogType.PLAIN);
            var algorithm = (Algorithm) algorithmComboBox.getSelectedItem();
            if (algorithm == null) {
                Dialogs.inform("No Algorithm Selected", "No algorithm was selected.", DialogType.ERROR);
                return;
            }

            int pause = 6 - sortSpeedSlider.getValue();
            var task = algorithm.newTask(barChart, pause, TimeUnit.MILLISECONDS);
            if (choice.yes() && (sortManager.isTaskRunning() || !sortManager.submit(task))) {
                informSortIsRunning();
            }
        }
    }

    private class CancelAction extends AbstractAction {

        CancelAction() {
            super("Cancel");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!sortManager.isTaskRunning()) {
                informSortIsRunning();
                return;
            }

            var choice = Dialogs.confirm("Cancel Sort", "Do you want to cancel sort?", DialogType.PLAIN);
            if (choice.yes()) {
                sortManager.cancel();
                setEnabled(false);
            }
        }
    }

    private class SetSampleSizeAction extends AbstractAction {

        SetSampleSizeAction() {
            super("Set Size");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (sortManager.isTaskRunning()) {
                informSortIsRunning();
                return;
            }

            var input = currentSampleSizeTextField.getValue();
            if (input instanceof Long number) {
                if (number != barChart.getModel().size()) {
                    var values = IntStream.rangeClosed(1, number.intValue())
                            .mapToObj(supplier)
                            .collect(Collectors.toCollection(ArrayList::new));
                    Collections.shuffle(values);
                    barChart.setModel(DefaultObservableArray.ofComparable(values));
                }
            }
        }
    }
}
