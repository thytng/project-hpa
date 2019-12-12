import backend.Entrance;
import backend.Position;
import backend.Subgrid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SubgridTest {
    private Subgrid s1, s2, s3, s4;
    private Entrance e4, e34, e12, e13, e24;

    @BeforeEach
    public void createSubgrids() {
        s1 = new Subgrid(1, 1, 7, 7);
        s2 = new Subgrid(7, 1, 12, 7);
        s3 = new Subgrid(1, 7, 4, 9);
        s4 = new Subgrid(4, 7, 12, 13);

        e4 = new Entrance(7, 13);  // this one should be in s4
        e34 = new Entrance(4, 8);  // this one should be in s3 and s4
        e12 = new Entrance(7, 4);  // this one should be in s1 and s2
        e13 = new Entrance(3, 7);   // this one should be in s1 and s3
        e24 = new Entrance(8, 7);   // this one should be in s2 and s4

        s1.addNodeAsEntrance(e12);
        s1.addNodeAsEntrance(e13);

        s2.addNodeAsEntrance(e12);
        s2.addNodeAsEntrance(e24);

        s3.addNodeAsEntrance(e34);

        s4.addNodeAsEntrance(e4);
        s4.addNodeAsEntrance(e34);
        s4.addNodeAsEntrance(e24);
    }

    @Test
    public void testAddNodeAsEntrance() {
        // successful adds
        assert s1.getNodes().contains(e12);
        assert s1.getNodes().contains(e13);

        assert s2.getNodes().contains(e12);
        assert s2.getNodes().contains(e24);

        assert s3.getNodes().contains(e34);

        assert s4.getNodes().contains(e4);
        assert s4.getNodes().contains(e34);
        assert s4.getNodes().contains(e24);

        // trying to add nodes that aren't in the subgrids
        assert !s1.getNodes().contains(e4);
        assert !s2.getNodes().contains(e13);
        assert !s3.getNodes().contains(e12);
        assert !s4.getNodes().contains(e12);

        // trying to add nodes that are in the subgrid but aren't along the edges;
        assert !s1.addNodeAsEntrance(new Entrance(3, 3));
        assert !s2.addNodeAsEntrance(new Entrance(8, 5));
    }

    @Test
    public void testNodeList() {
        // test adding
        Entrance e1 = new Entrance(2, 1);
        s1.addNodeAsEntrance(e1);
        assert s1.getNodes().size() == 3;
        assert s1.getNodes().contains(e1);
        assert s1.getNodes().contains(e12);
        assert s1.getNodes().contains(e13);

        // test removing
        // this shouldn't do anything
        s1.removeNode(e4);
        assert s1.getNodes().size() == 3;
        // this should
        s1.removeNode(e1);
        assert !s1.getNodes().contains(e1);
        assert s1.getNodes().size() == 2;
    }

    @Test
    public void testNeighbors() {
        // relationship should be symmetric
        assert e12.getNeighbors().containsKey(e13);
        assert e13.getNeighbors().containsKey(e12);

        assert e4.getNeighbors().containsKey(e34);
        assert e4.getNeighbors().containsKey(e24);
        assert e24.getNeighbors().containsKey(e4);

        // these aren't neighbors
        assert !e4.getNeighbors().containsKey(e13);
        assert !e13.getNeighbors().containsKey(e4);

        // test the distance between neighbors
        double dist1 = e4.getNeighbors().get(e24);
        assert dist1 == e24.getNeighbors().get(e4);
        assert dist1 == Math.hypot(1, 6);
    }

    @Test
    public void testNodeInSubgrid() {
        Position p1 = new Position(3, 3);
        Position p2 = new Position(8, 5);
        assert s1.nodeInSubgrid(p1);
        assert s2.nodeInSubgrid(p2);

        // testing other neighborly operations :)
        s1.addNoseInsideSubgrid(p1);
        assert s1.getNodes().contains(p1);
        assert s1.getNodes().size() == 3;
        assert p1.getNeighbors().containsKey(e12);
        assert e12.getNeighbors().containsKey(p1);
    }
}
