package WeightedGraph;

/**
 * Credit: https://algorithms.tutorialhorizon.com/weighted-graph-implementation-java/
 */
public class Edge {
  int v;
  int w;
  int weight;

  public Edge(int v, int w, int weight) {
    this.v = v;
    this.w = w;
    this.weight = weight;
  }

  public int getV() {
    return v;
  }

  public int getW() {
    return w;
  }

  public int getWeight() {
    return weight;
  }
}
