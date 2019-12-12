package backend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A weighted graph with entrances as nodes and the distance between any two
 * neighboring entrances as the weight of the edge.
 */
public class Graph {
    private Map<Node, Map<Node, Double>> graph;

    /**
     * Constructs a graph from the grid, adding all entrances in the subgrids as nodes.
     * @param grid
     */
    public Graph(Grid grid) {
        graph = new HashMap<>();

        List<Node> nodes = grid.getNodes();
        for (Node node : nodes) {
            addNode(node);
        }
    }

    /**
     * Adds a node to the graph by making it the key and its map of neighbors the value.
     * @param node
     */
    public void addNode(Node node) {
        graph.put(node, node.getNeighbors());
    }

    public void removeNode(Node node) {
        graph.remove(node);
    }

    public Map<Node, Map<Node, Double>> getGraph() {
        return graph;
    }
}
