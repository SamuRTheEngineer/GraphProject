package grafos.algorithms;

import grafos.algorithms.base.AlgorithmResult;

public class BellmanFordAdaptedResult implements AlgorithmResult {

    private final int[] gain;
    private final int[] parent;
    private boolean positiveCycleDetected;


    public BellmanFordAdaptedResult(int[] gain, int[] parent, boolean positiveCycleDetected) {
        this.gain = gain;
        this.parent = parent;
        this.positiveCycleDetected = positiveCycleDetected;
    }

    public int[] getGain() {
        return gain;
    }

    public int[] getParent() {
        return parent;
    }

    public boolean isPositiveCycleDetected() {
        return positiveCycleDetected;
    }
}
