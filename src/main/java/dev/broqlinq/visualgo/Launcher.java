package dev.broqlinq.visualgo;

import com.formdev.flatlaf.FlatIntelliJLaf;
import dev.broqlinq.visualgo.ui.Window;

import javax.swing.*;
import java.awt.*;

public class Launcher {

    private static final String WINDOW_TITLE = "VisuAlgo";

    public static void main(String[] args) {
        FlatIntelliJLaf.setup();
        UIManager.getDefaults().put("BarChartColorModel.baseColor", Color.DARK_GRAY);
        UIManager.getDefaults().put("BarChartColorModel.selectionColor", Color.GREEN);
        Window.display(WINDOW_TITLE);
    }
}
