package win.floss.amazeing.models;

import java.util.*;

public class MazeGeneratorDepthFirst implements MazeGeneratorStrategy {
    private Graph graph;
    private ArrayDeque<NodePosition> stack = new ArrayDeque<>();
    private ArrayList<Node> unvisitedNodes = new ArrayList<>();
    private Random random = new Random();

    @Override
    public Graph generate(int width, int height) {
        graph = new Graph(width, height);
        graph.getNodes().forEach(unvisitedNodes::addAll); // Passing the 2D array to 1 dimension
        NodePosition startingNodePosition = graph.getNodePosition(0, 0);
        explore(startingNodePosition);
        return graph;
    }

    private void explore(NodePosition startingNodePosition) {
        NodePosition currentNodePosition = startingNodePosition;
        while (!unvisitedNodes.isEmpty()) {
            NodePosition randomNeighbour = getRandomUnvisitedNeighbour(currentNodePosition);
            if (null != randomNeighbour) {
                stack.push(randomNeighbour);
                graph.addEdge(randomNeighbour, currentNodePosition);
                currentNodePosition = randomNeighbour;
                unvisitedNodes.remove(currentNodePosition.getNode());
            } else if (!stack.isEmpty()) {
                currentNodePosition = stack.pop();
            }
        }
    }

    NodePosition getRandomUnvisitedNeighbour(NodePosition nodePosition) {
        ArrayList<NodePosition> neighbouringNodes = graph.getNeighbouringNodePositions(nodePosition);
        while (!neighbouringNodes.isEmpty()) {
            int randomNeighbourIndex = random.nextInt(neighbouringNodes.size());
            NodePosition randomNeighbour = neighbouringNodes.get(randomNeighbourIndex);

            if (!unvisitedNodes.contains(randomNeighbour.getNode())) {
                neighbouringNodes.remove(randomNeighbour);
            } else {
                return randomNeighbour;
            }
        }

        return null;
    }
}
