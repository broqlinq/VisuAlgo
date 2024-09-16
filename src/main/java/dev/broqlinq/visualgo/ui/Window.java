package dev.broqlinq.visualgo.ui;

import dev.broqlinq.visualgo.ui.chart.BarChart;
import dev.broqlinq.visualgo.ui.dialog.DialogType;
import dev.broqlinq.visualgo.ui.dialog.Dialogs;
import dev.broqlinq.visualgo.ui.sort.MergeSortTask;
import dev.broqlinq.visualgo.ui.sort.SortingTask;
import dev.broqlinq.visualgo.ui.util.DefaultObservableArray;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static java.awt.event.InputEvent.CTRL_DOWN_MASK;
import static java.awt.event.InputEvent.SHIFT_DOWN_MASK;
import static javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW;

public class Window {

    /// A singleton instance of {@link Window}.
    private static Window instance;

    // Main frame
    private JFrame frame;

    // Sort components
    private BarChart<Integer> barChart;

    private Window(String title) {
        initFrame(title);
        initComponents();
        initListeners();
        initBindings();
    }

    private void initFrame(String title) {
        frame = new JFrame(title);
//        frame.setIgnoreRepaint(true);
        frame.setSize(getInitialFrameSize());
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    private void initComponents() {
        Random r = new Random();
        var values = IntStream.rangeClosed(1, 200)
                .map(_ -> r.nextInt(1, 200))
                .boxed()
                .toList();
        var chartModel = DefaultObservableArray.ofComparable(values);
        barChart = new BarChart<>(chartModel);
        barChart.setBorder(BorderFactory.createEmptyBorder(32, 32, 32, 32));
        frame.add(new JScrollPane(barChart));

        var keyStroke = KeyStroke.getKeyStroke('K', CTRL_DOWN_MASK | SHIFT_DOWN_MASK);
        barChart.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(keyStroke, "swap");
        barChart.getActionMap().put("swap", new AbstractAction("Swap") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int index = r.nextInt(barChart.getModel().size());
                barChart.getModel().set(index, r.nextInt(5, 100));
            }
        });

        keyStroke = KeyStroke.getKeyStroke('R', CTRL_DOWN_MASK | SHIFT_DOWN_MASK);
        barChart.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(keyStroke, "randomize");
        barChart.getActionMap().put("randomize", new AbstractAction("Randomize") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int size = r.nextInt(5, 15);
                var values = IntStream.rangeClosed(1, 140)
                        .map(_ -> r.nextInt(1, size))
                        .boxed()
                        .toArray(Integer[]::new);

                var model = DefaultObservableArray.ofComparable(values);
                barChart.setModel(model);
            }
        });

        keyStroke = KeyStroke.getKeyStroke('S', CTRL_DOWN_MASK | SHIFT_DOWN_MASK);
        barChart.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(keyStroke, "sort");
        barChart.getActionMap().put("sort", new AbstractAction("Sort") {
            @Override
            public void actionPerformed(ActionEvent e) {
                var choice = Dialogs.confirm("Sort", "Do you want to run sort?", DialogType.PLAIN);
                if (choice.yes()) {
                    SortingTask<Integer> sortTask = new MergeSortTask<>(barChart, 10, TimeUnit.MILLISECONDS);
                    sortTask.execute();
                }
            }
        });
    }

    private void initListeners() {
        WindowListener closeHandler = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                var choice = Dialogs.confirm("Exit", "Do you want to exit?", DialogType.INFO);
                if (choice.yes()) {
                    close();
                }
            }
        };
        frame.addWindowListener(closeHandler);
    }

    private void initBindings() {

    }

    private static Dimension getInitialFrameSize() {
        var tk = Toolkit.getDefaultToolkit();
        var screenSize = tk.getScreenSize();
        screenSize.width = screenSize.width * 2 / 3;
        screenSize.height = screenSize.height * 2 / 3;
        return screenSize;
    }

    public void close() {
        frame.setVisible(false);
        frame.dispose();
    }

    public static Window display(String title) {
        if (instance == null) {
            try {
                EventQueue.invokeAndWait(() -> {
                    instance = new Window(title);
                    instance.display();
                });
            } catch (InterruptedException | InvocationTargetException e) {
                throw new RuntimeException("Failed to instantiate window", e);
            }
        }
        return instance;
    }

    public void display() {
        frame.setVisible(true);
    }
}
