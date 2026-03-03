package grafos.algorithms;

import grafos.algorithms.base.*;
import grafos.model.Edge;
import grafos.model.Graph;

import java.util.*;

public class Dijkstra implements StepAlgorithm<DijkstraResult> {

    //encuentra el camino mas corto desde un origen hasta los demas nodos teniendo en cuenta su peso.

    private Graph graph;
    private int[] dist;
    private int[] parent;
    private boolean[] visited;
    private PriorityQueue<NodeDistance> pq; //priority queue que guarda las distancias a un nodo desde el inicial

    private static class NodeDistance implements Comparable<NodeDistance> {
        int node;
        int distance;

        NodeDistance(int node, int distance) {
            this.node = node;
            this.distance = distance;
        }

        @Override
        public int compareTo(NodeDistance other) {
            return Integer.compare(this.distance, other.distance);
        }
    }

    @Override
    public void initialize(Graph graph, int start) {
        this.graph = graph;

        int V = graph.getVertices();

        dist = new int[V]; //distancia
        parent = new int[V];
        visited = new boolean[V];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);

        dist[start] = 0;

        pq = new PriorityQueue<>();
        pq.add(new NodeDistance(start, 0));
    }

    @Override
    public boolean hasNextStep() {
        return !pq.isEmpty();
    }

    @Override
    public AlgorithmStep nextStep() {

        if (!hasNextStep()) {
            throw new NoSuchElementException("No more steps available");
        }

        NodeDistance currentPair = pq.poll();
        int current = currentPair.node;

        if (visited[current]) {
            return nextStep(); // ignorar entradas viejas (esta visitado)
        }

        visited[current] = true;

        //para cada nodo vecino del current
        for (Edge edge : graph.getNeighbors(current)) {
            int neighbor = edge.getTo();
            int weight = edge.getWeight();

            //si no esta visitado y la distancia al actual + su peso es menor a la distancia al vecino
            if (!visited[neighbor] &&
                    dist[current] + weight < dist[neighbor]) {
                //entonces lo actualiza, porque encontro un mejor camino
                dist[neighbor] = dist[current] + weight;
                parent[neighbor] = current;
                pq.add(new NodeDistance(neighbor, dist[neighbor]));
            }
        }

        Set<Integer> visitedSet = new HashSet<>();
        for (int i = 0; i < visited.length; i++) {
            if (visited[i]) visitedSet.add(i);
        }
        //creamos un conjunto de nodos visitados para visualizacion

        return new AlgorithmStep(
                "Procesando nodo " + current +
                        " (dist=" + dist[current] + ")",
                current,
                null,
                visitedSet,
                null
        );
    }

    @Override
    public DijkstraResult getResult() {
        return new DijkstraResult(dist, parent);
    }

    @Override
    public boolean isApplicable(Graph graph) {
        //aplica para grafos con peso no negativo
        for (Edge e : graph.getEdges()) {
            if (e.getWeight() < 0) return false;
        }

        return true;
    }
}