package grafos.algorithms;

import grafos.algorithms.base.*;
import grafos.model.Edge;
import grafos.model.Graph;
import grafos.structures.DisjointSet;

import java.util.*;

public class Kruskal implements StepAlgorithm<KruskalResult> {

    //encuentra el árbol de mínima expansión
    //conecta todos los nodos con el minimo peso total, sin ciclos.
    private Graph graph;
    private List<Edge> sortedEdges;
    private DisjointSet disjointSet;
    private List<Edge> mst;
    private int currentIndex;
    private boolean finished;

    @Override
    public void initialize(Graph graph, int start) {
        this.graph = graph;
        this.sortedEdges = new ArrayList<>(graph.getEdges());
        Collections.sort(sortedEdges); //ordena la lista de vertices
        this.disjointSet = new DisjointSet(graph.getVertices());
        this.mst = new ArrayList<>();
        this.currentIndex = 0;
        this.finished = false;
    }

    @Override
    public boolean hasNextStep() {
        return currentIndex < sortedEdges.size() //si el indice del nodo se pasa se la cantidad de nodos, no hay otro paso
                && mst.size() < graph.getVertices() - 1
                && !finished;
    }

    @Override
    public AlgorithmStep nextStep() {

        if (!hasNextStep()) {
            finished = true;
            return null;
        }

        Edge edge = sortedEdges.get(currentIndex++);
        boolean added = disjointSet.union(edge.getFrom(), edge.getTo());

        String description;

        if (added) {
            mst.add(edge);
            description = "Arista agregada al MST: " + edge.getFrom() + " - " + edge.getTo();
        } else {
            description = "Arista descartada (forma ciclo): " + edge.getFrom() + " - " + edge.getTo();
        }

        return new AlgorithmStep(
                description,
                null,
                edge,
                null,
                new HashSet<>(mst)
        );
    }

    @Override
    public KruskalResult getResult() {
        finished = true;
        return new KruskalResult(mst);
    }

    @Override
    public boolean isApplicable(Graph graph) {
        return !graph.isDirected();
    }
}