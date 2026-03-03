package grafosgui;

import grafos.model.Graph;
import grafos.model.Edge;
import grafos.algorithms.base.AlgorithmStep;

import javafx.scene.layout.Pane;

import java.util.*;

public class GraphPane extends Pane {

    private Graph graph;

    private Map<Integer, NodeView> nodes = new HashMap<>();
    private List<EdgeView> edges = new ArrayList<>();

    public GraphPane() {
        setPrefSize(700, 600);
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
        drawGraph();
    }

    public Graph getGraph() {
        return graph;
    }

    public void updateEdgePositions() {
        // Las aristas se actualizan solas gracias al "bind" de JavaFX
    }

    public void highlightPath(List<Integer> path) {
        if (path == null || path.size() < 2) return;

        //limpiar resaltados anteriores
        nodes.values().forEach(NodeView::setDefaultColor);
        edges.forEach(EdgeView::setDefaultColor);

        for (int i = 0; i < path.size() - 1; i++) {
            int u = path.get(i);
            int v = path.get(i + 1);

            // resaltar el nodo
            nodes.get(u).setCurrentColor();

            // buscar y resaltar la arista entre u y v
            for (EdgeView ev : edges) {
                if (graph.isDirected()) {
                    if (ev.getFrom() == u && ev.getTo() == v) {
                        ev.setActiveColor();
                    }
                } else {
                    if ((ev.getFrom() == u && ev.getTo() == v) || (ev.getFrom() == v && ev.getTo() == u)) {
                        ev.setActiveColor();
                    }
                }
            }
        }
        // resaltar el último nodo (el destino)
        nodes.get(path.get(path.size() - 1)).setCurrentColor();
    }

    private void drawGraph() {
        getChildren().clear();
        nodes.clear();
        edges.clear();

        int n = graph.getVertices();
        Random rnd = new Random();

        // Crear nodos en posiciones aleatorias
        for (int i = 0; i < n; i++) {
            // Dejamos un margen de 50px para que no toquen los bordes
            double x = 50 + (getWidth() - 100) * rnd.nextDouble();
            double y = 50 + (getHeight() - 100) * rnd.nextDouble();

            int victims = graph.getNode(i).getVictims();
            NodeView node = new NodeView(i, victims, x, y);
            node.enableDrag(this); // Habilitamos el arrastre
            nodes.put(i, node);
        }

        // Crear aristas vinculadas a los nodos
        for (Edge e : graph.getEdges()) {
            NodeView startNode = nodes.get(e.getFrom());
            NodeView endNode = nodes.get(e.getTo());

            EdgeView edgeView = new EdgeView(startNode, endNode, e.getWeight(), graph.isDirected());
            edges.add(edgeView);
            getChildren().add(edgeView);
        }

        // Añadir los nodos al final para que queden por encima de las aristas
        getChildren().addAll(nodes.values());
    }

    public void updateStep(AlgorithmStep step) {

        if (step == null) return;

        // Reset nodos
        nodes.values().forEach(NodeView::setDefaultColor);

        // Reset aristas
        edges.forEach(EdgeView::setDefaultColor);

        // Visitados
        if (step.getVisited() != null) {
            for (Integer v : step.getVisited()) {
                nodes.get(v).setVisitedColor();
            }
        }

        // Nodo actual
        if (step.getCurrentNode() != null) {
            nodes.get(step.getCurrentNode())
                    .setCurrentColor();
        }

        // Arista activa
        if (step.getCurrentEdge() != null) {
            int from = step.getCurrentEdge().getFrom();
            int to = step.getCurrentEdge().getTo();

            for (EdgeView ev : edges) {
                if ((ev.getFrom() == from && ev.getTo() == to) ||
                        (ev.getFrom() == to && ev.getTo() == from)) {

                    ev.setActiveColor();
                }
            }
        }
    }
}