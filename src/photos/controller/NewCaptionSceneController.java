package photos.controller;

import static photos.controller.Utils.saveUsers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import photos.model.Photo;

/**
 * This class controls the new caption scene.
 * Allows the user to set a new caption for a photo.
 * Used in the Photo Display Scene when the user wants to set a new caption for a photo.
 * Contains methods to allow user to set a new caption for a photo or cancel the operation.
 * @author Maxim Vyshnevsky and Jorge Pinzon
 */
public class NewCaptionSceneController {
    @FXML
    private TextArea textArea;

    private Photo photo;

    
    /** 
     * Initializes the new caption scene with the photo.
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
