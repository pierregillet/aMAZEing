package win.floss.amazeing.models;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;

public class MazeSolverBreadthFirst implements MazeSolverStrategy {

    @Override
    public Deque<Cell> solve(Graph graph, Cell startingCell,
                             Cell endingCell) {
        Deque<Cell> frontier = new ArrayDeque<>();
        ArrayList<Cell> discoveredNodes = new ArrayList<>();
        HashMap<Cell, Cell> parentNodePosition = new HashMap<>();
        frontier.add(startingCell);

        while (!frontier.isEmpty()) {
            Cell currentCell = frontier.pop();
            if (currentCell.equals(endingCell)) {
                Deque<Cell> path = new ArrayDeque<>();
                currentCell = parentNodePosition.get(currentCell);
                while (!currentCell.equals(startingCell)) {
                    path.addFirst(currentCell);
                    currentCell = parentNodePosition.get(currentCell);
                }
                return path;
            }
            ArrayList<Cell> adjacentNodes = graph.getAdjacentNodes(currentCell.getNode());
            for (Cell adjacentCell : adjacentNodes) {
                if (!discoveredNodes.contains(adjacentCell)) {
                    discoveredNodes.add(adjacentCell);
                    parentNodePosition.put(adjacentCell, currentCell);
                    frontier.add(adjacentCell);
                }
            }
        }
        return null;
    }
}
