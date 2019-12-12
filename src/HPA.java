import backend.Entrance;
import backend.Grid;
import backend.Subgrid;

public class HPA {
    public static void main(String[] args) {
        Grid grid = new Grid(0, 0, 13, 15);
        Subgrid s1 = new Subgrid(1, 1, 7, 7);
        Subgrid s2 = new Subgrid(7, 1, 12, 7);
        Subgrid s3 = new Subgrid(1, 7, 4, 9);
        Subgrid s4 = new Subgrid(4, 7, 12, 13);

        Entrance e4 = new Entrance(7, 13);  // this one should be in s4
        Entrance e34 = new Entrance(4, 8);  // this one should be in s3 and s4

    }
}
