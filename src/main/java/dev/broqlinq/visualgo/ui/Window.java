package dev.broqlinq.visualgo.ui;

import dev.broqlinq.visualgo.ui.chart.BarChart;
import dev.broqlinq.visualgo.ui.dialog.DialogType;
import dev.broqlinq.visualgo.ui.dialog.Dialogs;
import dev.broqlinq.visualgo.ui.sort.SortControl;
import dev.broqlinq.visualgo.ui.util.DefaultObservableArray;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Window {

    /// A singleton instance of {@link Window}.
    private static Window instance;

    // Main frame
    private JFrame frame;

    // Sort-related components
    private BarChart<Integer> barChart;
    private SortControl<?> sortControl;

    private JTabbedPane tabbedPane;

    private Window(String title) {
        initFrame(title);
        initComponents();
        initListeners();
        initBindings();
    }

    private void initFrame(String title) {
        frame = new JFrame(title);
        frame.setSize(getInitialFrameSize());
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    private void initComponents() {
        var values = IntStream.rangeClosed(1, 500)
                .boxed()
                .collect(Collectors.toCollection(ArrayList::new));
        Collections.shuffle(values);

        var chartModel = DefaultObservableArray.ofComparable(values);
        barChart = new BarChart<>(chartModel);
        barChart.setBorder(BorderFactory.createEmptyBorder(32, 32, 32, 32));
        {
            JPanel p = new JPanel(new BorderLayout());
            p.add(barChart, BorderLayout.CENTER);
            frame.add(new JScrollPane(barChart, JScrollPane.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS));
        }

        sortControl = new SortControl<>(barChart, i -> i);

        var sortPanel = new JPanel(new BorderLayout());
        sortPanel.add(sortControl.getContentPanel(), BorderLayout.WEST);
        sortPanel.add(new JScrollPane(barChart), BorderLayout.CENTER);
        frame.add(sortPanel);
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
        sortControl.cancelRunningTask();
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
