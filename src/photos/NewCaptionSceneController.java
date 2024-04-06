package photos;

import static photos.Utils.saveUsers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class NewCaptionSceneController {
    @FXML
    private TextArea textArea;

    private Photo photo;

    public void initialize(Photo photo) {
        this.photo = photo;
    }

    public void setCaption() {
        photo.setCaption(textArea.getText());
        textArea.getScene().getWindow().hide();
        saveUsers();
    }

    public void cancel() {
        textArea.getScene().getWindow().hide();
    }
}
