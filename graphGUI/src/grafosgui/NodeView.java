package grafosgui;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class NodeView extends StackPane {

    private final int id;
    private final Circle circle;
    private final Text label;
    private final Text subtitle;
    private double mouseX, mouseY;


    public int getNodeId() {
        return id;
    }

    public NodeView(int id, int victims, double x, double y) {
        this.id = id;

        circle = new Circle(22 + victims * 1.5);        circle.setStroke(Color.WHITE);
        circle.setStrokeWidth(2);

        //sombra
        this.setEffect(new javafx.scene.effect.DropShadow(5, Color.rgb(0, 0, 0, 0.5)));

        label = new Text(String.valueOf(id));
        label.setStyle("-fx-font-weight: bold; -fx-fill: #2c3e50; -fx-font-size: 14px;");

        subtitle = new Text("c = "+victims);
        subtitle.setStyle("-fx-font-size: 11px; -fx-fill: white;");
        subtitle.setOpacity(0.85);


        setLayoutX(x - 22);
        setLayoutY(y - 22);

        setDefaultColor();

        VBox textBox = new VBox(-2, label, subtitle);
        textBox.setAlignment(javafx.geometry.Pos.CENTER);

        getChildren().addAll(circle, textBox);
    }

    public void enableDrag(GraphPane graphPane) {
        this.setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
            this.setCursor(javafx.scene.Cursor.CLOSED_HAND);
        });

        this.setOnMouseDragged(e -> {
            double deltaX = e.getSceneX() - mouseX;
            double deltaY = e.getSceneY() - mouseY;

            // Actualizar posición del nodo
            setLayoutX(getLayoutX() + deltaX);
            setLayoutY(getLayoutY() + deltaY);

            mouseX = e.getSceneX();
            mouseY = e.getSceneY();

            // Notificar al panel para que redibuje las aristas
            graphPane.updateEdgePositions();
        });

        this.setOnMouseReleased(e -> this.setCursor(javafx.scene.Cursor.HAND));
        this.setCursor(javafx.scene.Cursor.HAND);
    }


    public void setColor(Color color) {
        javafx.animation.FillTransition ft = new javafx.animation.FillTransition(
                javafx.util.Duration.millis(300), circle, (Color) circle.getFill(), color
        );
        ft.play();
    }

    public void setVisitedColor() { setColor(Color.web("#2ecc71")); }
    public void setCurrentColor() { setColor(Color.web("#f1c40f")); }
    public void setDefaultColor() { setColor(Color.web("#0088ff")); }

}