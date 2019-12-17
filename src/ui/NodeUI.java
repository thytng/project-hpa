package ui;

import backend.Node;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Represents a node on the grid.
 */
public class NodeUI extends Ellipse2D.Float {
    public static final int RADIUS = 2;
    public static final Color IN_PATH = Color.GREEN;
    protected Node node;

    public NodeUI(int x, int y) {
        super(x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
        this.x = x;
        this.y = y;
    }

    public Node getNode() {
        return node;
    }
}

