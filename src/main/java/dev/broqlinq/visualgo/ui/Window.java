package dev.broqlinq.visualgo.ui;

import dev.broqlinq.visualgo.ui.dialog.DialogType;
import dev.broqlinq.visualgo.ui.dialog.Dialogs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.reflect.InvocationTargetException;

public class Window {

    private static Window instance;

    private final JFrame frame;

    private Window(String title) {
        frame = new JFrame(title);
        frame.setSize(getInitialFrameSize());
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

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

    private static Dimension getInitialFrameSize() {
        var tk = Toolkit.getDefaultToolkit();
        var screenSize = tk.getScreenSize();
        screenSize.width = screenSize.width * 2 / 3;
        screenSize.height = screenSize.height * 2 / 3;
        return screenSize;
    }

    public void display() {
        frame.setVisible(true);
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
}
