package ui;

import backend.Position;

import java.awt.*;

/**
 * Represents a position on the grid.
 * A position has all the characteristics of a node(UI)
 * and can be anywhere within a subgrid.
 */
public class PositionUI extends NodeUI {
    public static final Color COLOR = Color.ORANGE;

    public PositionUI(int x, int y) {
        super(x, y);
        this.node = new Position(x, y);
    }
}
