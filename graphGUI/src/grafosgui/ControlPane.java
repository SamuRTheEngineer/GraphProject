package grafosgui;

import grafos.algorithms.*;
import grafos.algorithms.base.*;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ControlPane extends VBox {

    private StepAlgorithm<?> algorithm;

    private List<Integer> getPathList(int[] parent, int target) {
        List<Integer> path = new ArrayList<>();
        int current = target;
        while (current != -1) {
            path.add(current);
            current = parent[current];
        }
        Collections.reverse(path);
        return path;
    }

    private String buildPath(int[] parent, int target) {
        StringBuilder sb = new StringBuilder();
        int current = target;
        while (current != -1) {
            sb.insert(0, "[" + current + "] ");
            if (parent[current] != -1) sb.insert(0, "→ ");
            current = parent[current];
        }
        return sb.toString();
    }

    public ControlPane(GraphPane graphPane) {
        // --- ESTILO DEL PANEL LATERAL ---
        setSpacing(20);
        setPadding(new Insets(25));
        setStyle("-fx-background-color: #2c3e50; -fx-background-radius: 0 15 15 0; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 10, 0, 0, 0);");
        setPrefWidth(240);

        Label title = new Label("GRAFOS");
        title.setStyle("-fx-text-fill: #ecf0f1; -fx-font-size: 20px; -fx-font-weight: 900; -fx-letter-spacing: 2px;");

        // --- SECCIÓN: CONFIGURACIÓN ---
        VBox configBox = new VBox(8);

        ComboBox<String> algoBox = new ComboBox<>();
        algoBox.setMaxWidth(Double.MAX_VALUE);
        algoBox.setPromptText("Seleccionar Algoritmo");
        algoBox.getItems().addAll("BFS", "DFS", "Dijkstra", "Bellman-Ford", "Bellman-Ford Adapted");
        algoBox.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-border-color: #7f8c8d; -fx-border-radius: 5;");

        // Estilo personalizado para las celdas del ComboBox (para que se vean blancas)
        algoBox.setButtonCell(new ListCell<>() {
            @Override protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? algoBox.getPromptText() : item);
                setStyle("-fx-text-fill: white;");
            }
        });

        Label targetLabel = new Label("NODO DESTINO");
        targetLabel.setStyle("-fx-text-fill: #bdc3c7; -fx-font-size: 10px; -fx-font-weight: bold;");
        targetLabel.setVisible(false);
        targetLabel.setManaged(false);

        TextField targetField = new TextField();
        targetField.setPromptText("Ej: 5");
        targetField.setStyle("-fx-background-color: #34495e; -fx-text-fill: #f1c40f; -fx-font-weight: bold; -fx-border-color: #7f8c8d; -fx-border-radius: 5;");
        targetField.setVisible(false);
        targetField.setManaged(false);

        configBox.getChildren().addAll(algoBox, targetLabel, targetField);

        // --- SECCIÓN: BOTONES ---
        VBox actionsBox = new VBox(10);
        Button startBtn = new Button("▶  INICIAR");
        startBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 5;");
        startBtn.setMaxWidth(Double.MAX_VALUE);

        Button nextBtn = new Button("➡  SIGUIENTE PASO");
        nextBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 5;");
        nextBtn.setMaxWidth(Double.MAX_VALUE);
        actionsBox.getChildren().addAll(startBtn, nextBtn);

        // --- SECCIÓN: MENSAJES DE ESTADO ---
        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: #2ecc71; -fx-font-size: 11px; -fx-font-style: italic;");
        messageLabel.setWrapText(true);

        // --- SECCIÓN: RESULTADOS (Path y Weight) ---
        VBox resultBox = new VBox(12);
        resultBox.setPadding(new Insets(15, 10, 15, 10));
        resultBox.setStyle("-fx-background-color: #1a252f; -fx-background-radius: 10; -fx-border-color: #34495e;");
        // El cuadro de resultados no se ve ni ocupa espacio al inicio
        resultBox.setVisible(false);
        resultBox.setManaged(false);



        Label pathLabel = new Label("Esperando resultado...");
        pathLabel.setStyle("-fx-text-fill: #ecf0f1; -fx-font-size: 14px; -fx-font-family: 'Monospaced';");
        pathLabel.setWrapText(true);

        Label weightLabel = new Label("--");
        weightLabel.setStyle("-fx-text-fill: #00d2ff; -fx-font-size: 14px; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, #00d2ff, 1, 0.2, 0, 0);");

        resultBox.getChildren().addAll(pathLabel, weightLabel);

        // Añadir todo al ControlPane en orden
        getChildren().addAll(title, new Separator(), configBox, actionsBox, messageLabel, resultBox);

        //logica
        startBtn.setOnAction(e -> {
            String selected = algoBox.getValue();
            //decidimos si el algoritmo es de costo osea que muestra resultados.
            boolean esAlgoritmoCosto = "Dijkstra".equals(selected) || "Bellman-Ford Adapted".equals(selected);
            // Lo ocultamos al iniciar para que no muestre datos viejos
            resultBox.setVisible(false);
            resultBox.setManaged(false);
            targetField.setVisible(false);
            targetField.setManaged(false);
            targetLabel.setVisible(false);
            targetLabel.setManaged(false);
            weightLabel.setText("--");
            pathLabel.setText("Esperando resultado...");

            switch (selected != null ? selected : "") {
                case "BFS" -> algorithm = new BFS();
                case "DFS" -> algorithm = new DFS();
                case "Dijkstra" -> algorithm = new Dijkstra();
                case "Bellman-Ford" -> algorithm = new BellmanFord();
                case "Bellman-Ford Adapted" -> algorithm = new BellmanFordAdapted();
            }

            if (algorithm != null) {
                algorithm.initialize(graphPane.getGraph(), 0);
                messageLabel.setText("✔ Algoritmo listo");

                if (esAlgoritmoCosto) {
                    resultBox.setVisible(true);
                    resultBox.setManaged(true);
                    targetField.setVisible(true);
                    targetField.setManaged(true);
                    targetLabel.setVisible(true);
                    targetLabel.setManaged(true);
                    FadeTransition ft = new FadeTransition(Duration.millis(500), resultBox);
                    ft.setFromValue(0);
                    ft.setToValue(1);
                    ft.play();
                }

                FadeTransition fadeIn = new FadeTransition(Duration.millis(400), messageLabel);
                fadeIn.setFromValue(0); fadeIn.setToValue(1);
                PauseTransition stay = new PauseTransition(Duration.seconds(2));
                FadeTransition fadeOut = new FadeTransition(Duration.millis(400), messageLabel);
                fadeOut.setFromValue(1); fadeOut.setToValue(0);
                new SequentialTransition(fadeIn, stay, fadeOut).play();
            }
        });

        nextBtn.setOnAction(e -> {
            if (algorithm != null) {
                if (algorithm.hasNextStep()) {
                    AlgorithmStep step = algorithm.nextStep();
                    graphPane.updateStep(step);
                } else if (algorithm instanceof Dijkstra dijkstra) {
                    DijkstraResult result = dijkstra.getResult();
                    int[] parent = result.getParent();
                    int[] distance = result.getDist();
                    int target;

                    try {
                        target = Integer.parseInt(targetField.getText());
                    } catch (NumberFormatException ex) {
                        pathLabel.setText("⚠ Nodo inválido");
                        return;
                    }

                    if (target < 0 || target >= graphPane.getGraph().getVertices()) {
                        pathLabel.setText("❌ Fuera de rango");
                        return;
                    }
                    if (distance[target] == Integer.MAX_VALUE) {
                        pathLabel.setText("🚫 Inalcanzable");
                        weightLabel.setText("Costo: ∞");
                        return;
                    }
                    FadeTransition ft = new FadeTransition(Duration.millis(500), resultBox);
                    ft.setFromValue(0);
                    ft.setToValue(1);
                    ft.play();

                    List<Integer> fullPath = getPathList(parent, target);

                    pathLabel.setText("📍 " + buildPath(parent, target));
                    weightLabel.setText("🏁 COSTO TOTAL: " + distance[target]);

                    graphPane.highlightPath(fullPath);

                } else if (algorithm instanceof BellmanFordAdapted bellmanFordAdapted) {

                    BellmanFordAdaptedResult result = bellmanFordAdapted.getResult();
                    int[] parent = result.getParent();
                    int[] gain = result.getGain();
                    int target;


                    try {
                        target = Integer.parseInt(targetField.getText());
                    } catch (NumberFormatException ex) {
                        pathLabel.setText("⚠ Nodo inválido");
                        return;
                    }

                    if (target < 0 || target >= graphPane.getGraph().getVertices()) {
                        pathLabel.setText("❌ Fuera de rango");
                        return;
                    }
                    if (gain[target] == Integer.MIN_VALUE) {
                        pathLabel.setText("🚫 Inalcanzable");
                        weightLabel.setText("Ganancia: -∞");
                        return;
                    }

                    if (result.isPositiveCycleDetected()) {
                        pathLabel.setText("🚫 Ciclo Positivo Detectado");
                        weightLabel.setText("Ganancia: ∞");
                        return;
                    }

                    FadeTransition ft = new FadeTransition(Duration.millis(500), resultBox);
                    ft.setFromValue(0);
                    ft.setToValue(1);
                    ft.play();


                    List<Integer> fullPath = getPathList(parent, target);
                    pathLabel.setText("📍 " + buildPath(parent, target));
                    weightLabel.setText("🏁 GANANCIA TOTAL: " + gain[target]);

                    graphPane.highlightPath(fullPath);

                }
            }
        });
    }
}



/*
7 8
0 1 4 3
0 2 2 0
1 3 5 5
2 3 8 5
2 4 10 1
3 5 2 4
5 6 3 0
4 5 2 4
 */