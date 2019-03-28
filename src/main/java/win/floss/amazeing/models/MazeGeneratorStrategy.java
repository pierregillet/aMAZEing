package win.floss.amazeing.models;

public interface MazeGeneratorStrategy {
    Graph generate(int width, int height, Coordinates startingPoint, Coordinates endingPoint);
}
