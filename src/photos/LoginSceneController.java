package photos;

import java.io.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;


/**
 * Controller for the login scene.
 * @author Jorge Pinzon, Maxim Vyshnevsky
 */
public class LoginSceneController {
    @FXML
    private Label label;
    @FXML
    private TextField usernameInput;

    // Runs when the login button is clicked
    public void login(ActionEvent event) {
        String username = usernameInput.getText(); // Get the username from the text field
        if (username.equals("admin")) { // If the username is admin, go to the admin scene
            try {
                // Load the AdminScene
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminScene.fxml"));
                Parent root = loader.load();
                Scene adminScene = new Scene(root);

                // Get the AdminSceneController and set the username
                AdminSceneController adminSceneController = loader.getController();
                adminSceneController.initialize(username);

                // Get the current stage and set the new scene on it
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(adminScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (username.isEmpty()) { // Username cannot be empty, show an alert
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Username Required");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a username.");
            alert.showAndWait();
        } else { // If the username is not empty and not admin, go to the user scene
            try {
                // Load the UserScene
                FXMLLoader loader = new FXMLLoader(getClass().getResource("UserScene.fxml"));
                Parent root = loader.load();
                Scene userScene = new Scene(root);

                // Get the UserSceneController and set the username
                UserSceneController userSceneController = loader.getController();
                userSceneController.initialize(username);

                // Get the current stage and set the new scene on it
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(userScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void exitProgram() {
        System.exit(0);
    }
}

// if username != admin, then go to UserScene

/*
 * public void initialize() {
 * String javaVersion = System.getProperty("java.version");
 * String javafxVersion = System.getProperty("javafx.version");
 * label.setText("Hello, JavaFX " + javafxVersion + "\nRunning on Java " +
 * javaVersion + ".");
 * }
 */
