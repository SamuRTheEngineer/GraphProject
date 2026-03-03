package grafos.algorithms;

import grafos.algorithms.base.AlgorithmResult;

public class BellmanFordAdaptedResult implements AlgorithmResult {

    private final int[] gain;
    private final int[] parent;


    public BellmanFordAdaptedResult(int[] gain, int[] parent) {
        this.gain = gain;
        this.parent = parent;
    }

    public int[] getGain() {
        return gain;
    }

    public int[] getParent() {
        return parent;
    }
}
