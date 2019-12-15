package ui;

import javax.swing.*;
import java.awt.*;

public class Room extends Rectangle {
    private int x1, y1, x2, y2;
    private int width, height;

    public Room(int x1, int y1, int x2, int y2) {
        super(x1, y1, Math.abs(x2 - x1), Math.abs(y2 - y1));
        width = Math.abs(x2 - x1);
        height = Math.abs(y2 - y1);
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = this.x1 + width;
        this.y2 = this.y1 + height;
    }
}
