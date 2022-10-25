package Graph;

import java.util.ArrayList;

/**
 * Vertex for the graph. Has a id (name) and a list of the edges coming off of it.
 */
public class Vertex {

    private final String id;
    private ArrayList<Edge> edges;
    private ArrayList<Vertex> neighbours;
    private int degree;

    public Vertex(String id) {
        this.id = id;
        edges = new ArrayList<>();
        this.neighbours = new ArrayList();
        this.degree = 0;
    }

    private int nodeDegree(ArrayList<Edge> edges) {
        for (Edge e : edges) {
            degree++;
        }
        return degree;
    }
    
    public int getDegree(){
        return degree;
    }

    public String getId() {
        return id;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
        if (edge.getVertex1() != this) {
            neighbours.add(edge.getVertex1());
        } else if (edge.getVertex2() != this) {
            neighbours.add(edge.getVertex2());
        }
        nodeDegree(edges);
    }

    public ArrayList<Vertex> getNeighbours() {
        return neighbours;
    }

    @Override
    public String toString() {
        return this.id;
    }
}
