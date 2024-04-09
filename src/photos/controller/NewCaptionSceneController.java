package photos.controller;

import static photos.controller.Utils.saveUsers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import photos.model.Photo;

public class NewCaptionSceneController {
    @FXML
    private TextArea textArea;

    private Photo photo;

    
    /** 
     * @param photo
     */
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
