package win.floss.amazeing.models;

import java.util.*;

public class MazeGeneratorDepthFirst implements MazeGeneratorStrategy {
    private Graph graph;
    private ArrayDeque<Cell> stack = new ArrayDeque<>();
    private ArrayList<Node> unvisitedNodes = new ArrayList<>();
    private Random random = new Random();

    @Override
    public Graph generate(int width, int height, Coordinates startingPoint, Coordinates endingPoint) {
        graph = new Graph(width, height);
        graph.getNodes().forEach(unvisitedNodes::addAll); // Passing the 2D array to 1 dimension
        Cell startingCell = graph.getNodePosition(startingPoint);
        explore(startingCell);
        return graph;
    }

    private void explore(Cell startingCell) {
        Cell currentCell = startingCell;
        while (!unvisitedNodes.isEmpty()) {
            Cell randomNeighbour = getRandomUnvisitedNeighbour(currentCell);
            if (null != randomNeighbour) {
                stack.push(randomNeighbour);
                graph.addEdge(randomNeighbour, currentCell);
                currentCell = randomNeighbour;
                unvisitedNodes.remove(currentCell.getNode());
            } else if (!stack.isEmpty()) {
                currentCell = stack.pop();
            }
        }
    }

    private Cell getRandomUnvisitedNeighbour(Cell cell) {
        ArrayList<Cell> neighbouringNodes = graph.getNeighbouringNodePositions(cell);
        while (!neighbouringNodes.isEmpty()) {
            int randomNeighbourIndex = random.nextInt(neighbouringNodes.size());
            Cell randomNeighbour = neighbouringNodes.get(randomNeighbourIndex);

            if (!unvisitedNodes.contains(randomNeighbour.getNode())) {
                neighbouringNodes.remove(randomNeighbour);
            } else {
                return randomNeighbour;
            }
        }

        return null;
    }
}
