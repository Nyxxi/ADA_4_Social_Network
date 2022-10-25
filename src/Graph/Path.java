package Graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Holds all information while creating a path through the graph. The starting
 *  and ending vertex, the path current being traversed and the final path 
 *  and weight.
 */
public class Path {

    private final Set<Vertex> visited;
    private final ArrayList<Edge> tempPath, finalPath;
    private final ArrayList<Vertex> vertexPath, finalVertexPath;
    private double weight;
    private final Vertex p1, p2;

    public Path(Vertex p1, Vertex p2) {
        this.p1 = p1;
        this.p2 = p2;
        tempPath = new ArrayList<>();
        finalPath = new ArrayList<>();
        vertexPath = new ArrayList<>();
        finalVertexPath = new ArrayList<>();
        visited = new HashSet<>();
        visited.add(p1);
    }

    public void addEdge(Edge edge) {
        tempPath.add(edge);
    }

    public void removeEdge(Edge edge) {
        tempPath.remove(edge);
    }
    
    public void addVertexToPath(Vertex vertex) {
        vertexPath.add(vertex);
    }
    
    public void removeVertexFromPath(Vertex vertex) {
        vertexPath.remove(vertex);
    }

    public boolean containsVertex(Vertex vertex) {
        return visited.contains(vertex);
    }

    public void addVisitedVertex(Vertex vertex) {
        visited.add(vertex);
    }

    public double pathWeight() {
        return getWeight();
    }

    public void setFinalPath(ArrayList<Edge> finalPath) {
        for (Edge edge : finalPath) {
            this.finalPath.add(edge);
        }
        this.weight = calculateWeight(finalPath);
    }
    
    public void setVertextPath(ArrayList<Vertex> vertexPath) {
        for (Vertex vertex : vertexPath) {
            this.finalVertexPath.add(vertex);
        }
        this.finalVertexPath.add(p2);
    }

    private double calculateWeight(ArrayList<Edge> path) {
        double weight = 0;
        for (Edge edge : path) {
            weight += edge.getWeight();
        }
        return weight;
    }

    public Vertex getP1() {
        return p1;
    }
    
    public Vertex getP2() {
        return p2;
    }
    
    public ArrayList<Edge> getPath() {
        return tempPath;
    }
    
    public ArrayList<Edge> getFinalPath() {
        return this.finalPath;
    }

    public double getWeight() {
        return weight;
    }
    
    public ArrayList<Vertex> getVertexPath() {
        return vertexPath;
    }

    public ArrayList<Vertex> getFinalVertexPath() {
        return finalVertexPath;
    }
}
