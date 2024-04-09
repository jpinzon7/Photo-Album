package photos.controller;

import static photos.controller.Utils.saveUsers;

import java.util.List;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import photos.model.Photo;
import photos.model.Tag;
import javafx.scene.control.ButtonBar;

/**
 * The controller class for the New Tag Scene.
 * This class handles the logic and user interactions for adding new tags to a photo.
 * @author Maxim Vyshnevsky and Jorge Pinzon
 */
public class NewTagSceneController {
    @FXML
    TextField locationTextField;
    @FXML
    TextField personTextField;
    @FXML
    TextField customTagName;
    @FXML
    TextField customTagValue;

    private Photo photo;


    
    /** 
     * Initializes the New Tag Scene with the photo.
     * @param photo
     */
    public void initialize(Photo photo) {
        this.photo = photo;
    }

    /**
     * Adds a location tag to the photo.
     * If the photo already has a location tag, the user is prompted to confirm the replacement of the location tag.
     * If the user confirms, the location tag is replaced with the new location.
     * If the user cancels, the location tag is not replaced.
     * If the photo does not have a location tag, a new location tag is added to the photo.
     */
    public void addLocationTag() {
        String location = locationTextField.getText();
        List<Tag> tags = photo.getTags();
        if (tags.stream().anyMatch(tag -> tag.getTagName().equals("location"))) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Location Tag");
            alert.setHeaderText("You already have a location tag");
            alert.setContentText(
                    "If you choose to proceed, your location tag value will be replaced with the new location.");

            ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType proceedBtn = new ButtonType("Proceed");

            alert.getButtonTypes().setAll(cancelBtn, proceedBtn);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == proceedBtn) {
                // Replace the location tag with the new location
                tags.remove(tags.stream().filter(tag -> tag.getTagName().equals("location")).findFirst().get());
                Tag newLocationTag = new Tag("location", location);
                tags.add(newLocationTag);
                saveUsers();
            }
        } else {
            Tag locationTag = new Tag("location", location);
            tags.add(locationTag);
            saveUsers();
        }

        locationTextField.clear();
    }

    /**
     * Adds a person tag to the photo.
     * The person tag is added to the photo.
     */
    public void addPersonTag() {
        String person = personTextField.getText();
        Tag personTag = new Tag("person", person);
        photo.addTag(personTag);
        saveUsers();
        personTextField.clear();
    }

    /**
     * Adds a custom tag to the photo.
     * The custom tag is added to the photo.
     */
    public void addCustomTag() {
        String tagName = customTagName.getText();
        String tagValue = customTagValue.getText();
        Tag customTag = new Tag(tagName, tagValue);
        photo.addTag(customTag);
        saveUsers();
        customTagValue.clear();
    }

    /**
     * Closes the New Tag Scene.
     */
    public void goBack() {
        Stage stage = (Stage) locationTextField.getScene().getWindow();
        stage.close();
    }
}
