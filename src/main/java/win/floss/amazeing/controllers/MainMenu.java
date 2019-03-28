package win.floss.amazeing.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import win.floss.amazeing.models.*;

import java.io.IOException;
import java.net.URL;

public class MainMenu {

    @FXML
    public void startGame(ActionEvent event) {
        MazeGeneratorStrategy mazeGeneratorStrategy = new MazeGeneratorDepthFirst();
        MazeSolverStrategy mazeSolverStrategy = new MazeSolverBreadthFirst();
        Maze maze = new Maze(mazeGeneratorStrategy, mazeSolverStrategy, 1);
        MazeController controller = new MazeController(maze);
        URL url = getClass().getResource("/win/floss/amazeing/Maze.fxml");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(url);
        fxmlLoader.setController(controller);// Create a controller instance

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
}
