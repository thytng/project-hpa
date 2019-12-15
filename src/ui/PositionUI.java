package ui;

import backend.Position;

import java.awt.*;

public class PositionUI extends NodeUI {
    public static final Color COLOR = Color.ORANGE;

    public PositionUI(int x, int y) {
        super(x, y);
        this.node = new Position(x, y);
    }
}
