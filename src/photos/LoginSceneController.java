package photos;

import java.util.*;

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
import java.io.*;

public class LoginSceneController {

    @FXML
    private Label label;
    @FXML
    private TextField usernameInput;
    // String username = the text in the username textfield

    public void login(ActionEvent event) {
        String username = usernameInput.getText();
        if (username.equals("admin")) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminScene.fxml"));
                Parent root = loader.load();
                Scene adminScene = new Scene(root);

                // Get the UserSceneController and set the username
                AdminSceneController adminSceneController = loader.getController();
                adminSceneController.initialize(username);

                // Get the current stage and set the new scene on it
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(adminScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (username.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Username Required");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a username.");
            alert.showAndWait();
        } else {
            try {
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
