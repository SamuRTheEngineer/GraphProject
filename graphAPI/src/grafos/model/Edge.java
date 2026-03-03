package grafos.model;

public class Edge implements Comparable<Edge> {

    private final int from;
    private final int to;
    private final int weight; //si queremos un grafo sin peso, simplemente le ponemos 1 a todos los pesos de lss aristas.

    public Edge(int from, int to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge other) {
        return Integer.compare(this.weight, other.weight);
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public int getWeight() {
        return weight;
    }
}