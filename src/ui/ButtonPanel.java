package ui;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Panel for displaying the steps to take.
 */
public class ButtonPanel extends JPanel {
    private Map<Button, Boolean> buttonMap = new HashMap<>();

    private GridUI grid;

    public ButtonPanel(GridUI grid) {
        setLayout(new GridLayout(1, 7));
        this.grid = grid;
        initMap();
        initButtons();
    }

    private void initMap() {
        for (String label : Program.STEPS) {
            Button button = new Button(label);
            add(button);
            buttonMap.put(button, false);
        }
    }

    private void initButtons() {
        for (Button button : buttonMap.keySet()) {
            // set all other buttons' values to be false
            button.addActionListener(e -> {
                for (Button otherButton : buttonMap.keySet()) {
                    buttonMap.put(otherButton, false);
                }
                buttonMap.put(button, true);
                grid.switchToStep(button.getLabel());
                System.out.println(button.getLabel());
            });
        }
    }
}
