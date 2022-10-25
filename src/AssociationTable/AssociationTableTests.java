package AssociationTable;

import BestAssociation.BestAssociation;
import Cliques.CliqueFinder;
import Community.CommunityClusterFinder;
import Graph.Path;
import Graph.Vertex;
import java.util.ArrayList;
import java.util.Set;

/**
 * Runs tests on each of the three main classes in the project - Best
 * Association, Clique Finder, Community Cluster
 */
public class AssociationTableTests {

    public static void main(String[] args) {
        double[][] matrix = TestCases.getTestCaseOneMatrix();
        String[] names = TestCases.getTestCaseOneNames();

        double[][] secondMatrix = TestCases.getTestCaseTwoMatrix();
        String[] secondNames = TestCases.getTestCaseTwoNames();

        bestAssociationTests(matrix, names);

        System.out.println("\n\n");

        clicheFinderTests(matrix, names, secondMatrix, secondNames);

        System.out.println("\n\n");

        communityClusterFinderTests(matrix, names, secondMatrix, secondNames);
    }

    public static void bestAssociationTests(double[][] matrix, String[] names) {
        System.out.println("----Best Association----");
        System.out.println("--Test One--");
        BestAssociation best = new BestAssociation(matrix, names);
        Path bestPath1 = best.getBestAssociation(2, 5);
        System.out.println("Shortest path between " + bestPath1.getP1().getId()
                + " and " + bestPath1.getP2().getId() + " takes path " + bestPath1.getFinalVertexPath());
        System.out.println("The edges taken are " + bestPath1.getFinalPath()
                + " and has the weight " + bestPath1.getWeight());

        System.out.println();
        System.out.println("--Test Two--");
        Path bestPath2 = best.getBestAssociation(1, 4);
        System.out.println("Shortest path between " + bestPath2.getP1().getId()
                + " and " + bestPath2.getP2().getId() + " takes path " + bestPath2.getFinalVertexPath());
        System.out.println("The edges taken are " + bestPath2.getFinalPath()
                + " and has the weight " + bestPath2.getWeight());

        System.out.println();
        System.out.println("--Test Three--");
        Path bestPath3 = best.getBestAssociation(5, 0);
        System.out.println("Shortest path between " + bestPath3.getP1().getId()
                + " and " + bestPath3.getP2().getId() + " takes path " + bestPath3.getFinalVertexPath());
        System.out.println("The edges taken are " + bestPath3.getFinalPath()
                + " and has the weight " + bestPath3.getWeight());
    }

    public static void clicheFinderTests(double[][] matrix, String[] names, 
            double[][] secondMatrix, String[] secondNames) {
        System.out.println("----Clique Finder----");
        System.out.println("--Test One--");
        CliqueFinder clicheFinder = new CliqueFinder(matrix, names);
        TestCases.printMatrix(matrix, names.length, names);
        System.out.println();
        ArrayList<Set<Vertex>> clique = clicheFinder.startBronKerbosch();
        System.out.println();
        System.out.println(clique);
        System.out.println("\n");

        System.out.println("--Test Two--");
        CliqueFinder clicheFinder2 = new CliqueFinder(secondMatrix, secondNames);
        TestCases.printMatrix(secondMatrix, secondNames.length, secondNames);
        System.out.println();
        ArrayList<Set<Vertex>> clique2 = clicheFinder2.startBronKerbosch();
        System.out.println();
        System.out.println(clique2);
    }

    public static void communityClusterFinderTests(double[][] matrix, String[] names,
            double[][] secondMatrix, String[] secondNames) {
        System.out.println("----Community Cluster Finder----");
        System.out.println("--Test One--");
        CommunityClusterFinder communityCluster = new CommunityClusterFinder(matrix, names);
        communityCluster.findCluster();

        System.out.println("--Test Two--");
        CommunityClusterFinder communityCluster2 = new CommunityClusterFinder(secondMatrix, secondNames);
        communityCluster2.findCluster();
    }
}
