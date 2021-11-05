import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import WeightedGraph.*;

/**
 * Displays a Complete Weighted Graph
 */
public class Demo extends JPanel {

  public Graph graph;
  public Demo() {
    int vertices = 5;
    initializeGraph(vertices);
  }

  @Override
  public void paintComponent(Graphics g) {

    // vertex diameter
    final int vDiam = (int)(this.getParent().getSize().getHeight() / 30);

    // Number of vertices
    final int size = graph.getVertices();

    // Radius of graph
    int r = (int)(this.getParent().getSize().getHeight() / 3);

    // Increment Degree (in radians)
    double d = (360.0/size)*(Math.PI/180);

    // Draw Vertices
    g.setColor(Color.DARK_GRAY);
    for (int i = 0; i < size; i++) {
      int x = (int)((this.getSize().getWidth()/2) + (r*Math.cos(d*i)));
      int y = (int)((this.getSize().getHeight()/2) + (r*Math.sin(d*i)));
      g.fillOval(x, y, vDiam, vDiam);
    }

    // Draw Edges
    g.setColor(Color.DARK_GRAY);
    for (int i = 0; i < graph.getVertices(); i++) {
      LinkedList<Edge> list = graph.getAdjacencyList()[i];
      for (Edge edge : list) {
        int x1 = (int)((this.getSize().getWidth()/2) + (r*Math.cos(d*i)) + (vDiam/2));
        int y1 = (int)((this.getSize().getHeight()/2) + (r*Math.sin(d*i)) + (vDiam/2));
        int x2 = (int)((this.getSize().getWidth()/2) + (r*Math.cos(d*edge.getW())) + (vDiam/2));
        int y2 = (int)((this.getSize().getHeight()/2) + (r*Math.sin(d*edge.getW())) + (vDiam/2));
        int mX = ((x1 + x2)/2);
        int mY = ((y1 + y2)/2);
        g.drawLine(x1, y1, x2, y2);
        g.setColor(Color.RED);
        g.drawString(Integer.toString(edge.getWeight()), mX, mY);
        g.setColor(Color.DARK_GRAY);
      }
    }
  }

  public void initializeGraph(int vertices) {
    graph = new Graph(vertices);
    for (int i = 0; i < vertices; i++) {
      for (int j = i + 1; j < vertices; j++) {
        graph.addEdge(i, j, (int)(Math.random() * vertices + 1));
      }
    }
  }

  public static void main(String [] args) {
    JFrame frame = new JFrame();
    Demo demo = new Demo();
    frame.add(demo);
    frame.setBackground(Color.WHITE);
    frame.setSize(750, 750);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}
