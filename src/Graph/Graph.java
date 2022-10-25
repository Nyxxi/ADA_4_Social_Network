package Graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Weighted undirected graph that holds a list of vertices. Has a method to
 * convert a n x n matrix to a weighted graph.
 */
public class Graph {

    private final int INFINITY = Integer.MAX_VALUE;
    private final ArrayList<Vertex> vertices;
    private final ArrayList<Edge> edges;
    private double[][] weightedNxNGraph;

    public Graph() {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public void addVertex(Vertex vertex) {
        this.getVertices().add(vertex);
    }

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void createWeightedGraph(double[][] matrix, String[] names) {
        Vertex[] newVertices = new Vertex[names.length];
        for (int i = 0; i < names.length; i++) {
            newVertices[i] = new Vertex(names[i]);
            this.vertices.add(newVertices[i]);
        }

        for (int i = 0; i < names.length; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (matrix[i][j] != 0) {
                    Edge edge = new Edge(newVertices[i], newVertices[j], matrix[i][j], false);
                    newVertices[i].addEdge(edge);
                    newVertices[j].addEdge(edge);
                    edges.add(edge);
                }
            }
        }
    }

    /* 
        Creates a weighted path used to find all pairs
    */
    public double[][] createWeightedNxNGraph(double[][] matrix) {
        weightedNxNGraph = new double[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] != 0) {
                    weightedNxNGraph[i][j] = -Math.log(matrix[i][j]);
                } else if (i == j) {
                    weightedNxNGraph[i][j] = 0;
                } else {
                    weightedNxNGraph[i][j] = INFINITY;
                }
            }
        }

        return weightedNxNGraph;
    }

    @Override
    public String toString() {
        String output = "";

        for (Vertex vertex : this.vertices) {
            output += vertex.getId() + ": ";
            output += vertex.getEdges() + ". ";
        }

        return output;
    }

    /*
        Method code from Andrew, slightly edited to match our graph implementation
     */
    public ArrayList<Edge> dijkstrasAlgorithm(Vertex source) {
        final Map<Vertex, Integer> shortestPathEstimates
                = new HashMap<>();
        Map<Vertex, Edge> leastEdges
                = new HashMap<>();
        // create a min priority queue to hold each vertex
        Comparator<Vertex> comparator = new Comparator<Vertex>() {
            @Override
            public int compare(Vertex u, Vertex v) {
                return shortestPathEstimates.get(u)
                        - shortestPathEstimates.get(v);
            }
        };
        PriorityQueue<Vertex> queue = new PriorityQueue<>(this.getVertices().size(), comparator);
        for (Vertex vertex : this.getVertices()) {
            leastEdges.put(vertex, null);
            if (vertex != source) {
                shortestPathEstimates.put(vertex, Integer.MAX_VALUE);
                queue.add(vertex);
            } else {
                shortestPathEstimates.put(source, 0);
            }
        }
        // create set to hold vertices whose final shortest paths known
        Set<Vertex> knownSPVertices = new HashSet<>();
        knownSPVertices.add(source);
        // start building the shortest paths tree
        ArrayList<Edge> spt = new ArrayList<>();
        Vertex addedVertex = source;
        // process each element of the queue
        while (queue.size() > 0) {  // relax edges incident to addedVertex
            for (Edge edge : addedVertex.getEdges()) {
                Vertex v = edge.oppositeVertex(addedVertex);
                int newEstimate = shortestPathEstimates.get(addedVertex)
                        + edge.getIntWeight();
                if (!knownSPVertices.contains(v)
                        && newEstimate < shortestPathEstimates.get(v)) {  // find the adjacent vertex in the queue
                    // note an iterator is used so queue can be modified
                    Iterator<Vertex> iterator = queue.iterator();
                    boolean found = false;
                    while (!found && iterator.hasNext()) {
                        found = (iterator.next() == v);
                    }
                    if (found) // should always be found
                    {  // remove v from queue so gets resorted after change
                        iterator.remove();
                        shortestPathEstimates.put(v, newEstimate);
                        queue.add(v);
                        leastEdges.put(v, edge);
                    }
                }
            }
            // priority queue now has vertex with smallest pe at head
            addedVertex = queue.poll();
            knownSPVertices.add(addedVertex);
            spt.add(leastEdges.get(addedVertex));
        }
        return spt;
    }
}
