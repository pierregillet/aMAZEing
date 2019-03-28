package win.floss.amazeing.models;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodePosition that = (NodePosition) o;
        return node.equals(that.node) &&
               coordinates.equals(that.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(node, coordinates);
    }

    public Node getNode() {
        return node;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }
}
