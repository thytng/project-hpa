package backend;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a subgrid/room on the grid.
 */
public class Subgrid {
    private double x1, y1, x2, y2;  // upper-left and lower-right corners
    private List<Node> nodes;   // nodes in subgrid

    /**
     * Creates a new subgrid, checking that the coordinates are valid.
     * @param x1 Upper-left x
     * @param y1 Upper-left y
     * @param x2 Lower-right x
     * @param y2 Lower-right y
     */
    public Subgrid(double x1, double y1, double x2, double y2) {
        if (x1 > x2 || y1 > y2) {
            throw new IllegalArgumentException("(x1, y1) must be the upper-left corner and" +
                    "(x2, y2) the lower-right corner.");
        }
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;

        this.nodes = new ArrayList<>();
    }

    /**
     * Adds a node as an entrance to the subgrid, first checking whether it is along the edges.
     * @param entrance
     * @return Whether the entrance was successfully added.
     */
    public boolean addNodeAsEntrance(Entrance entrance) {
        if (nodes.contains(entrance)) return true;  // do nothing if the entrance is already added
        if (entranceAlongEdges(entrance)) {
            addNeighborsToNode(entrance);
            nodes.add(entrance);
            return true;
        }
        return false;
    }

    /**
     * Checks whether a possible entrance is along the four edges of the subgrid.
     * @param entrance
     * @return
     */
    public boolean entranceAlongEdges(Entrance entrance) {
        double x = entrance.getX(), y = entrance.getY();
        if (x == x1 || x == x2) {
            return y >= y1 && y <= y2;
        } else if (y == y1 || y == y2) {
            return x >= x1 && x <= x2;
        }
        return false;
    }

    /**
     * Adds a node inside the subgrid.
     * @param node
     * @return Whether the node was successfully added
     */
    public boolean addNoseInsideSubgrid(Node node) {
        if (nodes.contains(node)) return true;  // do nothing if it is already added
        if (nodeInSubgrid(node)) {
            addNeighborsToNode(node);
            nodes.add(node);
            return true;
        }
        return false;
    }

    /**
     * Checks whether the node is within the subgrid.
     * @param node
     * @return
     */
    public boolean nodeInSubgrid(Node node) {
        double x = node.getX(), y = node.getY();
        return x >= x1 && x <= x2 && y >= y1 && y <= y2;
    }

    private void addNeighborsToNode(Node node) {
        for (Node neighbor : nodes) {
            if (neighbor != node) { // a node cannot be its own neighbor
                // make them add each other as neighbors
                node.addNeighbor(neighbor);
                neighbor.addNeighbor(node);
            }
        }
    }

    /**
     * Remove a node from the subgrid,
     * making sure to remove the neighbor relationships as well.
     * @param node
     */
    public void removeNode(Node node) {
        nodes.remove(node);
        for (Node oldNeighbor : nodes) {
            node.removeNeighbor(oldNeighbor);
            oldNeighbor.removeNeighbor(node);
        }
    }

    /**
     * Returns all the nodes saved as entrances to the subgrid.
     * @return
     */
    public List<Node> getNodes() {
        return nodes;
    }

    @Override
    public String toString() {
        return "A subgrid at position (" + x1 + "," + y1 + "),(" + x2 + "," + y2 + ")";
    }
}
