package Community;

import Graph.AllPairsFloydWarshall;
import Graph.Edge;
import Graph.Graph;
import Graph.Vertex;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * This class accepts an n x n matrix which is used to create a weighted n x n
 * matrix. All shortest pairs are then found and entered into a priority queue.
 * Each is pulled off based off shortest weight and if from different sets are
 * unionized. This creates community clusters from most to least strongest.
 */
public class CommunityClusterFinder {

    private final double[][] matrix;
    private double[][] weightedNxNGraph;
    private final String[] names;
    private Graph graph;
    private DisjointSets disjointedSets;

    public CommunityClusterFinder(double[][] matrix, String[] names) {
        this.matrix = matrix;
        this.names = names;
        setUpGraph();
        setUpDisjointedSets();
    }

    private void setUpGraph() {
        graph = new Graph();
        graph.createWeightedGraph(this.matrix, this.names);
        weightedNxNGraph = graph.createWeightedNxNGraph(matrix);
    }

    private void setUpDisjointedSets() {
        disjointedSets = new DisjointSets();
        for (Vertex vertex : graph.getVertices()) {
            disjointedSets.makeSet(vertex);
        }
    }

    public void findCluster() {
        firstCase();

        AllPairsFloydWarshall allPairs = new AllPairsFloydWarshall(weightedNxNGraph);
        PriorityQueue<Edge> queue = makePriorityQueue(allPairs);

        for (int i = 2; i < matrix.length; i++) {
            otherCases(queue);
            System.out.println(disjointedSets);
        }
    }

    /* 
        Find first case by simply finding the lowest weighted edge 
     */
    private void firstCase() {
        Edge minEdge = graph.getEdges().get(0);
        for (Edge edge : graph.getEdges()) {
            if (minEdge.getWeight() > edge.getWeight()) {
                minEdge = edge;
            }
        }

        disjointedSets.union(minEdge.getVertex1(), minEdge.getVertex2());
        System.out.println("First Community is between: " + minEdge.getVertex1()
                + " & " + minEdge.getVertex2() + ". Weight: " + minEdge.getWeight());
        System.out.println(disjointedSets);
    }

    /* 
        Pulls from front of priority queue until the vertices of the edge are 
         not in the same set and then unionizes them 
     */
    private void otherCases(PriorityQueue<Edge> queue) {
        boolean nextShortestFound = false;

        while (!nextShortestFound) {
            Edge edge = queue.poll();
            Vertex v1 = edge.getVertex1();
            Vertex v2 = edge.getVertex2();
            //If both vertices are from the same set
            if (!disjointedSets.findSet(v1).equals(disjointedSets.findSet(v2))) {
                disjointedSets.union(v1, v2);
                nextShortestFound = true;
                System.out.println("Next Community is between: " + v1
                        + " & " + v2 + ". \tWeight: " + edge.getWeight());
            }
        }
    }

    /*
        Makes a priority queue of every pair of vertices orders by the total weight
         of the shortest path between vertices
     */
    private PriorityQueue<Edge> makePriorityQueue(AllPairsFloydWarshall allPairs) {
        int n = matrix.length;
        ArrayList<Vertex> vertices = graph.getVertices();
        double[][][] weightedMatrix = allPairs.getD();

        PriorityQueue<Edge> queue = new PriorityQueue<>(n * n, new WeightSorter());
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                queue.add(new Edge(vertices.get(i), vertices.get(j),
                        weightedMatrix[n][i][j], true));
            }
        }

        return queue;
    }

    /*
        Helper method to sort the Edge by weight.
     */
    public class WeightSorter implements Comparator<Edge> {

        @Override
        public int compare(Edge a, Edge b) {
            return a.getIntWeight() - b.getIntWeight();
        }

    }
}
