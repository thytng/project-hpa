package backend;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    private List<Subgrid> indoorsSubgrids;
    private Subgrid outdoorSubgrid;

    private List<Node> nodes;

    private Node currentStart, currentGoal;

    public Grid(double x1, double y1, double x2, double y2) {
        outdoorSubgrid = new Subgrid(x1, y1, x2, y2);

        this.indoorsSubgrids = new ArrayList<>();
        this.nodes = new ArrayList<>();
    }

    public List<Subgrid> getIndoorsSubgrids() {
        return indoorsSubgrids;
    }

    public void addSubgrid(Subgrid subgrid) {
        indoorsSubgrids.add(subgrid);
    }

    /**
     * Adds a node as an entrance to the corresponding subgrid(s) it is in.
     * @param entrance
     * @return a boolean indicating whether it was successfully added to at least one subgrid.
     */
    public boolean addNodeAsEntrance(Entrance entrance) {
        boolean added = false;
        int timesAdded = 0;
        for (Subgrid subgrid : indoorsSubgrids) {
            if (subgrid.addNodeAsEntrance(entrance)) {
                added = true;
                timesAdded++;
                addNodeToList(entrance);
            }
        }
        // if it's only added once, this means that it's an entrance leading outside
        if (timesAdded == 1) {
            outdoorSubgrid.addNoseInsideSubgrid(entrance);
        }
        return added;
    }

    /**
     * Adds a position to the grid, prioritizing adding it to an indoor subgrid first.
     * If it's not in any of the indoor subgrids, try adding it to the outdoor one.
     * A position can only be in one subgrid.
     * @param position
     * @return
     */
    public boolean plopDownPosition(Position position) {
        boolean added = false;
        for (Subgrid subgrid : indoorsSubgrids) {
            // add it to the first grid that contains it
            if (subgrid.addNoseInsideSubgrid(position)) {
                addNodeToList(position);
                return true;
            }
        }
        if (!added) {
            added = outdoorSubgrid.addNoseInsideSubgrid(position);
            addNodeToList(position);
        }
        return added;
    }

    /**
     * Takes in a node and finds the subgrid that it is in,
     * returning null if it's in none of the subgrids.
     * @param node
     * @return
     */
    public Subgrid findNodesSubgrid(Node node) {
        for (Subgrid subgrid : indoorsSubgrids) {
            if (subgrid.nodeInSubgrid(node)) {
                return subgrid;
            }
        }
        return null;
    }

    public void removeNodeFromGrid(Node node) {
        nodes.remove(node);
        for (Subgrid subgrid : indoorsSubgrids) {
            subgrid.removeNode(node);
        }
        outdoorSubgrid.removeNode(node);
    }

    private void addNodeToList(Node node) {
        if (!nodes.contains(node)) {
            nodes.add(node);
        }
    }

    public List<Node> getNodes() {
        return nodes;
    }
}
