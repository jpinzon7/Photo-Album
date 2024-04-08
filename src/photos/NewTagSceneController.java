package photos;

import static photos.Utils.saveUsers;

import java.util.List;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.ButtonBar;

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


    public void initialize(Photo photo) {
        this.photo = photo;
    }

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

    public void addPersonTag() {
        String person = personTextField.getText();
        Tag personTag = new Tag("person", person);
        photo.addTag(personTag);
        saveUsers();
        personTextField.clear();
    }

    public void addCustomTag() {
        String tagName = customTagName.getText();
        String tagValue = customTagValue.getText();
        Tag customTag = new Tag(tagName, tagValue);
        photo.addTag(customTag);
        saveUsers();
        customTagValue.clear();
    }

    public void goBack() {
        Stage stage = (Stage) locationTextField.getScene().getWindow();
        stage.close();
    }
}
