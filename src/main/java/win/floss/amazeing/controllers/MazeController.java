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
import win.floss.amazeing.models.NodeNotFoundException;
import win.floss.amazeing.models.Graph;
import win.floss.amazeing.models.Maze;

import java.net.URL;
import java.util.ResourceBundle;

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

//        this.mazeGridpane.setGridLinesVisible(true);

        for (int row = 0; row < height; row++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.ALWAYS) ; // allow row to grow
            rowConstraints.setFillHeight(true); // ask nodes to fill height for row
            mazeGridpane.getRowConstraints().add(rowConstraints);
        }

        for (int column = 0; column < width; column++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setHgrow(Priority.ALWAYS) ; // allow row to grow
            columnConstraints.setFillWidth(true); // ask nodes to fill height for row
            mazeGridpane.getColumnConstraints().add(columnConstraints);
        }

        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                Button button = new Button();
                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                mazeGridpane.add(button, column, row);
            }
        }
    }
}
