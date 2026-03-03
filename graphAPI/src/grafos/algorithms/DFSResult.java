package grafos.algorithms;

import grafos.algorithms.base.AlgorithmResult;

public class DFSResult implements AlgorithmResult {

    private final int[] parent; //parent[v] daria el nodo desde el cual se descubrio v.
    //es el arbol de exploracion en profundidad como un arreglo de padres
    //es una version orientada a visualizacion. Aún no se guarda tiempo de descubrimiento ni finalización.
    public DFSResult(int[] parent) {
        this.parent = parent;
    }

    public int[] getParent() {
        return parent;
    }
}