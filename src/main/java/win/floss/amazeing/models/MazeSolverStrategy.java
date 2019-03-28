package win.floss.amazeing.models;

import java.util.Deque;

public interface MazeSolverStrategy {
    Deque<Cell> solve(Graph graph, Cell startingCell,
                      Cell endingCell);
}
