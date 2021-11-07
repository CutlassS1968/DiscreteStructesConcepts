import Graph.WeightedGraph.Edge;
import Graph.WeightedGraph.Graph;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import javax.swing.*;

public class DrawGraph {

  public static final int WIDTH = 600;
  public static final int HEIGHT = 600;
  public static final int GRAPH_SIZE = 10;
  public static Point mPoint;
  private static JFrame frame;
  private static Canvas canvas;
  public static BufferStrategy b;
  private static GraphicsEnvironment ge;
  private static GraphicsDevice gd;
  private static GraphicsConfiguration gc;
  private static BufferedImage buffer;
  private static Graphics graphics;
  private static Graphics2D g2D;
  private static AffineTransform at;
  private int gR;
  private int vR;
  public Graph graph;
  private MovingAdapter ma;

  public static boolean isRunning = false;

  /*
   * DrawGraph draws the graph on a loop, so create a graph as a
   * thread and calculate the coords of vertices there, they will be
   * updated here in the draw loop
   */
  public static void main(String [] args) {
    DrawGraph main = new DrawGraph();
  }

  public DrawGraph() {
    vR = HEIGHT/30;
    gR = HEIGHT/3;
    isRunning = true;
    ma = new MovingAdapter();
    initializeCompleteGraph(GRAPH_SIZE);
    initializeFrame();
    runWindow();
  }

  /**
   * Creates a Complete Graph of size n, and sets initial starting coordinates
   * @param n Size of graph
   */
  public void initializeCompleteGraph(int n) {
    graph = new Graph(n);
    double d = (360.0/n)*(Math.PI/180);

    for (int i = 0; i < n; i++) {
      int x = (int)((WIDTH/2) + (gR*Math.cos(d*i)));
      int y = (int)((HEIGHT/2) + (gR*Math.sin(d*i)));
      graph.getVertices()[i].setX(x);
      graph.getVertices()[i].setY(y);
      graph.getVertices()[i].setR(vR);
      for (int j = i + 1; j < n; j++) {
        graph.addEdge(graph.getVertices()[i], graph.getVertices()[j], (int)(Math.random() * n + 1));
      }
    }
  }

  /**
   * Set up the Frame, Canvas, and Graphics tools
   */
  public void initializeFrame() {
    frame = new JFrame("Demo");
    frame.setIgnoreRepaint(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);

    // Create canvas (used for painting to frame)
    canvas = new Canvas();
    canvas.setIgnoreRepaint(true);
    canvas.setSize(WIDTH, HEIGHT);
    canvas.setBackground(Color.WHITE);

    // Add to Display
    Container content = new Container();
    content.add(canvas);
    content.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    frame.setContentPane(content);
    frame.pack();

    // Add mouse listener
    canvas.addMouseMotionListener(ma);
    canvas.addMouseListener(ma);

    // Center to Display
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);

    // Set up the BufferStrategy for double buffering
    canvas.createBufferStrategy(2);
    b = canvas.getBufferStrategy();

    // Get graphics configuration
    ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    gd = ge.getDefaultScreenDevice();
    gc = gd.getDefaultConfiguration();

    // Create off-screen drawing surface
    buffer = gc.createCompatibleImage(WIDTH, HEIGHT);

    // Objects needed for rendering
    graphics = null;
    g2D = null;
  }

  /**
   * Main loop of the program; Constantly draws graph to canvas
   */
  public void runWindow() {

    // Vars for Debug
    int fps = 0;
    int frames = 0;
    long totalTime = 0;
    long curTime = System.currentTimeMillis();
    long lastTime = curTime;

    // Main Loop
    while (isRunning) {
      try {
        // Calculate frames
        lastTime = curTime;
        curTime = System.currentTimeMillis();
        totalTime += curTime - lastTime;
        if (totalTime > 1000) {
          totalTime -= 1000;
          fps = frames;
          frames = 0;
        }
        ++frames;

        // Get mouse location
        if (frame.getMousePosition() != null) mPoint = frame.getMousePosition();

        canvas.setSize(WIDTH, HEIGHT);
        buffer = gc.createCompatibleImage(WIDTH, HEIGHT);

        // clear back buffer
        g2D = buffer.createGraphics();
        g2D.setColor(Color.WHITE);
        g2D.fillRect(0, 0, WIDTH, HEIGHT);

        // Draw Vertices
        g2D.setColor(Color.DARK_GRAY);
        for (int i = 0; i < graph.getVertices().length; i++) {
          at = new AffineTransform();
          g2D.fill(graph.getVertices()[i]);
        }

        // Draw Edges
        g2D.setColor(Color.DARK_GRAY);
        for (int i = 0; i < graph.getVertices().length; i++) {
          LinkedList<Edge> list = graph.getAdjacencyList()[i];
          for (Edge edge : list) {
            at = new AffineTransform();
            int x1 = (int)(edge.getStart().getCenterX());
            int y1 = (int)(edge.getStart().getCenterY());
            int x2 = (int)(edge.getEnd().getCenterX());
            int y2 = (int)(edge.getEnd().getCenterY());
            int mX = ((x1 + x2)/2);
            int mY = ((y1 + y2)/2);
            g2D.drawLine(x1, y1, x2, y2);
            g2D.setColor(Color.RED);
            g2D.drawString(Integer.toString(edge.getWeight()), mX, mY);
            g2D.setColor(Color.DARK_GRAY);
          }
        }

        // display debug stats in frame
        g2D.setFont(new Font("Courier New", Font.PLAIN, 12));
        g2D.setColor(Color.GREEN);
        g2D.drawString(String.format("FPS: %s", fps), 20, 20);
        g2D.drawString(String.format("X: %s", mPoint.x), 20, 30);
        g2D.drawString(String.format("Y: %s", mPoint.y), 20, 40);

        graphics = b.getDrawGraphics();
        graphics.drawImage(buffer, 0, 0, null);
        if (!b.contentsLost()) b.show();

        // Let the OS have a little time
        Thread.sleep(15);
      } catch (InterruptedException ie) {
      } catch (NullPointerException ne) {

      } finally {
        // release resources
        if (graphics != null) graphics.dispose();
        if (g2D != null) g2D.dispose();
      }
    }
  }

  class MovingAdapter extends MouseAdapter {

    private boolean pressed = false;
    private Point point;

    @Override
    public void mousePressed(MouseEvent e) {
//      System.out.println("Mouse Pressed!");
      if (e.getButton() != MouseEvent.BUTTON1) {
        return;
      }

      for (int i = 0; i < graph.getVertices().length; i++) {
        if (graph.getVertices()[i] != null && graph.getVertices()[i].getBounds2D().contains(e.getPoint())) {
          pressed = true;
          this.point = e.getPoint();
//          System.out.println(pressed);
        }
      }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
      if (pressed) {
        int deltaX = e.getX() - point.x;
        int deltaY = e.getY() - point.y;
        for (int i = 0; i < graph.getVertices().length; i++) {
          if (graph.getVertices()[i].getBounds().contains(point.x, point.y)) {
            graph.getVertices()[i].setX((int) graph.getVertices()[i].getX() + deltaX);
            graph.getVertices()[i].setY((int) graph.getVertices()[i].getY() + deltaY);
            point = e.getPoint();
          }
        }
      }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      pressed = false;
    }
  }
}