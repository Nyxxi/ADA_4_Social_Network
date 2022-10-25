package BestAssociation;

import Graph.Edge;
import Graph.Graph;
import Graph.Path;
import Graph.Vertex;
import java.util.ArrayList;

/**
 * This class takes in a n x n matrix of direct associations and creates a graph.
 *  Then when given two people will calculate the best association between them
 *  returning the path and weight that bridge that connection.
 */
public class BestAssociation {

    private final double[][] matrix;
    private final String[] names;
    private Graph graph;

    public BestAssociation(double[][] matrix, String[] names) {
        this.matrix = matrix;
        this.names = names;
        setUpGraph();
    }
    
    private void setUpGraph() {
        graph = new Graph();
        graph.createWeightedGraph(this.matrix, this.names);
    }

    public Path getBestAssociation(int person1, int person2) {
        Vertex p1 = graph.getVertices().get(person1);
        Vertex p2 = graph.getVertices().get(person2);
        double weight = 0;

        ArrayList<Edge> shortEdges = graph.dijkstrasAlgorithm(p1);
        
        return findShortestPath(p1, p2, shortEdges, weight);
    }

    public Path findShortestPath(Vertex p1, Vertex p2,
            ArrayList<Edge> shortEdges, double weight) {
        Path path = new Path(p1, p2);
        Vertex current = p1;

        findShortestPathRecursion(current, p2, shortEdges, path);

        return path;
    }

    /* 
        Recursion for going down the paths until the end vertex is found. 
         When end is found a copy of the current path is taken and saved as the 
         shortest path.
    */
    private void findShortestPathRecursion(Vertex current, Vertex end,
            ArrayList<Edge> edges, Path path) {
        path.addVertexToPath(current);
        if (!current.equals(end)) {
            ArrayList<Edge> directEdges = directEdges(current, edges);
            for (Edge edge : directEdges) {
                path.addEdge(edge);
                Vertex newVertex = edge.oppositeVertex(current);
                if(!path.containsVertex(newVertex)) {
                    path.addVisitedVertex(newVertex);
                    findShortestPathRecursion(newVertex, end, edges, path);
                } 
                
                //Copies the current path as the final path
                if(newVertex.equals(end)) {
                    path.setFinalPath(path.getPath());
                    path.setVertextPath(path.getVertexPath());
                }
                
                path.removeEdge(edge);
            }
        } 
        path.removeVertexFromPath(current);
    }

    /*
        Finds edges that are from the current vertex
    */
    private ArrayList<Edge> directEdges(Vertex current, ArrayList<Edge> edges) {
        ArrayList<Edge> directEdges = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.containsVertex(current)) {
                directEdges.add(edge);
            }
        }

        return directEdges;
    }

}
