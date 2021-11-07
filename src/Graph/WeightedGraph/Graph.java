package Graph.WeightedGraph;

import java.util.LinkedList;

/**
 * Credit: https://algorithms.tutorialhorizon.com/weighted-graph-implementation-java/
 */
public class Graph {

  private Vertex[] vertices;
  private LinkedList<Edge>[] adjacencylist;

  public Graph(int size) {
    this.vertices = new Vertex[size];
    adjacencylist = new LinkedList[size];
    //initialize adjacency lists and vertices
    for (int i = 0; i < size; i++) {
      adjacencylist[i] = new LinkedList<>();
      vertices[i] = new Vertex(i);
    }
  }

  public void addEdge(Vertex source, Vertex destination, int weight) {
    Edge edge = new Edge(source, destination, weight);
    adjacencylist[source.getLabel()].add(edge);
  }

  public void printGraph() {
    for (int i = 0; i < vertices.length; i++) {
      LinkedList<Edge> list = adjacencylist[i];
      for (Edge edge : list) {
        System.out.println("vertex-" + i + " is connected to " +
            edge.getEnd() + " with weight " + edge.getWeight());
      }
    }
  }

  public Vertex[] getVertices() {
    return this.vertices;
  }

  public LinkedList<Edge>[] getAdjacencyList() {
    return adjacencylist;
  }
}
