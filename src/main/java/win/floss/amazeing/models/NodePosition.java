package win.floss.amazeing.models;

public class NodePosition {
    private Node node;
    private long row;
    private long column;

    public NodePosition(Node node, long row, long column) {
        this.node = node;
        this.row = row;
        this.column = column;
    }

    public Node getNode() {
        return node;
    }

    public long getRow() {
        return row;
    }

    public long getColumn() {
        return column;
    }
}
