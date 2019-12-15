package ui;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Program extends JFrame {
    public static int GRID_SIZE = 800;

    public static final List<String> STEPS = Arrays.asList("Build Subgrid", "Add Entrance", "Add Start Position",
            "Add End Position", "Build Graph", "Find Path", "Clear Path");

    public Program() {
        this.setSize(GRID_SIZE, GRID_SIZE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridUI grid = new GridUI();
        this.add(new ButtonPanel(grid), BorderLayout.PAGE_START);
        this.add(grid, BorderLayout.CENTER);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Program();
    }
}
