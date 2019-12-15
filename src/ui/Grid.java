package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class Grid extends JComponent {
    public static int SQUARE_GRID_SIZE = 10;

    private List<Room> rooms = new ArrayList<>();

    private List<Rectangle> grids = new ArrayList<>();

    private Point startDrag, endDrag;

    public Grid() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startDrag = checkIntersection(e.getPoint());
                endDrag = startDrag;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Room r = new Room(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
                rooms.add(r);
                startDrag = null;
                endDrag = null;
                repaint();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                endDrag = checkIntersection(e.getPoint());
                repaint();
            }
        });
    }

    private void paintBackground(Graphics2D g2){
        g2.setPaint(Color.LIGHT_GRAY);

        for (int i = 0; i < getSize().width; i += SQUARE_GRID_SIZE) {
            for (int j = 0; j < getSize().height; j += SQUARE_GRID_SIZE) {
                Rectangle grid = new Rectangle(i, j, SQUARE_GRID_SIZE, SQUARE_GRID_SIZE);
                grids.add(grid);
                g2.draw(grid);
            }
        }
    }

    private Point checkIntersection(Point p) {
        for (Rectangle grid : grids) {
            if (grid.contains(p)) {
                return new Point(grid.x, grid.y);
            }
        }
        return null;
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        paintBackground(g2);

        for (Shape s : rooms) {
            g2.setPaint(Color.PINK);
            g2.draw(s);
            g2.setPaint(Color.PINK);
            g2.fill(s);
        }

        if (startDrag != null && endDrag != null) {
            g2.setPaint(Color.GRAY);
            Shape r = new Room(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
            g2.draw(r);
        }
    }
}
