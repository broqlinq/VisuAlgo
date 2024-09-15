package dev.broqlinq.visualgo;

import com.formdev.flatlaf.FlatIntelliJLaf;
import dev.broqlinq.visualgo.ui.sort.Window;

public class Launcher {

    private static final String WINDOW_TITLE = "VisuAlgo";

    public static void main(String[] args) {
        FlatIntelliJLaf.setup();
        Window.display(WINDOW_TITLE);
    }
}
