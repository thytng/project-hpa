package ui;

import backend.Entrance;
import backend.Grid;
import backend.Subgrid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GridUI extends JComponent {
    public static int SQUARE_GRID_SIZE = 10;

    private List<Rectangle> grids = new ArrayList<>();

    private List<SubgridUI> rooms = new ArrayList<>();
    private List<NodeUI> nodes = new ArrayList<>();

    private Grid grid;

    private Point startDrag, endDrag;
    private boolean roomDrawn = false;

    private Map<String, Boolean> steps = new HashMap<>();

    public GridUI() {
        grid = new Grid(0, 0, Program.GRID_SIZE, Program.GRID_SIZE);
        initStepBoolean();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (steps.get(Program.STEPS.get(0))) {
                    startDrag = checkIntersection(e.getPoint());
                    endDrag = startDrag;
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (steps.get(Program.STEPS.get(0)) && roomDrawn) {    // Making sure that it's not just a mouse click
                    SubgridUI r = new SubgridUI(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
                    rooms.add(r);
                    grid.addSubgrid(r.getSubgrid());
                    startDrag = null;
                    endDrag = null;
                    roomDrawn = false;
                    repaint();
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                for (Subgrid s : grid.getIndoorsSubgrids()) {
                    System.out.println(s);
                }
                System.out.println("=======================");

                if (steps.get(Program.STEPS.get(1))) {
                    // rounding down so that it's easier to create a door along the border
                    EntranceUI entrance = new EntranceUI(e.getX() - e.getX()%SQUARE_GRID_SIZE,
                            e.getY() - e.getY()%SQUARE_GRID_SIZE);
                    System.out.println(e.getPoint());
                    if (grid.addEntrance((Entrance) entrance.getNode())) {
                        System.out.println("Add entrance");
                        nodes.add(entrance);
                        repaint();
                    }
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (steps.get(Program.STEPS.get(0))) {
                    roomDrawn = true;
                    endDrag = checkIntersection(e.getPoint());
                    repaint();
                }
            }
        });
    }

    private void initStepBoolean() {
        for (String step : Program.STEPS) {
            steps.put(step, true);
        }
    }

    public void switchToStep(String step) {
        if (steps.containsKey(step)) {
            for (String otherStep : steps.keySet()) {
                steps.put(otherStep, false);
            }
            steps.put(step, true);
        }
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

    private void paintSubgrids(Graphics2D g2) {
        for (Rectangle2D.Float room : rooms) {
            g2.setPaint(Color.BLACK);
            g2.draw(room);
//            g2.fill(room);
        }

        if (steps.get(Program.STEPS.get(0))) {
            if (startDrag != null && endDrag != null) {
                g2.setPaint(Color.GRAY);
                Shape r = new SubgridUI(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
                g2.draw(r);
            }
        }
    }

    private void paintNodes(Graphics2D g2) {
        for (Ellipse2D.Float node : nodes) {
            g2.setPaint(node instanceof EntranceUI ? EntranceUI.COLOR : PositionUI.COLOR);
            g2.draw(node);
            g2.fill(node);
        }
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        paintBackground(g2);
        paintSubgrids(g2);
        paintNodes(g2);
    }
}
