package AssociationTable;

/**
 *
 * Sets up n x n association tables for two different test cases and returns the
 *  table.
 */
public class TestCases {
    
    private static String[] namesA = {"Anna", "Bill", "Carl", "Dave", "Emma", "Fred"};
    private static String[] namesB = {"Anna", "Bill", "Carl", "Dave", "Emma", "Fred", "Greg", 
                                        "Hari", "Iggy", "Jake"};
    
    private static double[][] matrixA;
    private static double[][] matrixB;
    
    private static int nA = namesA.length;
    private static int nB = namesB.length;

    public static double[][] getTestCaseOneMatrix() {
        matrixA = new double[nA][nA];
        
        for (int i = 0; i < nA; i++) {
            for (int j = 0; j < nA; j++) {
                matrixA[i][j] = 0;
            }
        }

        addEdge(0, 1, 0.5, matrixA);
        addEdge(1, 3, 0.4, matrixA);
        addEdge(2, 3, 0.3, matrixA);
        addEdge(0, 2, 0.4, matrixA);
        addEdge(2, 4, 0.5, matrixA);
        addEdge(3, 4, 0.8, matrixA);
        addEdge(4, 5, 0.7, matrixA);

        return matrixA;
    }
    
    public static String[] getTestCaseOneNames() {
        return namesA;
    }

    public static double[][] getTestCaseTwoMatrix() {
        matrixB = new double[nB][nB];
        
        for (int i = 0; i < nB; i++) {
            for (int j = 0; j < nB; j++) {
                matrixB[i][j] = 0;
            }
        }

        addEdge(0, 1, 0.3, matrixB);
        addEdge(0, 5, 0.2, matrixB);
        addEdge(0, 6, 0.3, matrixB);
        addEdge(1, 6, 0.4, matrixB);
        addEdge(1, 5, 0.5, matrixB);
        addEdge(2, 7, 0.8, matrixB);
        addEdge(2, 4, 0.1, matrixB);
        addEdge(2, 3, 0.2, matrixB);
        addEdge(3, 1, 0.7, matrixB);
        addEdge(3, 8, 0.6, matrixB);
        addEdge(3, 9, 0.3, matrixB);
        addEdge(4, 7, 0.5, matrixB);
        addEdge(4, 8, 0.1, matrixB);
        addEdge(5, 6, 0.2, matrixB);
        addEdge(6, 7, 0.4, matrixB);
        addEdge(9, 0, 0.2, matrixB);

        return matrixB;
    }
    
    public static String[] getTestCaseTwoNames() {
        return namesB;
    }

    private static void addEdge(int person1, int person2, double weight, double[][] matrix) {
        matrix[person1][person2] = weight;
        matrix[person2][person1] = weight;
    }

    public static void printMatrix(double[][] matrix, int n, String[] names) {
        System.out.println("");
        System.out.print("      ");
        for (int i = 0; i < n; i++) {
            System.out.print(names[i] + "   ");
        }
        System.out.println("");

        for (int i = 0; i < n; i++) {
            System.out.print(names[i] + "  ");
            for (int j = 0; j < n; j++) {
                System.out.print(matrix[i][j] + "    ");
            }
            System.out.println("");
        }
    }
}
