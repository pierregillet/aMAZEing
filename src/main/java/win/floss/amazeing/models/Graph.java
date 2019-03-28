package win.floss.amazeing.models;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph {
    private ArrayList<ArrayList<Node>> nodes;
    private HashMap<Node, ArrayList<Cell>> adjacencyList;

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

    public ArrayList<Orientation> getWalls(Cell cell) {
        ArrayList<Orientation> walls = new ArrayList<>();

        Node currentNode = cell.getNode();
        int rowIndex = cell.getCoordinates().getRowIndex();
        int columnIndex = cell.getCoordinates().getColumnIndex();

        if (0 == rowIndex) {
            walls.add(Orientation.TOP);
        } else {
            Cell topNode = getNodePosition(rowIndex - 1, columnIndex);
            ArrayList<Cell> adjacentNodes = getAdjacentNodes(currentNode);
            if (null != adjacentNodes && !adjacentNodes.contains(topNode)) {
                walls.add(Orientation.TOP);
            }
        }

        if (nodes.get(rowIndex).size() - 1 == columnIndex) {
            walls.add(Orientation.RIGHT);
        } else {
            Cell rightNode = getNodePosition(rowIndex, columnIndex + 1);
            ArrayList<Cell> adjacentNodes = getAdjacentNodes(currentNode);
            if (null != adjacentNodes && !adjacentNodes.contains(rightNode)) {
                walls.add(Orientation.RIGHT);
            }
        }

        if (nodes.size() - 1 == rowIndex) {
            walls.add(Orientation.BOTTOM);
        } else {
            Cell bottomNode = getNodePosition(rowIndex + 1, columnIndex);
            ArrayList<Cell> adjacentNodes = getAdjacentNodes(currentNode);
            if (null != adjacentNodes && !adjacentNodes.contains(bottomNode)) {
                walls.add(Orientation.BOTTOM);
            }
        }

        if (0 == columnIndex) {
            walls.add(Orientation.LEFT);
        } else {
            Cell leftNode = getNodePosition(rowIndex, columnIndex - 1);
            ArrayList<Cell> adjacentNodes = getAdjacentNodes(currentNode);
            if (null != adjacentNodes && !adjacentNodes.contains(leftNode)) {
                walls.add(Orientation.LEFT);
            }
        }

        return walls;
    }

    public Cell getNodePosition(int rowIndex, int columnIndex) {
        Node node = nodes.get(rowIndex).get(columnIndex);
        return new Cell(node, rowIndex, columnIndex);
    }

    public Cell getNodePosition(Coordinates coordinates) {
        Node currentNode = nodes.get(coordinates.getRowIndex()).get(coordinates.getColumnIndex());
        return new Cell(currentNode, coordinates);
    }

    public Cell searchNodeById(int id) {
        for (int rowIndex = 0; rowIndex < nodes.size(); rowIndex++) {
            ArrayList<Node> row = nodes.get(rowIndex);
            for (int columnIndex = 0; columnIndex < row.size(); columnIndex++) {
                Node node = row.get(columnIndex);
                if (node.getId() == id) {
                    return new Cell(node, new Coordinates(rowIndex, columnIndex));
                }
            }
        }
        return null;
    }

    public ArrayList<Cell> getAdjacentNodes(Node node) {
        return adjacencyList.get(node);
    }

    public ArrayList<Cell> getNeighbouringNodePositions(Cell cell) {
        ArrayList<Cell> neighbouringNodes = new ArrayList<>();

        int rowIndex = cell.getCoordinates().getRowIndex();
        int columnIndex = cell.getCoordinates().getColumnIndex();

        if (0 != rowIndex) {
            int topRowIndex = rowIndex - 1;
            Node topNode = nodes.get(topRowIndex).get(columnIndex);
            neighbouringNodes.add(new Cell(topNode, new Coordinates(topRowIndex, columnIndex)));
        }

        if (nodes.get(rowIndex).size() - 1 != columnIndex) {
            int rightColumnIndex = columnIndex + 1;
            Node rightNode = nodes.get(rowIndex).get(rightColumnIndex);
            neighbouringNodes.add(new Cell(rightNode, new Coordinates(rowIndex, rightColumnIndex)));
        }

        if (nodes.size() - 1 != rowIndex) {
            int bottomRowIndex = rowIndex + 1;
            Node bottomNode = nodes.get(bottomRowIndex).get(columnIndex);
            neighbouringNodes.add(new Cell(bottomNode, new Coordinates(bottomRowIndex, columnIndex)));
        }

        if (0 != columnIndex) {
            int leftColumnIndex = columnIndex - 1;
            Node leftNode = nodes.get(rowIndex).get(leftColumnIndex);
            neighbouringNodes.add(new Cell(leftNode, new Coordinates(rowIndex, leftColumnIndex)));
        }

        return neighbouringNodes;
    }

    public void addEdge(Cell firstCell, Cell secondCell) {
        adjacencyList.get(firstCell.getNode()).add(secondCell);
        adjacencyList.get(secondCell.getNode()).add(firstCell);
    }


    public ArrayList<ArrayList<Node>> getNodes() {
        return nodes;
    }

    public HashMap<Node, ArrayList<Cell>> getAdjacencyList() {
        return adjacencyList;
    }

    public int getHeight() {
        return nodes.size();
    }

    public int getWidth() {
        return nodes.get(0).size();
    }
}
