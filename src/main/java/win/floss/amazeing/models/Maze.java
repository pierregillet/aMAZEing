package win.floss.amazeing.models;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class Maze {
    private MazeGeneratorStrategy mazeGenerator;
    private MazeSolverStrategy mazeSolver;
    private Graph graph;
    private Coordinates startingPoint;
    private Coordinates endingPoint;
    private int level;
    private Deque<Cell> path = new ArrayDeque<>();

    public Maze(MazeGeneratorStrategy mazeGenerator,
                MazeSolverStrategy mazeSolver, int level) {
        this.mazeGenerator = mazeGenerator;
        this.mazeSolver = mazeSolver;
        this.level = level;
        int width = level * 5;
        int height = level * 5;
        startingPoint = new Coordinates(0, 0);
        endingPoint = new Coordinates(height - 1, width - 1);
        graph = mazeGenerator.generate(width, height, startingPoint, endingPoint);
        path.add(graph.getNodePosition(startingPoint));
    }

    public Deque<Cell> solve() {
        return mazeSolver.solve(graph, graph.getNodePosition(startingPoint),
                         graph.getNodePosition(endingPoint));
    }

    public boolean isInPath(Cell cell) {
        return path.contains(cell);
    }

    public void addToPath(Cell cell) {
        path.add(cell);
    }

    public Cell getPathLastElement() {
        return path.peekLast();
    }

    /**
     * @param targetCell Cell until which we want to backtrack
     * @return A list of cells removed from path
     * @throws NodeNotFoundException in case node can't be found in path
     */
    public ArrayList<Cell> backtrackPathTo(Cell targetCell) throws NodeNotFoundException {
        if (path.isEmpty() || !isInPath(targetCell)) {
            throw new NodeNotFoundException();
        }
        ArrayList<Cell> removedCells = new ArrayList<>();
        while (!path.peekLast().equals(targetCell)) {
            removedCells.add(path.pollLast());
        }
        return removedCells;
    }


    public Graph getGraph() {
        return graph;
    }

    public Coordinates getStartingPoint() {
        return startingPoint;
    }

    public Coordinates getEndingPoint() {
        return endingPoint;
    }

    public int getLevel() {
        return level;
    }
}
