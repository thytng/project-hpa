package ui;

import javax.swing.*;
import java.awt.*;

public class Program extends JFrame {
    public static int GRID_SIZE = 800;

    public Program() {
        this.setSize(GRID_SIZE, GRID_SIZE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(new ButtonPanel(), BorderLayout.PAGE_START);
        this.add(new GridUI(), BorderLayout.CENTER);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Program();
    }
}
