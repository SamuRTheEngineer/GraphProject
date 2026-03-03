package grafosgui;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class EdgeView extends Group {

    private final int from;
    private final int to;
    private final Line line;
    private final Polygon arrowHead;
    private final Text weightText; // Nuevo: Texto del peso
    private final boolean isDirected;

    public EdgeView(NodeView startNode, NodeView endNode, int weight, boolean isDirected) {
        this.from = startNode.getNodeId();
        this.to = endNode.getNodeId();
        this.isDirected = isDirected;

        line = new Line();

        // Vincular la línea a los centros de los nodos
        line.startXProperty().bind(startNode.layoutXProperty().add(startNode.widthProperty().divide(2)));
        line.startYProperty().bind(startNode.layoutYProperty().add(startNode.heightProperty().divide(2)));
        line.endXProperty().bind(endNode.layoutXProperty().add(endNode.widthProperty().divide(2)));
        line.endYProperty().bind(endNode.layoutYProperty().add(endNode.heightProperty().divide(2)));

        // Estilo de la línea
        line.setStroke(Color.web("#95a5a6", 0.7));
        line.setStrokeWidth(3);

        // Peso (Texto)
        weightText = new Text(String.valueOf(weight));
        weightText.setFill(Color.WHITE);
        // Vincular el peso al punto medio de la línea
        weightText.xProperty().bind(line.startXProperty().add(line.endXProperty()).divide(2).add(10));
        weightText.yProperty().bind(line.startYProperty().add(line.endYProperty()).divide(2).subtract(10));

        getChildren().addAll(line, weightText);

        // Para la flecha, es mejor usar un método que se actualice
        if (isDirected) {
            arrowHead = new Polygon();
            arrowHead.setFill(Color.web("#95a5a6", 0.7));
            getChildren().add(arrowHead);

            // Listener para mover la flecha cuando la línea cambie
            javafx.beans.value.ChangeListener<Number> arrowUpdater = (obs, oldVal, newVal) -> updateArrow();
            line.startXProperty().addListener(arrowUpdater);
            line.startYProperty().addListener(arrowUpdater);
            line.endXProperty().addListener(arrowUpdater);
            line.endYProperty().addListener(arrowUpdater);
        } else {
            arrowHead = null;
        }
    }

    private Polygon createArrowHead(double x1, double y1, double x2, double y2) {
        double radius = 25; // Radio del nodo para que la flecha quede en el borde, no en el centro
        double angle = Math.atan2((y2 - y1), (x2 - x1));

        // Ajustar el punto final para que la flecha toque el borde del círculo
        double endX = x2 - radius * Math.cos(angle);
        double endY = y2 - radius * Math.sin(angle);

        Polygon arrow = new Polygon();
        double arrowSize = 10;

        // Puntos del triángulo de la flecha
        arrow.getPoints().addAll(
                endX, endY,
                endX - arrowSize * Math.cos(angle - Math.toRadians(30)),
                endY - arrowSize * Math.sin(angle - Math.toRadians(30)),
                endX - arrowSize * Math.cos(angle + Math.toRadians(30)),
                endY - arrowSize * Math.sin(angle + Math.toRadians(30))
        );

        arrow.setFill(Color.web("#fcfcfc", 0.7));
        return arrow;
    }

    private void updateArrow() {
        if (arrowHead == null) return;
        double x1 = line.getStartX();
        double y1 = line.getStartY();
        double x2 = line.getEndX();
        double y2 = line.getEndY();

        double radius = 28;
        double angle = Math.atan2((y2 - y1), (x2 - x1));
        double endX = x2 - radius * Math.cos(angle);
        double endY = y2 - radius * Math.sin(angle);

        double arrowSize = 10;
        arrowHead.getPoints().setAll(
                endX, endY,
                endX - arrowSize * Math.cos(angle - Math.toRadians(30)),
                endY - arrowSize * Math.sin(angle - Math.toRadians(30)),
                endX - arrowSize * Math.cos(angle + Math.toRadians(30)),
                endY - arrowSize * Math.sin(angle + Math.toRadians(30))
        );
    }

    public void setDefaultColor() {
        line.setStroke(Color.web("#95a5a6", 0.7));
        weightText.setFill(Color.WHITE);
        if (arrowHead != null) arrowHead.setFill(Color.web("#95a5a6", 0.7));
    }

    public void setActiveColor() {
        line.setStroke(Color.web("#e74c3c"));
        weightText.setFill(Color.web("#e74c3c"));
        if (arrowHead != null) arrowHead.setFill(Color.web("#e74c3c"));
    }

    public int getFrom() { return from; }
    public int getTo() { return to; }
}
