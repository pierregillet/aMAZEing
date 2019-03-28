package win.floss.amazeing.models;

import java.util.ArrayList;
import java.util.Deque;

public class Maze {
    private MazeGeneratorStrategy mazeGenerator;
    private MazeSolverStrategy mazeSolver;
    private Graph graph;
    private Coordinates startingPoint;
    private Coordinates endingPoint;
    private int level;
    private ArrayList<Node> path = new ArrayList<>();

    public Maze(MazeGeneratorStrategy mazeGenerator,
                MazeSolverStrategy mazeSolver, int level) {
        this.mazeGenerator = mazeGenerator;
        this.mazeSolver = mazeSolver;
        this.level = level;
        int width = level * 10;
        int height = level * 10;
        this.startingPoint = new Coordinates(0, 0);
        this.endingPoint = new Coordinates(height - 1, width - 1);
        this.graph = mazeGenerator.generate(width, height, startingPoint, endingPoint);
    }

    public void visitNode(int id) throws NodeNotFoundException {
        Node node = graph.searchNodeById(id).getNode();
        if (null == node) {
            throw new NodeNotFoundException();
        } else {
            path.add(node);
        }
    }

    public Deque<NodePosition> solve() {
        return mazeSolver.solve(graph, graph.getNodePosition(startingPoint),
                         graph.getNodePosition(endingPoint));
    }

    public Graph getGraph() {
        return graph;
    }
}
