package win.floss.amazeing.models;

import java.util.ArrayList;
import java.util.Deque;

public interface MazeSolverStrategy {
    Deque<NodePosition> solve(Graph graph, NodePosition startingNodePosition,
                              NodePosition endingNodePosition);
}
