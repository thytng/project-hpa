package ui;

import backend.Entrance;

import java.awt.*;

public class EntranceUI extends NodeUI {
    public static final Color COLOR = Color.GRAY;

    public EntranceUI(int x, int y) {
        super(x, y);
        this.node = new Entrance(x, y);
    }
}
