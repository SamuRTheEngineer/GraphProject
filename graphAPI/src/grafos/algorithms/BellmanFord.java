package grafos.algorithms;

import grafos.algorithms.base.*;
import grafos.model.Edge;
import grafos.model.Graph;

import java.util.*;

public class BellmanFord implements StepAlgorithm<BellmanFordResult> {

    //tambien encuentra caminos mas cortos desde un origen pero teniendo en cuenta pesos negativos

    private Graph graph;
    private int[] dist;
    private int[] parent;

    private List<Edge> edges;
    private int V;

    private int currentIteration;
    private int currentEdgeIndex;

    private boolean checkingNegativeCycle;
    private boolean hasNegativeCycle;

    @Override
    public void initialize(Graph graph, int start) {

        this.graph = graph;
        this.V = graph.getVertices();

        dist = new int[V];
        parent = new int[V];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);

        dist[start] = 0;

        edges = graph.getEdges();

        currentIteration = 0;
        currentEdgeIndex = 0;
        checkingNegativeCycle = false;
        hasNegativeCycle = false;
    }

    @Override
    public boolean hasNextStep() {

        // Fase 1: V - 1 iteraciones
        if (!checkingNegativeCycle) {
            return currentIteration < V - 1; //si no se estan probando ciclos negativos, entonces hay next si la iteracion es menor a v-1
        }

        // Fase 2: chequeo ciclo negativo
        return currentEdgeIndex < edges.size(); //tiene next si el indice es menor a la cantidad de aristas
    }

    @Override
    public AlgorithmStep nextStep() {

        if (!hasNextStep()) {
            throw new NoSuchElementException("No more steps available");
        }

        // Fase de Relajación: Actualizar la distancia conocida más corta a cada nodo
        if (!checkingNegativeCycle) {

            Edge edge = edges.get(currentEdgeIndex);
            currentEdgeIndex++;

            int u = edge.getFrom();
            int v = edge.getTo();
            int weight = edge.getWeight();

            boolean updated = false;

            if (dist[u] != Integer.MAX_VALUE && //si la distancia no es infinito, y la suma de la distancia a ese nodo mas su peso, es menor a la distancia al v, entonces actualiza
                    dist[u] + weight < dist[v]) {

                dist[v] = dist[u] + weight;
                parent[v] = u;
                updated = true;
            }

            // Si terminamos una pasada completa
            if (currentEdgeIndex == edges.size()) {
                currentEdgeIndex = 0;
                currentIteration++;

                // Si ya hicimos V-1 iteraciones, entonces pasamos a fase ciclo para ver si hay ciclos negativos
                if (currentIteration == V - 1) {
                    checkingNegativeCycle = true;
                }
            }

            return new AlgorithmStep(
                    "Iteración " + currentIteration +
                            " relajando arista " + u + " → " + v,
                    updated ? v : null,
                    edge,
                    null,
                    null
            );
        }

        // Fase detección de cicl: si checkingNegativeCycle = true;

        Edge edge = edges.get(currentEdgeIndex);
        currentEdgeIndex++;

        int u = edge.getFrom();
        int v = edge.getTo();
        int weight = edge.getWeight();

        if (dist[u] != Integer.MAX_VALUE &&
                dist[u] + weight < dist[v]) {
            //si despues de hacer todas las pasadas, la distancia sigue "mejorando", es que hay un ciclo negativo.
            hasNegativeCycle = true;
        }

        return new AlgorithmStep(
                "Chequeando ciclo negativo en arista " + u + " → " + v,
                null,
                edge,
                null,
                null
        );
    }

    @Override
    public BellmanFordResult getResult() {
        return new BellmanFordResult(dist, parent, hasNegativeCycle);
    }

    @Override
    public boolean isApplicable(Graph graph) {
        return true;
    } //es el mas versatil, funciona para todo grafo ponderado
}