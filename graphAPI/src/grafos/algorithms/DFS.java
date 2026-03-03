package grafos.algorithms;

import grafos.algorithms.base.*;
import grafos.model.Edge;
import grafos.model.Graph;

import java.util.*;

public class DFS implements StepAlgorithm<DFSResult> {

    //Recorre el grafo yendo hasta lo más profundo posible

    private Graph graph;
    private Deque<Integer> stack;
    private boolean[] visited;
    private int[] parent;

    @Override
    public void initialize(Graph graph, int start) {
        this.graph = graph;
        this.stack = new ArrayDeque<>();
        this.visited = new boolean[graph.getVertices()];
        this.parent = new int[graph.getVertices()];

        Arrays.fill(parent, -1);

        // visitamos el nodo y lo metemos a la pila
        visited[start] = true;
        stack.push(start);
    }

    @Override
    public boolean hasNextStep() {
        return !stack.isEmpty();
    }

    @Override
    public AlgorithmStep nextStep() {

        if (!hasNextStep()) {
            throw new NoSuchElementException("No more steps available");
        }

        //si hay siguiente paso, se saca de la pila

        int current = stack.pop();

        // se exploran las aristas incidentes al nodo current
        for (Edge edge : graph.getNeighbors(current)) {
            int neighbor = edge.getTo();

            //si no se han visitado los nodos a los que llevan esas aristas, se procesan.

            if (!visited[neighbor]) {
                visited[neighbor] = true;   // se marca como visitado
                parent[neighbor] = current;
                stack.push(neighbor);
            }
        }

        // Construimos conjunto de visitados para visualización
        Set<Integer> visitedSet = new HashSet<>();
        for (int i = 0; i < visited.length; i++) {
            if (visited[i]) visitedSet.add(i);
        }

        return new AlgorithmStep(
                "Procesando nodo " + current,
                current,
                null,
                visitedSet,
                null
        );
    }

    @Override
    public DFSResult getResult() {
        return new DFSResult(parent);
    }

    @Override
    public boolean isApplicable(Graph graph) {
        return true;
    } //sirve para todos pero ignoran los pesos
}