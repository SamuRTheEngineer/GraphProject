package grafos.structures;

public class DisjointSet {

    private final int[] parent; //padre de cada nodo
    private final int[] rank; //es una heurística para mantener el arbol mas plano posible
    //no es la altura real pero se usa para decidir cual arbol va debajo de otro

    public DisjointSet(int size) {
        parent = new int[size];
        rank = new int[size];

        for (int i = 0; i < size; i++) {
            parent[i] = i; //al inicio, cada elemento esta en su propio conjunto.
        }
    }

    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]); // path compression | hace que todos los nodos visitados apunten directamente a la raiz
        }
        return parent[x]; //si el padre de x no es el mismo, entonces busca el padre (sigue subiendo). busca la raiz del conjunto al que pertenece x
    }

    public boolean union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX == rootY) return false; //si tienen la misma raiz, ya estan en el mismo conjunto, si se une la arista se forma un ciclo

        if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY; //el arbol mas pequeño se conecta debajo del mas grande
        } else if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }

        return true;
    }
}