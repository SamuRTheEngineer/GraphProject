package grafos.algorithms;

import grafos.algorithms.base.AlgorithmResult;

public class DijkstraResult implements AlgorithmResult {

    private final int[] dist; //distancia hacia cada nodo
    private final int[] parent; //arbol de caminos minimos

    public DijkstraResult(int[] dist, int[] parent) {
        this.dist = dist;
        this.parent = parent;
    }

    public int[] getDist() {
        return dist;
    }

    public int[] getParent() {
        return parent;
    }
}