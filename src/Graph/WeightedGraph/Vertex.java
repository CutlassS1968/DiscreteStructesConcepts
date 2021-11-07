package Graph.WeightedGraph;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Vertex extends Ellipse2D.Double {

  private int r;
  private int label;

  public Vertex(int label) {
    super(0, 0, 0, 0);
    this.label = label;
  }

  public Vertex(int r, int label) {
    super(0, 0, r*2, r*2);
    this.label = label;
  }

  public Vertex(int x, int y, int r, int label) {
    super(x, y, r*2, r*2);
    this.label = label;
  }

  @Override
  public double getWidth() {
    return super.width;
  }

  @Override
  public double getHeight() {
    return super.height;
  }

  public double getR() {
    return r;
  }

  public int getLabel() {
    return label;
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public void setFrame(double x, double y, double w, double h) {

  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public void setR(int r) {
    this.r = r;
    this.height = r*2;
    this.width = r*2;
  }

  @Override
  public Rectangle2D getBounds2D() {
    return new Rectangle2D.Double(x, y, r*2, r*2);
  }
}
