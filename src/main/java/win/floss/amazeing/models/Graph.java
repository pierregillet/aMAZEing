package win.floss.amazeing.models;

import java.util.HashSet;
import java.util.Vector;

public class Graph {
    private Vector<Vector<Node>> nodes;
    private HashSet<Edge> adjacencyList;

    Graph(int width, int height) {
        this.adjacencyList = new HashSet<>();
        this.nodes = new Vector<>(height);
        for (int i = 0; i < height; i++) {
            Vector<Node> row = new Vector<>(width);
            for (int j = 0; j < width; j++) {
                row.add(j, new Node(i * width + j));
            }
            this.nodes.add(i, row);
        }
    }

    public Node searchNodeById(int id) {
        for (Vector<Node> row : nodes) {
            for (Node node : row) {
                if (node.getId() == id) {
                    return node;
                }
            }
        }
        return null;
    }

    public Vector<Vector<Node>> getNodes() {
        return nodes;
    }

    void setNodes(Vector<Vector<Node>> nodes) {
        this.nodes = nodes;
    }

    HashSet<Edge> getAdjacencyList() {
        return adjacencyList;
    }

    public int getHeight() {
        return nodes.size();
    }

    public int getWidth() {
        return nodes.get(0).size();
    }
}
