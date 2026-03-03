package grafosgui;

import grafos.model.Graph;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #1a252f;");


        GraphPane graphPane = new GraphPane();
        ControlPane controlPane = new ControlPane(graphPane);
        VBox controls = createControls(graphPane);

        root.setLeft(controls);
        root.setCenter(graphPane);
        root.setRight(controlPane);

        BorderPane.setMargin(graphPane, new Insets(10, 20, 10, 20));
        BorderPane.setMargin(controlPane, new Insets(0, 10, 0, 0));


        root.setStyle("-fx-background-color: #2c3e50;"); // Color de fondo moderno
        root.setPadding(new Insets(20));


        Scene scene = new Scene(root, 1200, 700);
        stage.setTitle("Graph Visualizer");
        stage.setScene(scene);
        stage.show();
    }

    private VBox createControls(GraphPane graphPane) {

        String inputStyle = """
        -fx-background-color: #2c3e50;
        -fx-text-fill: white;
        -fx-prompt-text-fill: #95a5a6;
        -fx-border-color: #7f8c8d;
        -fx-border-radius: 5;
        -fx-background-radius: 5;
    """;

        TextField verticesField = new TextField();
        verticesField.setPromptText("Número de nodos (n)");
        verticesField.setStyle(inputStyle);
        verticesField.setPrefWidth(120);

        TextField edgesCountField = new TextField();
        edgesCountField.setPromptText("Número de aristas (m)");
        edgesCountField.setStyle(inputStyle);
        edgesCountField.setPrefWidth(120);

        HBox topFields = new HBox(10, verticesField, edgesCountField);

        TextArea edgesArea = new TextArea();
        edgesArea.setPromptText("""
Formato:
u v d c
.
.
.
""");
        edgesArea.setStyle("""
    -fx-control-inner-background: #2c3e50; 
    -fx-text-fill: white;
    -fx-prompt-text-fill: #95a5a6;
    -fx-border-color: #7f8c8d;
    -fx-background-color: #2c3e50;
""");
        edgesArea.setPrefHeight(200);

        CheckBox directedBox = new CheckBox("Dirigido");
        directedBox.setStyle("-fx-text-fill: white;");

        Button createButton = new Button("Crear Grafo");
        createButton.setMaxWidth(Double.MAX_VALUE);
        createButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
        createButton.setOnAction(e -> {

            try {
                int vertices = Integer.parseInt(verticesField.getText());
                int expectedEdges = Integer.parseInt(edgesCountField.getText());
                boolean directed = directedBox.isSelected();

                String[] lines = edgesArea.getText().split("\n");

                int actualEdges = 0;
                for (String line : lines) {
                    if (!line.isBlank()) actualEdges++;
                }

                if (actualEdges != expectedEdges) {
                    showError("La cantidad de aristas ingresadas (" + actualEdges +
                            ") no coincide con el número especificado (" + expectedEdges + ").");
                    return;
                }

                Graph graph = new Graph(vertices, directed);

                for (String line : lines) {

                    if (line.isBlank()) continue;

                    String[] parts = line.trim().split(" ");

                    if (parts.length != 4) {
                        showError("Cada línea debe tener formato: u v w m");
                        return;
                    }

                    int from = Integer.parseInt(parts[0]);
                    int to = Integer.parseInt(parts[1]);
                    int weight = Integer.parseInt(parts[2]);
                    int victims = Integer.parseInt(parts[3]);

                    if (from >= vertices || to >= vertices || from < 0 || to < 0) {
                        showError("El número máximo de nodo permitido es: " + (vertices - 1));
                        return;
                    }

                    graph.addEdge(from, to, weight);

                    if (graph.getNode(to).getVictims() == 0) {
                        graph.getNode(to).setVictims(victims);
                    }
                }

                graphPane.setGraph(graph);

            } catch (NumberFormatException ex) {
                showError("Verifica que todos los campos sean números válidos.");
            }
        });


        VBox box = new VBox(15, topFields, edgesArea, directedBox, createButton);
        box.setPrefWidth(280); // Ancho fijo para que no invada el centro
        box.setPadding(new Insets(20));
        box.setStyle("-fx-background-color: #34495e; -fx-background-radius: 0 15 15 0;"); // Bordes redondeados solo a la derecha
        box.setEffect(new javafx.scene.effect.DropShadow(10, Color.BLACK));

        return box;
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}