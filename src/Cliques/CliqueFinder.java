package Cliques;

import Graph.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
 
/**
 *
 * which accepts an n × n association table and finds the largest cliques in the
 * social network, which are the largest groups of people all having direct
 * associations with each other above a specified threshold. A clique in an
 * (unweighted) graph is a set of k vertices all of which have edges to each
 * other. The Bron-Kerbosch algorithm can find all the maximal cliques in an
 * undirected graph. In its simplest form it recursively grows a clique R of
 * vertices into a maximum clique by considering adding one vertex v at a time
 * to R from a set of vertices P to be considered, while also maintaining a set
 * X of vertices to no longer consider. Initially, R = ∅, P holds all the
 * vertices in the graph, and X = ∅.
 */
public class CliqueFinder {

    double[][] matrix;
    String[] names;
    Graph g;
    ArrayList<Set<Vertex>> clicheList;
    Set<Vertex> r;
    Set<Vertex> p;
    Set<Vertex> x;
    

    public CliqueFinder(double[][] matrix, String[] names) {
        this.matrix = matrix;
        this.names = names;
        this.g = new Graph();
        r = new HashSet();
        p = new HashSet();
        x = new HashSet();
        clicheList = new ArrayList();
        g.createWeightedGraph(matrix, names);
    }

    /*
        Helper method to sort the Vertex by Degree.
    */
    public class DegreeSorter implements Comparator<Vertex> {

        @Override
        public int compare(Vertex a, Vertex b) {
            return a.getDegree() - b.getDegree();
        }

    }

    /*
        Helper method to find intersect of neighbours in set.
    */
    public Set<Vertex> Intersect(Vertex node, Set<Vertex> s) {
        Set<Vertex> temp = new HashSet();

        for (Vertex n : s) {
            if (node.getNeighbours().contains(n)) {
                temp.add(n);
            }
        }
        return temp;
    }

    public ArrayList<Set<Vertex>> startBronKerbosch() {
        
        //Degeneracy ordering for p as far as i understand it.
        System.out.println("Using Vertex Ordering.");
        ArrayList<Vertex> pArray = new ArrayList();
        pArray.addAll(g.getVertices());
        System.out.println("Before sorting: " + pArray);
        Collections.sort(pArray, new DegreeSorter());
        System.out.println("After sorting: " + pArray);
        
        p.addAll(pArray);
        bronKerbosch(r, p, x);

        return clicheList;
    }

    public void bronKerbosch(Set<Vertex> r, Set<Vertex> p, Set<Vertex> x) {

        Set<Vertex> vertexList = new HashSet(p);

        for (Vertex node : vertexList) {

            Set<Vertex> intersectP = new HashSet();
            Set<Vertex> intersectX = new HashSet();
            //R Union {v}
            r.add(node);
            p.remove(node);
            //P Intersect neibours(v)
            intersectP.addAll(Intersect(node, p));
            //X Intersect neibours(v)
            intersectX.addAll(Intersect(node, x));

            if (intersectP.isEmpty() && intersectX.isEmpty()) {
                /*
                *nost sure i added this as i was looking at the discussion
                *forum and it was mentioned that there is only 1 clique and that
                *was larger that size of 2. this algorithm does find more cliques
                *all of size 2
                */
                if(r.size() > 2){
                    clicheList.add(new HashSet(r));
                }
            } else {
                //recurssive call for algorithm
                bronKerbosch(r, intersectP, intersectX);
            }
            //adding node to done nodes in x then removing the node from r
            x.add(node);
            r.remove(node);
        }
    }
}
