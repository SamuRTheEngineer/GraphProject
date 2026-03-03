package grafos.model;

import java.util.ArrayList;
import java.util.List;

public class Graph {



    private final boolean directed; //si es dirigido o no
    private final List<List<Edge>> adj; //matriz de adyacencia
    private final List<Edge> edges; //lista de aristas
    private final List<Node> nodes; //lista de nodos

    public Graph(int vertices, boolean directed) { //constructor
        this.directed = directed;
        this.adj = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.nodes = new ArrayList<>();

        for (int i = 0; i < vertices; i++) {
            adj.add(new ArrayList<>());
            nodes.add(new Node(i));
        }
    }

    public Node getNode(int id) {
        return nodes.get(id);
    }

    public int getVertices() {
        return nodes.size();
    }

    public void addEdge(int u, int v, int weight) {
        validateVertex(u);
        validateVertex(v);

        Edge edge = new Edge(u, v, weight); //si los numeros de nodo son validos, crea una arista

        // Lista global (una sola vez)
        edges.add(edge);

        // Lista de adyacencia
        adj.get(u).add(edge);

        if (!directed) { //si no es dirigido, entonces en la lista de adyacencia se añade la arista opuesta. Así, se puede ir se u a v y de v a u.
            Edge reverse = new Edge(v, u, weight);
            adj.get(v).add(reverse);
        }
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public List<Edge> getNeighbors(int vertex) {
        validateVertex(vertex);
        return adj.get(vertex);
    }

    public boolean isDirected() {
        return directed;
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= getVertices()) {
            throw new IllegalArgumentException("Invalid vertex: " + v);
        }
    }
}