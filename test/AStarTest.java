import backend.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AStarTest {
    private Grid grid;
    private Subgrid s1, s2, s3, s4;
    private Entrance e4, e34, e12, e13, e24;

    @BeforeEach
    public void createSubgrids() {
        grid = new Grid(0, 0, 13, 15);

        s1 = new Subgrid(1, 1, 7, 7);
        s2 = new Subgrid(7, 1, 12, 7);
        s3 = new Subgrid(1, 7, 4, 9);
        s4 = new Subgrid(4, 7, 12, 13);

        grid.addSubgrid(s1);
        grid.addSubgrid(s2);
        grid.addSubgrid(s3);
        grid.addSubgrid(s4);

        e4 = new Entrance(7, 13);
        e34 = new Entrance(4, 8);
        e12 = new Entrance(7, 4);
        e13 = new Entrance(3, 7);
        e24 = new Entrance(8, 7);

        grid.addNodeAsEntrance(e4);
        grid.addNodeAsEntrance(e34);
        grid.addNodeAsEntrance(e12);
        grid.addNodeAsEntrance(e13);
        grid.addNodeAsEntrance(e24);
    }

    @Test
    public void testFindPathDifferentSubgrids() {
        Position start = new Position(11, 2);   // in s2
        Position goal = new Position(3, 8);    // in s3
        assert grid.plopDownPosition(start);
        assert grid.findNodesSubgrid(start) == s2;
        assert s2.getNodes().contains(start);
        assert grid.plopDownPosition(goal);
        assert grid.getNodes().contains(goal);

        List<Node> path = AStar.findPath(start, goal);
        assert path != null;
        assert path.size() == 4;
        assert path.contains(start);
        assert path.contains(goal);
        assert path.contains(e12);
        assert path.contains(e13);
        printList(path);
    }

    @Test
    public void testFindPathWithinSameSubgrid() {
        Position start = new Position(10, 11);  // in s4
        Position goal = new Position(7, 9);     // also in s4
        grid.plopDownPosition(start);
        grid.plopDownPosition(goal);

        List<Node> path = AStar.findPath(start, goal);
        assert path.size() == 2;
        assert path.contains(start);
        assert path.contains(goal);
        printList(path);
    }

    @Test
    public void testFindPathFromOutside() {
        Position start = new Position(6, 4);
        Position goal = new Position(2, 14);
        grid.plopDownPosition(start);
        grid.plopDownPosition(goal);
        List<Node> path = AStar.findPath(start, goal);
        printList(path);
    }

    private void printList(List list) {
        for (Object o : list) {
            System.out.println(o);
        }
    }
}
