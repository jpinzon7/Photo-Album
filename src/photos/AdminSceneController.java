package photos;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AdminSceneController {
    @FXML
    private Label userLabel;

    public void initialize(String username) {
        userLabel.setText("Welcome, " + username + "!");
    }
}
