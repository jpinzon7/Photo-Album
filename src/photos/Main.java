package photos;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    //starts application at login scene
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("LoginScene.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Photo Album");
            primaryStage.setScene(scene);
            primaryStage.show();
        }

        catch (IOException e) {
        }

    }

    //method for switching to the user scene
    public void switchToUserScene() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("UserScene.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("___'s Photo Album");
            stage.setScene(scene);
            stage.show();
        }

        catch (IOException e) {
        }
    }

    //method for switching to the admin scene
    public void switchToAdminScene() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("AdminScene.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Admin Photo Album");
            stage.setScene(scene);
            stage.show();
        }

        catch (IOException e) {
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}