import ui.SubgridUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;


public class HPA extends JFrame {
    public static int CANVAS_WIDTH = 800;
    public static int CANVAS_HEIGHT = 600;

    private List<SubgridUI> rooms;

    private int x1, y1, x2, y2;

    public HPA() {
        rooms = new ArrayList<>();

        Grid grid = new Grid();
        grid.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        add(grid);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("HPA*");
        pack();
        setVisible(true);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                x1 = e.getX();
                y1 = e.getY();
                System.out.println("yaaa");
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                SubgridUI room = new SubgridUI(x1, y1, e.getX(), e.getY());
                rooms.add(room);
                System.out.println("what");
                repaint();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                SubgridUI room = new SubgridUI(x1, y1, e.getX(), e.getY());
                rooms.add(room);
                System.out.println("yeeeeee");
                repaint();
            }
        });
    }

    public class Grid extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (SubgridUI room : rooms) {
//                room.draw(g);
            }
        }
    }

    // The entry main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            // Run the GUI codes on the Event-Dispatching thread for thread safety
            @Override
            public void run() {
                new HPA(); // Let the constructor do the job
            }
        });
    }
}
