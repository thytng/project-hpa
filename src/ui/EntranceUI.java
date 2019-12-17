package ui;

import backend.Entrance;

import java.awt.*;

/**
 * Represents an entrance on the grid.
 * Has all the characteristics of a node(UI)
 * but can only be along the edge of subgrid(s).
 */
public class EntranceUI extends NodeUI {
    public static final Color COLOR = Color.GRAY;

    public EntranceUI(int x, int y) {
        super(x, y);
        this.node = new Entrance(x, y);
    }
}
