package photos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginSceneController {

    @FXML
    private Label label;
    private TextField usernameInput;

    //String username = the text in the username textfield


    public void login(ActionEvent event) {
        if (usernameInput.getText().equals("admin")) { //if the user inputs "admin" as the username, then go to AdminScene
            Main mainInstance = new Main();
            mainInstance.switchToAdminScene();
        }
        else { //if the user inputs anything other than admin as the username, then go to UserScene
                Main mainInstance = new Main();
                mainInstance.switchToUserScene();
            }
        }

    

    //if username != admin, then go to UserScene





/* 
    public void initialize() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        label.setText("Hello, JavaFX " + javafxVersion + "\nRunning on Java " + javaVersion + ".");
    }
*/
}
    