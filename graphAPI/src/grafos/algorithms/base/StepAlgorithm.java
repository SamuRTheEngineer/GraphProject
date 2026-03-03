package grafos.algorithms.base;

import grafos.model.Graph;

public interface StepAlgorithm<R extends AlgorithmResult> {

    void initialize(Graph graph, int start);

    boolean hasNextStep();

    AlgorithmStep nextStep();

    R getResult();

    boolean isApplicable(Graph graph);
}
