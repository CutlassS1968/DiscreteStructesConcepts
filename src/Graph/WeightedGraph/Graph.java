package Graph.WeightedGraph;

import java.util.LinkedList;

/**
 * Credit: https://algorithms.tutorialhorizon.com/weighted-graph-implementation-java/
 */
public class Graph {
  public Vertex[] vertices;
  public LinkedList<Edge>[] adjacencylist;

  public Graph(int size) {
    this.vertices = new Vertex[size];
    adjacencylist = new LinkedList[size];
    //initialize adjacency lists and vertices
    for (int i = 0; i < size; i++) {
      adjacencylist[i] = new LinkedList<>();
      vertices[i] = new Vertex();
    }
  }

  public void addEdge(int source, int destination, int weight) {
    Edge edge = new Edge(source, destination, weight);
    adjacencylist[source].add(edge); //for directed graph
  }

  public void printGraph() {
    for (int i = 0; i < vertices.length; i++) {
      LinkedList<Edge> list = adjacencylist[i];
      for (Edge edge : list) {
        System.out.println("vertex-" + i + " is connected to " +
            edge.w + " with weight " + edge.weight);
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
