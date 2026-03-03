package grafos.algorithms;

import grafos.algorithms.base.AlgorithmResult;

public class BFSResult implements AlgorithmResult {

    private final int[] parent; //el resultado del BFS es el arbol de expansion en forma de arreglo de padres
    //la posicion de cada nodo me dice quien es su padre
    //asi, podemos calcular caminos minimos y distancias contando hacia atras

    public BFSResult(int[] parent) {
        this.parent = parent;
    }

    public int[] getParent() {
        return parent;
    }
}