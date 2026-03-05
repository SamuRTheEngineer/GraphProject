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

    private boolean positiveCycleDetected = false;
    private boolean finished = false;
    private boolean changedInThisIteration = false;

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
        return currentIteration < V && !finished;
    }

    @Override
    public AlgorithmStep nextStep() {

        if (!hasNextStep()) {
            throw new NoSuchElementException("No more steps available");
        }

        Edge edge = edges.get(currentEdgeIndex);

        int u = edge.getFrom();
        int v = edge.getTo();

        boolean updated = false;

        if (gain[u] != Integer.MIN_VALUE) {
            /*
           En Bellman-Ford, gain[u] ya incluye las víctimas de todos los nodos
           en el mejor camino encontrado hasta U.
           Al movernos de U a V, solo sumamos las víctimas de V.

           Como Bellman-Ford garantiza que la mejor solución encontrada tiene a lo sumo V-1 aristas, evitando ciclos en el resultado final cuando no hay ciclos positivos.
        */
            int possibleGain = gain[u] + graph.getNode(v).getVictims();

            if (possibleGain > gain[v]) {

                if (currentIteration == V-1) { //si estamos en la V y hay mejora, ent ciclo positivo.
                    positiveCycleDetected = true;
                    finished = true;
                } else {
                    gain[v] = possibleGain;
                    parent[v] = u;
                    updated = true;
                    changedInThisIteration = true;
                }
            }
        }

        currentEdgeIndex++;

        //si se termina una pasada completa por las aristas
        if (currentEdgeIndex == edges.size()) {
            currentEdgeIndex = 0;
            currentIteration++;

            if (!changedInThisIteration) {//si en toda la vuelta no hubo un solo cambio, el algoritmo converge
                finished = true;
            }

            if (currentIteration >= V) { //si llegamos a iteracion V, para
                finished = true;
            }

            changedInThisIteration = false; //para la siguiente vuelta

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
        return new BellmanFordAdaptedResult(gain, parent, positiveCycleDetected);
    }

    @Override
    public boolean isApplicable(Graph graph) {
        return true;
    }

}
