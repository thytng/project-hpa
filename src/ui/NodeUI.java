package ui;

import backend.Node;

import java.awt.geom.Ellipse2D;

public class NodeUI extends Ellipse2D.Float {
    public static final int RADIUS = 2;
    private int x, y;
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

