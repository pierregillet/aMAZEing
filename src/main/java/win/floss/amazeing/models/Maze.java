package win.floss.amazeing.models;

import java.util.ArrayList;

public class Maze {
    private MazeGeneratorStrategy mazeGenerator;
    private Graph graph;
    private int level;
    private ArrayList<Node> path = new ArrayList<>();

    public Maze(MazeGeneratorStrategy mazeGenerator, int level) {
        this.mazeGenerator = mazeGenerator;
        this.level = level;
        this.graph = mazeGenerator.generate(level * 10, level * 10);
    }

    public void visitNode(int id) throws NodeNotFoundException {
        Node node = graph.searchNodeById(id).getNode();
        if (null == node) {
            throw new NodeNotFoundException();
        } else {
            path.add(node);
        }
    }

    public Graph getGraph() {
        return graph;
    }
}
