package grafos.algorithms;

import grafos.algorithms.base.AlgorithmResult;
import grafos.model.Edge;

import java.util.List;

public class KruskalResult implements AlgorithmResult {

    private final List<Edge> mst;

    public KruskalResult(List<Edge> mst) {
        this.mst = mst;
    }

    public List<Edge> getMst() {
        return mst;
    }
}