package win.floss.amazeing.models;

public class NodePosition {
    private Node node;
    private int rowIndex;
    private int columnIndex;

    public NodePosition(Node node, int rowIndex, int columnIndex) {
        this.node = node;
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }

    public Node getNode() {
        return node;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }
}
