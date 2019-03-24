package win.floss.amazeing.models;

import win.floss.amazeing.helpers.NodeNotFoundException;

import java.util.Vector;

public class Maze {
    private Graph graph;
    private int level;
    private Vector<Node> path = new Vector<>();

    public Maze(int level) {
        this.level = level;
        this.graph = new Graph(level * 10, level * 10);
    }

    public void visitNode(int id) throws NodeNotFoundException {
        Node node = graph.searchNodeById(id);
        if (node == null) {
            throw new NodeNotFoundException();
        }
    }

    public Graph getGraph() {
        return graph;
    }
}
