package win.floss.amazeing.models;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph {
    private ArrayList<ArrayList<Node>> nodes;
    private HashMap<Node, ArrayList<Node>> adjacencyList;

    Graph(int width, int height) {
        this.adjacencyList = new HashMap<>();
        this.nodes = new ArrayList<>(height);
        for (int i = 0; i < height; i++) {
            ArrayList<Node> row = new ArrayList<>(width);
            for (int j = 0; j < width; j++) {
                row.add(j, new Node(i * width + j));
            }
            this.nodes.add(i, row);
        }
    }

    public ArrayList<Orientation> getWalls(NodePosition nodePosition) {
        ArrayList<Orientation> walls = new ArrayList<>();

        Node currentNode = nodePosition.getNode();
        int rowIndex = nodePosition.getRowIndex();
        int columnIndex = nodePosition.getColumnIndex();

        if (0 == rowIndex) {
            walls.add(Orientation.TOP);
        } else {
            Node topNode = nodes.get(rowIndex - 1).get(columnIndex);
            ArrayList<Node> adjacentNodes = getAdjacentNodes(currentNode);
            if (null != adjacentNodes && !adjacentNodes.contains(topNode)) {
                walls.add(Orientation.TOP);
            }
        }

        if (nodes.size() - 1 == rowIndex) {
            walls.add(Orientation.BOTTOM);
        } else {
            Node bottomNode = nodes.get(rowIndex + 1).get(columnIndex);
            ArrayList<Node> adjacentNodes = getAdjacentNodes(currentNode);
            if (null != adjacentNodes && !adjacentNodes.contains(bottomNode)) {
                walls.add(Orientation.BOTTOM);
            }
        }

        if (0 == columnIndex) {
            walls.add(Orientation.LEFT);
        } else {
            Node leftNode = nodes.get(rowIndex).get(columnIndex - 1);
            ArrayList<Node> adjacentNodes = getAdjacentNodes(currentNode);
            if (null != adjacentNodes && !adjacentNodes.contains(leftNode)) {
                walls.add(Orientation.LEFT);
            }
        }

        if (nodes.get(rowIndex).size() - 1 == columnIndex) {
            walls.add(Orientation.RIGHT);
        } else {
            Node rightNode = nodes.get(rowIndex).get(columnIndex + 1);
            ArrayList<Node> adjacentNodes = getAdjacentNodes(currentNode);
            if (null != adjacentNodes && !adjacentNodes.contains(rightNode)) {
                walls.add(Orientation.RIGHT);
            }
        }

        return walls;
    }

    public NodePosition searchNodeById(int id) {
        for (int rowIndex = 0; rowIndex < nodes.size(); rowIndex++) {
            ArrayList<Node> row = nodes.get(rowIndex);
            for (int columnIndex = 0; columnIndex < row.size(); columnIndex++) {
                Node node = row.get(columnIndex);
                if (node.getId() == id) {
                    return new NodePosition(node, rowIndex, columnIndex);
                }
            }
        }
        return null;
    }

    public ArrayList<Node> getAdjacentNodes(Node node) {
        return adjacencyList.get(node);
    }

    public ArrayList<ArrayList<Node>> getNodes() {
        return nodes;
    }

    public HashMap<Node, ArrayList<Node>> getAdjacencyList() {
        return adjacencyList;
    }

    public int getHeight() {
        return nodes.size();
    }

    public int getWidth() {
        return nodes.get(0).size();
    }
}
