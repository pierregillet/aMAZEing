package win.floss.amazeing.controllers;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import win.floss.amazeing.models.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MazeController implements Initializable {
    private Maze maze;

    @FXML
    public GridPane mazeGridpane;

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
                maze.visitNode(id);
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
        int width = graph.getWidth();
        int height = graph.getHeight();

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
                NodePosition currentNodePosition = maze.getGraph().searchNodeById(id);
                Graph currentGraph = maze.getGraph();
                ArrayList<Orientation> walls = currentGraph.getWalls(currentNodePosition);
                Button button = new Button();
                String topBorder = walls.contains(Orientation.TOP) ? "1" : "0";
                String rightBorder = walls.contains(Orientation.RIGHT) ? "1" : "0";
                String bottomBorder = walls.contains(Orientation.BOTTOM) ? "1" : "0";
                String leftBorder = walls.contains(Orientation.LEFT) ? "1" : "0";
                String style = "-fx-border-width: " + topBorder + " "
                        + rightBorder + " " + bottomBorder + " "
                        + leftBorder + ";";
                button.setStyle(style);
                button.setOnAction(new MazeCellHandler(maze, id));
                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                mazeGridpane.add(button, column, row);
            }
        }
    }
}
