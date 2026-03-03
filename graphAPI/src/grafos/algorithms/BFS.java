package grafos.algorithms;

import grafos.algorithms.base.*;
import grafos.model.Edge;
import grafos.model.Graph;

import java.util.*;

public class BFS implements StepAlgorithm<BFSResult> {

    //Recorre el grafo por capas

    private Graph graph;
    private Queue<Integer> queue;
    private boolean[] visited;
    private int[] parent;
    private boolean finished;

    @Override
    public void initialize(Graph graph, int start) {
        this.graph = graph;
        this.queue = new LinkedList<>();
        this.visited = new boolean[graph.getVertices()];
        this.parent = new int[graph.getVertices()];
        this.finished = false;

        Arrays.fill(parent, -1);

        visited[start] = true; //visita el nodo inicial
        queue.add(start); //lo añade a la cola
    }

    @Override
    public boolean hasNextStep() {
        return !queue.isEmpty() && !finished;
    }

    @Override
    public AlgorithmStep nextStep() {

        if (!hasNextStep()) {
            finished = true;
            return null;
        }

        //si tiene siguiente paso, entonces saca un nodo de la cola
        int current = queue.poll();

        //se crea un conjunto de nodos visitados
        Set<Integer> visitedSet = new HashSet<>();
        for (int i = 0; i < visited.length; i++) {
            if (visited[i]) visitedSet.add(i);
        }

        //por cada arista incidente al vertice current, se revisa si el nodo al que lleva ya fue visitado
        for (Edge edge : graph.getNeighbors(current)) {
            int neighbor = edge.getTo();
            //si no ha sido visitado, lo visita
            if (!visited[neighbor]) {
                visited[neighbor] = true;
                parent[neighbor] = current;
                queue.add(neighbor);
            }
        }

        //Devuelve un paso. Esto es para mostrar el algoritmo por pasos.
        return new AlgorithmStep(
                "Procesando nodo " + current,
                current, null,
                visitedSet,
                null
        );
    }

    @Override
    public BFSResult getResult() {
        finished = true;
        return new BFSResult(parent);
    }

    @Override
    public boolean isApplicable(Graph graph) {
        return true; // BFS funciona para cualquier grafo pero ignora los pesos
    }
}