package Graph;

/**
 * Edges of the graph. Has both vertices connected to and the association and
 *  weight between them.
 */
public class Edge implements Comparable<Edge> {

    private final Vertex vertex1, vertex2;
    private final double association;
    private double weight;

    public Edge(Vertex v1, Vertex v2, double association, boolean isWeight) {
        this.vertex1 = v1;
        this.vertex2 = v2;
        this.association = association;
        calculateAndSetWeight(isWeight);
    }

    public Vertex getVertex1() {
        return vertex1;
    }

    public Vertex getVertex2() {
        return vertex2;
    }

    public Vertex oppositeVertex(Vertex vertex) {
        if (vertex1.equals(vertex)) {
            return vertex2;
        }

        return vertex1;
    }

    public boolean containsVertex(Vertex vertex) {
        return vertex1.equals(vertex) || vertex2.equals(vertex);
    }

    /*
        If association isn't already the weight value will calculate
    */
    private void calculateAndSetWeight(boolean isWeight) {
        if(isWeight) {
            this.weight = this.association;
        } else {
            this.weight = -Math.log(this.association);
        }
    }

    public double getWeight() {
        return weight;
    }

    public int getIntWeight() {
        return (int) (weight * 1000);
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getAssociation() {
        return association;
    }

    @Override
    public int compareTo(Edge e) {
        return Double.compare(this.weight, e.weight);
    }

    @Override
    public String toString() {
        return "Edge: " + this.vertex1.getId() + " & " + this.vertex2.getId() + "; " + this.weight;
    }
}
