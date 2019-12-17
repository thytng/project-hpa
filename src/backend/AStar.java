package backend;

import java.util.*;

public class AStar {

    /**
     * Runs the A* algorithm, assuming that nodes have been processed (i.e. neighbors have been added).
     * @param start
     * @param goal
     * @return The list of nodes in the path.
     */
    public static List<Node> findPath(Node start, Node goal) {
        PriorityQueue<Node> open = new PriorityQueue<>(new NodeComparator());
        List<Node> closed = new ArrayList<>();

        open.add(start);

        // while there are nodes to explore
        while (open.size() > 0) {
            // get the current node (one with the smallest f score)
            Node currentNode = open.poll();
            // put it in the closed list
            closed.add(currentNode);

            // if current node is the goal, trace back to find the path
            if (currentNode == goal) {
                return findPath(currentNode);
            }

            // if not, get all of its neighbors (nodes that we can get to)
            Map<Node, Double> neighbors = currentNode.getNeighbors();
            for (Node neighbor : neighbors.keySet()) {
                if (closed.contains(neighbor)) {
                    continue;
                }

                neighbor.setGoal(goal);
                // compute g and f scores based on the current positions
                double g = currentNode.getG() + neighbors.get(neighbor);
                double f = g + neighbor.getH();

                // if the neighbor is already in open, compare its f score to
                // the f score computed by getting to it from the current node
                if (open.contains(neighbor)) {
                    // if the neighbor has a lower f score with the older path,
                    // then skip it and move onto the next neighbor
                    if (f > neighbor.getF()) {
                        continue;
                    }
                    // add it to open otherwise
                } else {
                    open.add(neighbor);
                }
                // re-compute the neighbor's g and f scores and set its parent to be the current node
                neighbor.setParent(currentNode);
            }
        }
        return null;
    }

    /**
     * Finds the actual nodes in the path by traversing back from the end to the start nodes.
     * These nodes are the parent nodes.
     * @param end
     * @return
     */
    private static List<Node> findPath(Node end) {
        List<Node> path = new ArrayList<>();
        Node currentNode = end;
        while (currentNode != null) {
            path.add(currentNode);
            currentNode = currentNode.getParent();
        }
        Collections.reverse(path);
        return path;
    }
}
