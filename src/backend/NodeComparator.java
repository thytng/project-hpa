package backend;

import java.util.Comparator;

/**
 * Compares two nodes based on their f scores.
 */
public class NodeComparator implements Comparator<Node> {
    @Override
    public int compare(Node n1, Node n2) {
        return Double.compare(n1.getF(), n2.getF());
    }
}
