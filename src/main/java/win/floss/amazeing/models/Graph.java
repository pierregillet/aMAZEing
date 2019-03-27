package win.floss.amazeing.models;

import java.util.HashMap;
import java.util.Vector;

public class Graph {
    private Vector<Vector<Node>> nodes;
    private Vector<HashMap<Node, Vector<Node>>> adjacencyList;

    Graph(int width, int height) {
        this.adjacencyList = new Vector<>();
        this.nodes = new Vector<>(height);
        for (int i = 0; i < height; i++) {
            Vector<Node> row = new Vector<>(width);
            for (int j = 0; j < width; j++) {
                row.add(j, new Node(i * width + j));
            }
            this.nodes.add(i, row);
        }
    }

//    Vector<Orientation> getWalls(Node node) {
//        return null;
//    }

    NodePosition searchNodeById(int id) {
        for (int rowIndex = 0; rowIndex < nodes.size(); rowIndex++) {
            Vector<Node> row = nodes.get(rowIndex);
            for (int columnIndex = 0; columnIndex < row.size(); columnIndex++) {
                Node node = row.get(columnIndex);
                if (node.getId() == id) {
                    return new NodePosition(node, rowIndex, columnIndex);
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

    Vector<HashMap<Node, Vector<Node>>> getAdjacencyList() {
        return adjacencyList;
    }

    public int getHeight() {
        return nodes.size();
    }

    public int getWidth() {
        return nodes.get(0).size();
    }
}
