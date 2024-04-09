package photos.model;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class for the Photo Album application.
 * Starts the application at the login scene.
 * Contains methods to switch to the user scene and the admin scene.
 * @author Maxim Vyshnevsky and Jorge Pinzon
 */

public class Main extends Application {
    
    /** 
     * @param primaryStage
     */
    // starts application at login scene
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/view/LoginScene.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Photo Album");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Main method to launch the application.
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}