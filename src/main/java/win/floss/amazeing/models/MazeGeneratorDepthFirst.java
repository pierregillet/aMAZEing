package win.floss.amazeing.models;

import java.util.ArrayList;

public class MazeGeneratorDepthFirst implements MazeGeneratorStrategy {
    @Override
    public Graph generate(int width, int height) {
        Graph graph = new Graph(width, height);
        ArrayList<ArrayList<Node>> notVisited = graph.getNodes();



        return graph;
    }
}
