package grafos.algorithms.base;

import grafos.model.Edge;

import java.util.Set;

public class AlgorithmStep {

    private final String description;
    private final Integer currentNode;
    private final Edge currentEdge;
    private final Set<Integer> visited;
    private final Set<Edge> activeEdges;

    public AlgorithmStep(String description,
                         Integer currentNode,
                         Edge currentEdge,
                         Set<Integer> visited,
                         Set<Edge> activeEdges) {
        this.description = description;
        this.currentNode = currentNode;
        this.currentEdge = currentEdge;
        this.visited = visited;
        this.activeEdges = activeEdges;
    }

    public String getDescription() {
        return description;
    }

    public Integer getCurrentNode() {
        return currentNode;
    }

    public Edge getCurrentEdge() {
        return currentEdge;
    }

    public Set<Integer> getVisited() {
        return visited;
    }

    public Set<Edge> getActiveEdges() {
        return activeEdges;
    }
}