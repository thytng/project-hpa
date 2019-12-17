package ui;

import backend.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents the grid made up of mini square grids.
 */
public class GridUI extends JComponent {
    public static int SQUARE_GRID_SIZE = 10;    // size of each mini square grid

    private List<Rectangle> grids = new ArrayList<>();  // list of the mini grids
    private List<SubgridUI> rooms = new ArrayList<>();  // list of the subgrids
    private List<NodeUI> nodes = new ArrayList<>();

    private Grid grid;

    // variables to facilitate room drawing
    private Point startDrag, endDrag;
    private boolean roomDrawn = false;

    private Map<String, Boolean> steps = new HashMap<>();   // steps to take in the program

    // start and end nodes in the path
    private NodeUI startNode;
    private NodeUI endNode;

    // edges to visualize abstract graph
    private Set<Line2D> edges = new HashSet<>();

    public GridUI() {
        grid = new Grid(0, 0, Program.GRID_SIZE, Program.GRID_SIZE);
        initStepBoolean();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // start building a subgrid
                if (steps.get(Program.STEPS.get(0))) {
                    startDrag = checkIntersection(e.getPoint());
                    endDrag = startDrag;
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (steps.get(Program.STEPS.get(0)) && roomDrawn) {    // making sure that it's not just a mouse click
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
                // adding entrances
                if (steps.get(Program.STEPS.get(1))) {
                    // rounding down so that it's easier to create a door along the border
                    EntranceUI entrance = new EntranceUI(e.getX() - e.getX() % SQUARE_GRID_SIZE,
                            e.getY() - e.getY() % SQUARE_GRID_SIZE);
                    if (grid.addEntrance((Entrance) entrance.getNode())) {
                        nodes.add(entrance);
                        repaint();
                    }
                }

                // adding start position (can only add once)
                if (steps.get(Program.STEPS.get(2)) && startNode == null) {
                    PositionUI position = new PositionUI(e.getX(), e.getY());
                    if (grid.plopDownPosition((Position) position.getNode())) {
                        startNode = position;
                        nodes.add(position);
                        repaint();
                    }
                }

                // adding end position (can only add once)
                if (steps.get(Program.STEPS.get(3)) && endNode == null) {
                    PositionUI position = new PositionUI(e.getX(), e.getY());
                    if (grid.plopDownPosition((Position) position.getNode())) {
                        endNode = position;
                        nodes.add(position);
                        repaint();
                    }
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            // finish building a subgrid
            public void mouseDragged(MouseEvent e) {
                if (steps.get(Program.STEPS.get(0))) {
                    roomDrawn = true;
                    endDrag = checkIntersection(e.getPoint());
                    repaint();
                }
            }
        });
    }

    /**
     * Initialize the steps to take, setting all to be false at first.
     */
    private void initStepBoolean() {
        for (String step : Program.STEPS) {
            steps.put(step, false);
        }
    }

    /**
     * Switches to a step.
     * @param step The step to switch to.
     */
    public void switchToStep(String step) {
        if (steps.containsKey(step)) {
            for (String otherStep : steps.keySet()) {
                steps.put(otherStep, false);
            }
            steps.put(step, true);
            repaint();
        }
    }

    /**
     * Draws the mini square grids.
     * @param g2
     */
    private void paintBackground(Graphics2D g2) {
        g2.setPaint(Color.LIGHT_GRAY);
        for (int i = 0; i < getSize().width; i += SQUARE_GRID_SIZE) {
            for (int j = 0; j < getSize().height; j += SQUARE_GRID_SIZE) {
                Rectangle grid = new Rectangle(i, j, SQUARE_GRID_SIZE, SQUARE_GRID_SIZE);
                grids.add(grid);
                g2.draw(grid);
            }
        }
    }

    /**
     * Snaps the cursor to a mini subgrid when creating a subgrid so that they are aligned.
     * @param p Position of the cursor.
     * @return
     */
    private Point checkIntersection(Point p) {
        for (Rectangle grid : grids) {
            if (grid.contains(p)) {
                return new Point(grid.x, grid.y);
            }
        }
        return null;
    }

    /**
     * Draws the subgrids currently on the grid.
     * @param g2
     */
    private void paintSubgrids(Graphics2D g2) {
        for (SubgridUI room : rooms) {
            g2.setPaint(Color.BLACK);
            g2.draw(room);
        }

        // visualizing a subgrid currently being drawn
        if (steps.get(Program.STEPS.get(0))) {
            if (startDrag != null && endDrag != null) {
                g2.setPaint(Color.GRAY);
                Shape r = new SubgridUI(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
                g2.draw(r);
            }
        }
    }

    /**
     * Draws the nodes currently on the grid.
     * @param g2
     */
    private void paintNodes(Graphics2D g2) {
        for (NodeUI node : nodes) {
            g2.setPaint(node instanceof EntranceUI ? EntranceUI.COLOR : PositionUI.COLOR);
            g2.draw(node);
            g2.fill(node);
        }
    }

    /**
     * Draws the edges in the abstract graph.
     * @param g2
     */
    private void paintEdges(Graphics2D g2) {
        for (NodeUI node : nodes) {
            // getting all of a node's neighbors
            Set<Node> neighbors = node.getNode().getNeighbors().keySet();

            // mapping each neighbor to its corresponding GUI
            // this is technically not necessary as the node contains (x,y) coordinates
            // but it's more consistent for graphics to work with graphics (imo).
            Set<NodeUI> neighborsUI = nodes.stream().filter(nodeUI -> neighbors.contains(nodeUI.getNode())).
                    collect(Collectors.toSet());

            // creating the edges
            for (NodeUI neighbor : neighborsUI) {
                Line2D edge = new Line2D.Float((float) node.getX(), (float) node.getY(),
                        (float) neighbor.getX(), (float) neighbor.getY());
                if (!edgeDrawn(edge)) { // making sure an edge isn't added more than once
                    edges.add(edge);
                    repaint();
                }
            }
        }

        // actually drawing the edges if that step is chosen
        if (steps.get(Program.STEPS.get(4))) {
            for (Line2D edge : edges) {
                g2.setPaint(Color.GRAY);
                g2.draw(edge);
            }
        }
    }

    /**
     * Helper method to make sure an edge is only drawn once,
     * otherwise that edge will look thicker than others.
     *
     * @param edge
     */
    private boolean edgeDrawn(Line2D edge) {
        for (Line2D e : edges) {
            if (e.getP1().equals(edge.getP1()) && e.getP2().equals(edge.getP2())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Runs A* and colors the nodes in the path.
     * @param g2
     */
    private void paintPath(Graphics2D g2) {
        if (steps.get(Program.STEPS.get(5))) {
            List<NodeUI> nodeUIs = runAStar();
            if (nodeUIs != null) {
                g2.setPaint(NodeUI.IN_PATH);
                for (NodeUI node : nodeUIs) {
                    g2.fill(node);
                }
            }
        }
    }

    /**
     * Runs the A* algorithm with the added nodes.
     * @return List of nodes in the path.
     */
    private List<NodeUI> runAStar() {
        if (startNode != null && endNode != null) {
            System.out.println(startNode == null);
            List<Node> nodesInPath = AStar.findPath(startNode.getNode(), endNode.getNode());
            return nodes.stream().filter(nodeUI -> {
                assert nodesInPath != null;
                return nodesInPath.contains(nodeUI.getNode());
            }).
                    collect(Collectors.toList());
        }
        return null;
    }

    /**
     * Clears the current path by setting the start and end nodes to null.
     */
    private void clearPath() {
        if (steps.get(Program.STEPS.get(6)) && startNode != null && endNode != null) {
            // removing the start and end nodes from the backend grid
            grid.removeNodeFromGrid(startNode.getNode());
            grid.removeNodeFromGrid(endNode.getNode());
            // removing the graphics nodes from the UI
            nodes.remove(startNode);
            nodes.remove(endNode);
            // resetting these to nulls
            startNode = null;
            endNode = null;
            edges = new HashSet<>();

            repaint();
        }
    }

    /**
     * Creates a new grid to start over.
     */
    private void clearGrid() {
        if (steps.get(Program.STEPS.get(7))) {
            rooms.clear();
            nodes.clear();
            grid = new Grid(0, 0, Program.GRID_SIZE, Program.GRID_SIZE);
            startNode = null;
            endNode = null;

            repaint();
        }
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        paintBackground(g2);
        paintSubgrids(g2);
        paintNodes(g2);
        paintEdges(g2);
        paintPath(g2);
        clearPath();
        clearGrid();
    }
}
