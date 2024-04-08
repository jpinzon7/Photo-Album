package photos;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import static photos.Utils.USERS;
import static photos.Utils.saveUsers;

import java.io.IOException;

public class AdminSceneController {
    @FXML
    private Label userLabel;
    @FXML
    private ListView<String> userList; // ListView to display the list of users
    @FXML
    private TextField newUserField; // TextField to input the name of a new user

    public void initialize(String username) {
        userLabel.setText("Welcome, " + username + "!");
        userList.getItems().clear();
        loadUsers();
    }

    // Load the list of users into the ListView
    private void loadUsers() {
        userList.getItems().clear();
        for (User user : USERS) {
            userList.getItems().add(user.getUsername());
        }
    }

    // Create a new user
    public void createUser() {
        String newUsername = newUserField.getText();

        // Check if a user with the same username already exists
        if (USERS.stream().anyMatch(user -> user.getUsername().equals(newUsername))) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText(null);
            alert.setContentText("A user with this username already exists.");

            alert.showAndWait();
            return;
        }

        USERS.add(new User(newUsername));
        saveUsers();
        loadUsers();
    }

    // Delete the selected user
    public void deleteUser() {
        String selectedUsername = userList.getSelectionModel().getSelectedItem();

        if (selectedUsername != null) {
            USERS.removeIf(user -> user.getUsername().equals(selectedUsername));
            saveUsers();
            loadUsers();
        }
    }

   // If user clicks on logout button
    // Go back to the login scene
    public void logout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScene.fxml"));
            Parent root = loader.load();
            Scene loginScene = new Scene(root);

            Stage stage = (Stage) userLabel.getScene().getWindow();
            stage.setScene(loginScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // If user clicks on exit button, exit the program
    public void exitProgram() {
        System.exit(0);
    }
}

