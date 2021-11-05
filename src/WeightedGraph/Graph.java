package WeightedGraph;

import java.util.LinkedList;

/**
 * Credit: https://algorithms.tutorialhorizon.com/weighted-graph-implementation-java/
 */
public class Graph {
  public int vertices;
  public LinkedList<Edge>[] adjacencylist;

  public Graph(int vertices) {
    this.vertices = vertices;
    adjacencylist = new LinkedList[vertices];
    //initialize adjacency lists for all the vertices
    for (int i = 0; i < vertices; i++) {
      adjacencylist[i] = new LinkedList<>();
    }
  }

  public void addEdge(int source, int destination, int weight) {
    Edge edge = new Edge(source, destination, weight);
    adjacencylist[source].add(edge); //for directed graph
  }

  public void printGraph() {
    for (int i = 0; i < vertices; i++) {
      LinkedList<Edge> list = adjacencylist[i];
      for (Edge edge : list) {
        System.out.println("vertex-" + i + " is connected to " +
            edge.w + " with weight " + edge.weight);
      }
    }
  }

  public int getVertices() {
    return this.vertices;
  }

  public LinkedList<Edge>[] getAdjacencyList() {
    return adjacencylist;
  }
}
