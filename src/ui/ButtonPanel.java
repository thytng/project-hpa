package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class ButtonPanel extends JPanel {
    private Map<Button, Boolean> buttonMap = new HashMap<>();
    public ButtonPanel() {
        setLayout(new GridLayout(1, 7));
        initMap();
        initButtons();



    }

    private void initMap() {
        Button drawSubgrid = new Button("Draw Subgrid");
        add(drawSubgrid);
        Button addEntrance = new Button("Add Entrance");
        add(addEntrance);
        Button addStartPos = new Button("Add Start Position");
        add(addStartPos);
        Button addEndPos = new Button("Add End Position");
        add(addEndPos);
        Button buildGraph = new Button("Build Graph");
        add(buildGraph);
        Button findPath = new Button("Find Path");
        add(findPath);
        Button clearPath = new Button("Clear Path");
        add(clearPath);

        buttonMap.put(drawSubgrid, false);
        buttonMap.put(addEntrance, false);
        buttonMap.put(addStartPos, false);
        buttonMap.put(addEndPos, false);
        buttonMap.put(buildGraph, false);
        buttonMap.put(findPath, false);
        buttonMap.put(clearPath, false);
    }

    private void initButtons() {
        for (Button button : buttonMap.keySet()) {
            // set all other buttons' values to be false
            button.addActionListener(e -> {
                for (Button otherButton : buttonMap.keySet()) {
                    buttonMap.put(otherButton, false);
                }
                buttonMap.put(button, true);
            });
        }
    }
}
