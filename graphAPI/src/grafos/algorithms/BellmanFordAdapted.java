package grafos.algorithms;

import grafos.algorithms.base.StepAlgorithm;
import grafos.algorithms.base.*;
import grafos.model.Edge;
import grafos.model.Graph;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class BellmanFordAdapted implements StepAlgorithm<BellmanFordAdaptedResult> {

    private Graph graph;

    private int[] gain; //victimas acumuladas maximas
    private int[] parent;

    private List<Edge> edges;
    private int V;

    private int currentIteration;
    private int currentEdgeIndex;

    @Override
    public void initialize(Graph graph, int start) {

        this.graph = graph;
        this.V = graph.getVertices();

        gain = new int[V];
        parent = new int[V];


        Arrays.fill(gain, Integer.MIN_VALUE); //porque puede haber un nodo con 0.
        Arrays.fill(parent, -1);

        //El nodo inicial suma sus propias victimas
        gain[start] = graph.getNode(start).getVictims();

        edges = graph.getEdges();

        currentIteration = 0;
        currentEdgeIndex = 0;

    }

    @Override
    public boolean hasNextStep() {
        return currentIteration < V-1;
    }

    @Override
    public AlgorithmStep nextStep() {

        if (!hasNextStep()) {
            throw new NoSuchElementException("No more steps available");
        }

        Edge edge = edges.get(currentEdgeIndex);
        currentEdgeIndex++;

        int u = edge.getFrom();
        int v = edge.getTo();

        boolean updated = false;

        if (gain[u] != Integer.MIN_VALUE) {
            int possibleGain = gain[u] + graph.getNode(v).getVictims();

            if (possibleGain > gain[v]) {
                gain[v] = possibleGain;
                parent[v] = u;
                updated = true;
            }
        }

        //si se termina una pasada completa por las aristas
        if (currentEdgeIndex == edges.size()) {
            currentEdgeIndex = 0;
            currentIteration++;
        }

        return new AlgorithmStep(
                "Iteración " + currentIteration +
                        " evaluando arista " + u + " → " + v,
                updated ? v : null, //coloea nodo si mejora
                edge,
                null,
                null
                );
    }

    @Override
    public BellmanFordAdaptedResult getResult() {
        return new BellmanFordAdaptedResult(gain, parent);
    }

    @Override
    public boolean isApplicable(Graph graph) {
        return true;
    }

}
