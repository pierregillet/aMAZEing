package win.floss.amazeing.models;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;

public class MazeSolverBreadthFirst implements MazeSolverStrategy {

    @Override
    public Deque<NodePosition> solve(Graph graph, NodePosition startingNodePosition,
                                         NodePosition endingNodePosition) {
        Deque<NodePosition> frontier = new ArrayDeque<>();
        ArrayList<NodePosition> discoveredNodes = new ArrayList<>();
        HashMap<NodePosition, NodePosition> parentNodePosition = new HashMap<>();
        frontier.add(startingNodePosition);

        while (!frontier.isEmpty()) {
            NodePosition currentNodePosition = frontier.pop();
            if (currentNodePosition.equals(endingNodePosition)) {
                Deque<NodePosition> path = new ArrayDeque<>();
                currentNodePosition = parentNodePosition.get(currentNodePosition);
                while (!currentNodePosition.equals(startingNodePosition)) {
                    path.addFirst(currentNodePosition);
                    currentNodePosition = parentNodePosition.get(currentNodePosition);
                }
                return path;
            }
            ArrayList<NodePosition> adjacentNodes = graph.getAdjacentNodes(currentNodePosition.getNode());
            for (NodePosition adjacentNodePosition : adjacentNodes) {
                if (!discoveredNodes.contains(adjacentNodePosition)) {
                    discoveredNodes.add(adjacentNodePosition);
                    parentNodePosition.put(adjacentNodePosition, currentNodePosition);
                    frontier.add(adjacentNodePosition);
                }
            }
        }
        return null;
    }
}
