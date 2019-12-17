package backend;

import java.util.*;

/**
 * Represents a node in the graph.
 */
public class Node {
    private double x, y;

    private double f, g, h;

    private Node parent, goal;

    private Map<Node, Double> neighbors;

    public Node(double x, double y) {
        this.x = x;
        this.y = y;

        this.f = 0;
        this.g = 0;
        this.h = 0;

        this.neighbors = new HashMap<>();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setParent(Node parent) {
        this.parent = parent;
        addNeighbor(parent);
        computeG();
        computeF();
    }

    /**
     * Sets the distance from this node to the start node.
     */
    private void computeG() {
        g = parent.g + neighbors.get(parent);
    }

    /**
     * Returns the distance from this node to the start node.
     * @return
     */
    public double getG() {
        return g;
    }

    /**
     * Sets the end node.
     * @param goal
     */
    public void setGoal(Node goal) {
        this.goal = goal;
        computeH();
    }

    /**
     * Computes the heuristic distance from this node to the end node.
     */
    private void computeH() {
        h = computeDistance(this, goal);
    }

    /**
     * Returns the heuristic distance from this node to the end node.
     * @return
     */
    public double getH() {
        return h;
    }

    /**
     * Computes the f score: f = g + h
     */
    private void computeF() {
        f = g + h;
    }

    /**
     * Returns the f score: f = g + h
     * @return
     */
    public double getF() {
        return f;
    }

    /**
     * Adds another node as its neighbor by putting it as a key in the map,
     * and the distance between them as the value.
     * @param node
     */
    public void addNeighbor(Node node) {
        if (neighbors.get(node) == null) {
            neighbors.put(node, computeDistance(this, node));
        }
    }

    public void removeNeighbor(Node node) {
        neighbors.remove(node);
    }

    public Map<Node, Double> getNeighbors() {
        return neighbors;
    }

    public Node getParent() {
        return parent;
    }

    public String toString() {
        return this.getClass() + " at position (" + x + "," + y + ")";
    }

    /**
     * Computes the Euclidean distance between any two nodes
     * @param n1
     * @param n2
     * @return
     */
    public static double computeDistance(Node n1, Node n2) {
        return Math.hypot(n1.getX() - n2.getX(), n1.getY() - n2.getY());
    }
}
