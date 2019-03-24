package win.floss.amazeing.controllers;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import win.floss.amazeing.helpers.NodeNotFoundException;
import win.floss.amazeing.models.Graph;
import win.floss.amazeing.models.Maze;
import win.floss.amazeing.models.Node;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

public class MazeController implements Initializable {
    private Maze maze;

    @FXML
    public GridPane mazeGridpane;

    class MazeCellHandler implements EventHandler<ActionEvent> {
        private final int id;

        MazeCellHandler(int id) {
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
            for (int column = 0; column < width; column++) {
                Button button = new Button();
                mazeGridpane.add(button, column, row);
            }
        }
    }
}
