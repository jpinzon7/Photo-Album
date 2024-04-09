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
    // starts application at login scene
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScene.fxml"));
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
     * Switches to the user scene.
     * Called when the user logs in as a regular user.
     * Loads the UserScene and sets it on a new stage.
     * The user's username is passed to the UserSceneController.
     * The new stage is shown.
     */
    public void switchToUserScene() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("UserScene.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("___'s Photo Album");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Switches to the admin scene.
     * Called when the user logs in as an admin.
     * Loads the AdminScene and sets it on a new stage.
     * The new stage is shown.
     */
    public void switchToAdminScene() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("AdminScene.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Admin Photo Album");
            stage.setScene(scene);
            stage.show();
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