import backend.Entrance;
import backend.Grid;
import backend.Position;
import backend.Subgrid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GridTest {
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

        e4 = new Entrance(7, 13);  // this one should be in s4
        e34 = new Entrance(4, 8);  // this one should be in s3 and s4
        e12 = new Entrance(7, 4);  // this one should be in s1 and s2
        e13 = new Entrance(3, 7);   // this one should be in s1 and s3
        e24 = new Entrance(8, 7);   // this one should be in s2 and s4

        grid.addNodeAsEntrance(e4);
        grid.addNodeAsEntrance(e34);
        grid.addNodeAsEntrance(e12);
        grid.addNodeAsEntrance(e13);
        grid.addNodeAsEntrance(e24);
    }

    @Test
    public void testAddSubgrids() {
        assert grid.getIndoorsSubgrids().size() == 4;
    }

    @Test
    public void testAddNodeToEntrance() {
        // trying to match the entrance to their subgrids
        assert s4.getNodes().contains(e4);
        assert s1.getNodes().contains(e13);
        // making sure the entrance gets added to both subgrids
        assert s4.getNodes().contains(e24);
        assert s2.getNodes().contains(e24);

        // making sure the entrances are added to the list and there are no repeats
        assert grid.getNodes().contains(e4);
        assert grid.getNodes().contains(e34);
        assert grid.getNodes().contains(e12);
        assert grid.getNodes().size() == 5;
    }

    @Test
    public void testRemoveFromGrid() {
        grid.removeNodeFromGrid(e4);
        assert !grid.getNodes().contains(e4);
        assert !s4.getNodes().contains(e4);
        assert !e24.getNeighbors().containsKey(e4); // making sure it removes from the neighbor list as well
        assert e4.getNeighbors().isEmpty(); // this one should have zero neighbors left :(
    }

    @Test
    public void testPlopDownPosition() {
        Position p0 = new Position(1, 14);  // outside
        Position p1 = new Position(7, 3);  // in s1 and s2 but should only return s1
        Position p3 = new Position(4, 9);  // in the lower right corner of s3

        // add it outside so none of the indoor subgrids should contain it
        assert grid.plopDownPosition(p0);
        assert grid.getNodes().contains(p0);
        assert !s1.getNodes().contains(p0);
        assert !s2.getNodes().contains(p0);
        assert !s3.getNodes().contains(p0);
        assert !s4.getNodes().contains(p0);
        // but it should have one neighbor that is e4
        assert e4.getNeighbors().containsKey(p0);
        assert p0.getNeighbors().containsKey(e4);

        // added to both subgrids
        assert grid.plopDownPosition(p1);
        assert s1.getNodes().contains(p1);
        assert !s2.getNodes().contains(p1);

        assert grid.plopDownPosition(p3);
        assert s3.getNodes().contains(p3);
        assert !s4.getNodes().contains(p3);

        // testing the length of the list of nodes
        assert grid.getNodes().size() == 8;

        // making sure they are registered as neighbors
        assert p1.getNeighbors().containsKey(e12);
        assert e12.getNeighbors().containsKey(p1);
        assert e13.getNeighbors().containsKey(p3);

        // removing the positions
        grid.removeNodeFromGrid(p3);
        assert !grid.getNodes().contains(p3);
        assert p3.getNeighbors().isEmpty();
        assert !e13.getNeighbors().containsKey(p3);
        assert !s3.getNodes().contains(p3);
        assert grid.getNodes().size() == 7;
    }
}
