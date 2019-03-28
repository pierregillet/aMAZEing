package win.floss.amazeing.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

public class MazeGeneratorDepthFirst implements MazeGeneratorStrategy {
    private Graph graph;
    private ArrayList<Node> stack = new ArrayList<>();
    private ArrayList<Node> unvisitedNodes = new ArrayList<>();
    private Random random = new Random();

    @Override
    public Graph generate(int width, int height) {
        graph = new Graph(width, height);
        graph.getNodes().forEach(unvisitedNodes::addAll); // Passing the 2D array to 1 dimension

//        Coordinates startPoint = new Coordinates(0, 0);
//        Coordinates endPoint = new Coordinates(height, width);

        int generatorStartingPoint = random.nextInt(unvisitedNodes.size());
        NodePosition currentNodePosition = graph.getNodePosition(unvisitedNodes.get(generatorStartingPoint));

        explore(currentNodePosition);

        return graph;
    }

    void explore(NodePosition currentNodePosition) {
        unvisitedNodes.remove(currentNodePosition.getNode());
        NodePosition randomNeighbour = getRandomUnvisitedNeighbour(currentNodePosition);
        while (null != randomNeighbour) {
            graph.addEdge(randomNeighbour, currentNodePosition);
            unvisitedNodes.remove(currentNodePosition.getNode());
            stack.add(currentNodePosition.getNode());
            explore(randomNeighbour);
            randomNeighbour = getRandomUnvisitedNeighbour(currentNodePosition);
        }
        return;
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
