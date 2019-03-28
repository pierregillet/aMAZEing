package win.floss.amazeing.models;

public class NodePosition {
    private Node node;
    private Coordinates coordinates;

    public NodePosition(Node node, Coordinates coordinates) {
        this.node = node;
        this.coordinates = coordinates;
    }

    public NodePosition(Node node, int row, int column) {
        this.node = node;
        this.coordinates = new Coordinates(row, column);
    }

    public Node getNode() {
        return node;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }
}
