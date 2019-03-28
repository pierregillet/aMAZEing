package win.floss.amazeing.models;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph {
    private ArrayList<ArrayList<Node>> nodes;
    private HashMap<Node, ArrayList<NodePosition>> adjacencyList;

    Graph(int width, int height) {
        nodes = new ArrayList<>(height);
        for (int i = 0; i < height; i++) {
            ArrayList<Node> row = new ArrayList<>(width);
            for (int j = 0; j < width; j++) {
                row.add(j, new Node(i * width + j));
            }
            this.nodes.add(i, row);
        }

        adjacencyList = new HashMap<>(width * height);
        for (ArrayList<Node> row : nodes) {
            for (Node node : row) {
                adjacencyList.put(node, new ArrayList<>());
            }
        }
    }

    public ArrayList<Orientation> getWalls(NodePosition nodePosition) {
        ArrayList<Orientation> walls = new ArrayList<>();

        Node currentNode = nodePosition.getNode();
        int rowIndex = nodePosition.getCoordinates().getRowIndex();
        int columnIndex = nodePosition.getCoordinates().getColumnIndex();

        if (0 == rowIndex) {
            walls.add(Orientation.TOP);
        } else {
            NodePosition topNode = getNodePosition(rowIndex - 1, columnIndex);
            ArrayList<NodePosition> adjacentNodes = getAdjacentNodes(currentNode);
            if (null != adjacentNodes && !adjacentNodes.contains(topNode)) {
                walls.add(Orientation.TOP);
            }
        }

        if (nodes.get(rowIndex).size() - 1 == columnIndex) {
            walls.add(Orientation.RIGHT);
        } else {
            NodePosition rightNode = getNodePosition(rowIndex, columnIndex + 1);
            ArrayList<NodePosition> adjacentNodes = getAdjacentNodes(currentNode);
            if (null != adjacentNodes && !adjacentNodes.contains(rightNode)) {
                walls.add(Orientation.RIGHT);
            }
        }

        if (nodes.size() - 1 == rowIndex) {
            walls.add(Orientation.BOTTOM);
        } else {
            NodePosition bottomNode = getNodePosition(rowIndex + 1, columnIndex);
            ArrayList<NodePosition> adjacentNodes = getAdjacentNodes(currentNode);
            if (null != adjacentNodes && !adjacentNodes.contains(bottomNode)) {
                walls.add(Orientation.BOTTOM);
            }
        }

        if (0 == columnIndex) {
            walls.add(Orientation.LEFT);
        } else {
            NodePosition leftNode = getNodePosition(rowIndex, columnIndex - 1);
            ArrayList<NodePosition> adjacentNodes = getAdjacentNodes(currentNode);
            if (null != adjacentNodes && !adjacentNodes.contains(leftNode)) {
                walls.add(Orientation.LEFT);
            }
        }

        return walls;
    }

    public NodePosition getNodePosition(int rowIndex, int columnIndex) {
        Node node = nodes.get(rowIndex).get(columnIndex);
        return new NodePosition(node, rowIndex, columnIndex);
    }

    public NodePosition getNodePosition(Node node) {
        for (int rowIndex = 0; rowIndex < nodes.size(); rowIndex++) {
            ArrayList<Node> row = nodes.get(rowIndex);
            for (int columnIndex = 0; columnIndex < row.size(); columnIndex++) {
                Node currentNode = row.get(columnIndex);
                if (node.equals(currentNode)) {
                    return new NodePosition(currentNode, new Coordinates(rowIndex, columnIndex));
                }
            }
        }
        return null;
    }

    public NodePosition searchNodeById(int id) {
        for (int rowIndex = 0; rowIndex < nodes.size(); rowIndex++) {
            ArrayList<Node> row = nodes.get(rowIndex);
            for (int columnIndex = 0; columnIndex < row.size(); columnIndex++) {
                Node node = row.get(columnIndex);
                if (node.getId() == id) {
                    return new NodePosition(node, new Coordinates(rowIndex, columnIndex));
                }
            }
        }
        return null;
    }

    public ArrayList<NodePosition> getAdjacentNodes(Node node) {
        return adjacencyList.get(node);
    }

    public ArrayList<NodePosition> getNeighbouringNodePositions(NodePosition nodePosition) {
        ArrayList<NodePosition> neighbouringNodes = new ArrayList<>();

        int rowIndex = nodePosition.getCoordinates().getRowIndex();
        int columnIndex = nodePosition.getCoordinates().getColumnIndex();

        if (0 != rowIndex) {
            int topRowIndex = rowIndex - 1;
            Node topNode = nodes.get(topRowIndex).get(columnIndex);
            neighbouringNodes.add(new NodePosition(topNode, new Coordinates(topRowIndex, columnIndex)));
        }

        if (nodes.get(rowIndex).size() - 1 != columnIndex) {
            int rightColumnIndex = columnIndex + 1;
            Node rightNode = nodes.get(rowIndex).get(rightColumnIndex);
            neighbouringNodes.add(new NodePosition(rightNode, new Coordinates(rowIndex, rightColumnIndex)));
        }

        if (nodes.size() - 1 != rowIndex) {
            int bottomRowIndex = rowIndex + 1;
            Node bottomNode = nodes.get(bottomRowIndex).get(columnIndex);
            neighbouringNodes.add(new NodePosition(bottomNode, new Coordinates(bottomRowIndex, columnIndex)));
        }

        if (0 != columnIndex) {
            int leftColumnIndex = columnIndex - 1;
            Node leftNode = nodes.get(rowIndex).get(leftColumnIndex);
            neighbouringNodes.add(new NodePosition(leftNode, new Coordinates(rowIndex, leftColumnIndex)));
        }

        return neighbouringNodes;
    }

    public void addEdge(NodePosition firstNodePosition, NodePosition secondNodePosition) {
        adjacencyList.get(firstNodePosition.getNode()).add(secondNodePosition);
        adjacencyList.get(secondNodePosition.getNode()).add(firstNodePosition);
    }


    public ArrayList<ArrayList<Node>> getNodes() {
        return nodes;
    }

    public HashMap<Node, ArrayList<NodePosition>> getAdjacencyList() {
        return adjacencyList;
    }

    public int getHeight() {
        return nodes.size();
    }

    public int getWidth() {
        return nodes.get(0).size();
    }
}
