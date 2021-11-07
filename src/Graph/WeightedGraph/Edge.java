package Graph.WeightedGraph;

/**
 * Credit: https://algorithms.tutorialhorizon.com/weighted-graph-implementation-java/
 */
public class Edge {

  private Vertex start;
  private Vertex end;
  private int weight;

  public Edge(Vertex start, Vertex end, int weight) {
    this.start = start;
    this.end = end;
    this.weight = weight;
  }

  public Vertex getStart() {
    return start;
  }

  public Vertex getEnd() {
    return end;
  }

  public int getWeight() {
    return weight;
  }
}
