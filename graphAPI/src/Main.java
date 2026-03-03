import grafos.model.Graph;
import grafos.model.Edge;
import grafos.algorithms.*;
import grafos.algorithms.base.AlgorithmStep;

public class Main {

    public static void main(String[] args) {

        Graph graph = new Graph(6, false);

        graph.addEdge(0,1,1);
        graph.addEdge(0,3,1);
        graph.addEdge(1,2,1);
        graph.addEdge(1,4,1);
        graph.addEdge(3,4,1);
        graph.addEdge(3,5,1);
        graph.addEdge(4,5,1);

        // ================= BFS =================
        System.out.println("===== BFS =====");

        BFS bfs = new BFS();
        bfs.initialize(graph, 0);

        while (bfs.hasNextStep()) {
            AlgorithmStep step = bfs.nextStep();
            System.out.println(step.getDescription());
            System.out.println("Visitados: " + step.getVisited());
            System.out.println("---------------------");
        }

        BFSResult bfsResult = bfs.getResult();
        printParent("BFS Parent Array", bfsResult.getParent());

        // ================= DFS =================
        System.out.println("\n===== DFS =====");

        DFS dfs = new DFS();
        dfs.initialize(graph, 0);

        while (dfs.hasNextStep()) {
            AlgorithmStep step = dfs.nextStep();
            System.out.println(step.getDescription());
            System.out.println("Visitados: " + step.getVisited());
            System.out.println("---------------------");
        }

        DFSResult dfsResult = dfs.getResult();
        printParent("DFS Parent Array", dfsResult.getParent());

        // ================= KRUSKAL =================
        System.out.println("\n===== KRUSKAL =====");

        Kruskal kruskal = new Kruskal();
        kruskal.initialize(graph, 0);

        while (kruskal.hasNextStep()) {
            AlgorithmStep step = kruskal.nextStep();
            System.out.println(step.getDescription());
        }

        KruskalResult kruskalResult = kruskal.getResult();
        System.out.println("MST final:");
        for (Edge e : kruskalResult.getMst()) {
            System.out.println(e.getFrom() + " - " + e.getTo() +
                    " (peso " + e.getWeight() + ")");
        }

        System.out.println("\n===== DIJKSTRA =====");

        Dijkstra dijkstra = new Dijkstra();

        if (!dijkstra.isApplicable(graph)) {
            System.out.println("El grafo no es válido para Dijkstra.");
            return;
        }

        dijkstra.initialize(graph, 0);

        while (dijkstra.hasNextStep()) {
            AlgorithmStep step = dijkstra.nextStep();
            System.out.println(step.getDescription());
        }

        DijkstraResult result = dijkstra.getResult();

        System.out.println("Distancias finales:");
        int[] dist = result.getDist();
        for (int i = 0; i < dist.length; i++) {
            System.out.println("Nodo " + i + " → " + dist[i]);
        }

        System.out.println("\n===== BELLMAN-FORD =====");

        BellmanFord bf = new BellmanFord();
        bf.initialize(graph, 0);

        while (bf.hasNextStep()) {
            AlgorithmStep step = bf.nextStep();
            System.out.println(step.getDescription());
        }

        BellmanFordResult bfResult = bf.getResult();

        if (bfResult.hasNegativeCycle()) {
            System.out.println("El grafo tiene ciclo negativo.");
        } else {
            System.out.println("Distancias finales:");
            int[] dist1 = bfResult.getDist();
            for (int i = 0; i < dist1.length; i++) {
                System.out.println("Nodo " + i + " → " + dist1[i]);
            }
        }


    }

    private static void printParent(String title, int[] parent) {
        System.out.println(title);
        for (int i = 0; i < parent.length; i++) {
            System.out.println("Nodo " + i + " ← " + parent[i]);
        }
    }
}