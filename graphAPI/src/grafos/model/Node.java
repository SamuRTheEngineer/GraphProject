package grafos.model;

public class Node {

    private final int id;
    private int victims;

    public Node(int id) {
        this.id = id;
        this.victims = 0;
    }

    public int getId() {
        return id;
    }

    public int getVictims() {
        return victims;
    }

    public void setVictims(int victims) {
        this.victims = victims;
    }
}
