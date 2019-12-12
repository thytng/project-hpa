package backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private void computeG() {
        g = parent.g + neighbors.get(parent);
    }

    public double getG() {
        return g;
    }

    public void setGoal(Node goal) {
        this.goal = goal;
        computeH();
    }

    private void computeH() {
        h = computeDistance(this, goal);
    }

    public double getH() {
        return h;
    }

    private void computeF() {
        f = g + h;
    }

    public double getF() {
        return f;
    }

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

    public static double computeDistance(Node n1, Node n2) {
        return Math.hypot(n1.getX() - n2.getX(), n1.getY() - n2.getY());
    }

    public Node getParent() {
        return parent;
    }

    public String toString() {
        return this.getClass() + " at position (" + x + "," + y + ")";
    }
}
