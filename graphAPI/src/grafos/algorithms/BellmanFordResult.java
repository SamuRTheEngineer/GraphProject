package grafos.algorithms;

import grafos.algorithms.base.AlgorithmResult;

public class BellmanFordResult implements AlgorithmResult {

    private final int[] dist; //distancias a los nodos desde el inicial
    private final int[] parent; //arbol de padres
    private final boolean hasNegativeCycle; //indica si tiene un ciclo negativo

    public BellmanFordResult(int[] dist, int[] parent, boolean hasNegativeCycle) {
        this.dist = dist;
        this.parent = parent;
        this.hasNegativeCycle = hasNegativeCycle;
    }

    public int[] getDist() {
        return dist;
    }

    public int[] getParent() {
        return parent;
    }

    public boolean hasNegativeCycle() {
        return hasNegativeCycle;
    }
}