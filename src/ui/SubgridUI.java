package ui;

import backend.Subgrid;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class SubgridUI extends Rectangle2D.Float {
    private int x1, y1, x2, y2;
    private int width, height;

    public static final Color COLOR = Color.PINK;

    private Subgrid subgrid;

    public SubgridUI(int x1, int y1, int x2, int y2) {
        super(x1, y1, Math.abs(x2 - x1), Math.abs(y2 - y1));
        width = Math.abs(x2 - x1);
        height = Math.abs(y2 - y1);
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = this.x1 + width;
        this.y2 = this.y1 + height;

        this.subgrid = new Subgrid(this.x1, this.y1, this.x2, this.y2);
    }

    public Subgrid getSubgrid() {
        return subgrid;
    }
}
