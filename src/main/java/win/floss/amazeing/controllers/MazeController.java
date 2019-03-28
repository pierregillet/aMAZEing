package win.floss.amazeing.controllers;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import win.floss.amazeing.models.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Objects;
import java.util.ResourceBundle;

public class MazeController implements Initializable {
    private Maze maze;
    final int maxLevel = 2;

    @FXML
    public GridPane mazeGridpane;

    @FXML
    public Button nextLevelButton;

    class MazeCellHandler implements EventHandler<ActionEvent> {
        private final int id;
        private Maze maze;

        MazeCellHandler(Maze maze, int id) {
            this.maze = maze;
            this.id = id;
        }

        @Override
        public void handle(ActionEvent event) {
            try {
                visitNode(id);
            } catch (NodeNotFoundException e) {
                // This exception can be ignored for now.
            }
        }
    }

    MazeController(Maze maze) {
        this.maze = maze;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Graph graph = maze.getGraph();
        Coordinates startingPoint = maze.getStartingPoint();
        Coordinates endingPoint = maze.getEndingPoint();
        int width = graph.getWidth();
        int height = graph.getHeight();

        if (maxLevel <= this.maze.getLevel()) {
            nextLevelButton.setText("Game won, go back to menu");
        }

        for (int row = 0; row < height; row++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.ALWAYS);
            rowConstraints.setFillHeight(true);
            mazeGridpane.getRowConstraints().add(rowConstraints);
        }

        for (int column = 0; column < width; column++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setHgrow(Priority.ALWAYS);
            columnConstraints.setFillWidth(true);
            mazeGridpane.getColumnConstraints().add(columnConstraints);
        }

        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                int id = row * width + column;
                Cell currentCell = maze.getGraph().searchNodeById(id);
                Graph currentGraph = maze.getGraph();
                ArrayList<Orientation> walls = currentGraph.getWalls(currentCell);
                Button button = new Button();

                String topBorder = walls.contains(Orientation.TOP) ? "2" : "0";
                String rightBorder = walls.contains(Orientation.RIGHT) ? "2" : "0";
                String bottomBorder = walls.contains(Orientation.BOTTOM) ? "2" : "0";
                String leftBorder = walls.contains(Orientation.LEFT) ? "2" : "0";
                String style = "-fx-border-width: " + topBorder + " "
                        + rightBorder + " " + bottomBorder + " "
                        + leftBorder + ";";
                button.setStyle(style);
                if (row == startingPoint.getRowIndex() && column == startingPoint.getColumnIndex()) {
                    button.setId("startingPoint");
                    button.setText("Start");
                } else if (row == endingPoint.getRowIndex() && column == endingPoint.getColumnIndex()) {
                    button.setId("endingPoint");
                    button.setText("End");
                }
                GridPane.setHgrow(button, Priority.ALWAYS);
                GridPane.setVgrow(button, Priority.ALWAYS);
                button.setOnAction(new MazeCellHandler(maze, id));
                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                mazeGridpane.add(button, column, row);
            }
        }
    }

    @FXML
    public void solve() {
        Deque<Cell> path = maze.solve();
        for(Cell cell : path) {
            try {
                Node node = Objects.requireNonNull(getNodeByCoordinates(cell.getCoordinates()));
                node.getStyleClass().add("visited");
                if (node instanceof Button) {
                    ((Button) node).setText("X");
                }
            } catch (NullPointerException ignored) {
                // Only catching a style error, not a critical error.
            }
        }
        nextLevelButton.setDisable(false);
    }

    @FXML
    public void goToNextLevel() {
        int currentLevel = this.maze.getLevel();
        URL url;
        FXMLLoader fxmlLoader = new FXMLLoader();
        if (maxLevel > currentLevel) {
            MazeGeneratorStrategy mazeGeneratorStrategy = new MazeGeneratorDepthFirst();
            MazeSolverStrategy mazeSolverStrategy = new MazeSolverBreadthFirst();
            Maze maze = new Maze(mazeGeneratorStrategy, mazeSolverStrategy, currentLevel + 1);
            MazeController nextController = new MazeController(maze);
            url = getClass().getResource("/win/floss/amazeing/Maze.fxml");
            fxmlLoader.setLocation(url);
            fxmlLoader.setController(nextController); // Create a controller instance
        } else {
            url = getClass().getResource("/win/floss/amazeing/MainMenu.fxml");
            fxmlLoader.setLocation(url);
        }

        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new java.lang.RuntimeException("Could not load fxml file");
        }
        Stage stage = MainApp.getStage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void visitNode(int id) throws NodeNotFoundException {
        Graph graph = maze.getGraph();
        Cell selectedCell = graph.searchNodeById(id);
        if (null == selectedCell) {
            throw new NodeNotFoundException();
        } else {
            ArrayList<Cell> adjacentNodes = graph.getAdjacentNodes(maze.getPathLastElement().getNode());
            if (maze.isInPath(selectedCell)) {
                // if cell already in path, backtrack and remove style
                ArrayList<Cell> removedCells = maze.backtrackPathTo(selectedCell);
                removedCells.forEach(cell -> {
                    Node node = getNodeByCoordinates(cell.getCoordinates());
                    if (null != node) {
                        removeVisitedStyle(node);
                    }
                });
            } else if (adjacentNodes.contains(selectedCell)) {
                // else if it's a node that can be visited next add it to path
                if (selectedCell.getCoordinates().equals(maze.getEndingPoint())) {
                    nextLevelButton.setDisable(false);
                } else {
                    Node node = getNodeByCoordinates(selectedCell.getCoordinates());
                    if (null != node) {
                        setVisitedStyle(node);
                    }
                    maze.addToPath(selectedCell);
                }
            }
        }
    }

    private void setVisitedStyle(Node node) {
        node.getStyleClass().add("visited");
    }

    private void removeVisitedStyle(Node node) {
        node.getStyleClass().removeIf(style -> style.equals("visited"));
    }

    private Node getNodeByCoordinates (final Coordinates coordinates) {
        ObservableList<Node> children = mazeGridpane.getChildren();

        for (Node node : children) {
            if(GridPane.getRowIndex(node).equals(coordinates.getRowIndex())
               && GridPane.getColumnIndex(node).equals(coordinates.getColumnIndex())) {
                return node;
            }
        }
        return null;
    }
}
