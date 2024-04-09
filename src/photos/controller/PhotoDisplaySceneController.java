package photos.controller;

import static photos.controller.Utils.convertIntDateToString;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import photos.model.Album;
import photos.model.Photo;
import photos.model.Tag;

/**
 * This class controls the photo display scene.
 * 
 * @author Maxim Vyshnevsky
 */
public class PhotoDisplaySceneController {
    @FXML
    ImageView photoView; // The image view for the photo
    @FXML
    Button leftButton; // The button to go to the previous photo
    @FXML
    Button rightButton; // The button to go to the next photo
    @FXML
    Label dateLabel; // The label for the date the photo was taken
    @FXML
    Label captionLabel; // The label for the caption of the photo
    @FXML
    ScrollPane tagScrollPane; // The scroll pane for the tags
    @FXML
    TilePane tagTilePane; // The tile pane for the tags
    @FXML
    AnchorPane anchorPane; // The anchor pane for the scene

    private Album album;
    private int photoIndex;
    private Photo photo;

    private Stage popupStage; // The stage for the pop-up windows

    // Runs after the user clicks on a photo
    public void initialize(Album album, int photoIndex) {
        this.album = album;
        this.photoIndex = photoIndex;
        checkLeftButtonDisable(photoIndex);
        checkRightButtonDisable(photoIndex);
        setupPicture();
    }

    // Shows the tags for the photo
    // Runs during the initialization of the scene
    public void showTags() {
        List<Tag> tags = photo.getTags();
        for (Tag tag : tags) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("TagTile.fxml"));
                Node tagNode = loader.load();
                TagTileController controller = loader.getController();
                controller.initialize(tag.getTagName(), tag.getTagValue(), photo, tagNode, tagScrollPane);
                tagTilePane.getChildren().add(tagNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Runs when the user initializes the scene or goes to the next or previous photo
    // Displays the photo, and the date, caption, and tags for the photo
    public void setupPicture() {
        photo = album.getPhotos().get(photoIndex);
        photoView.setImage(new Image(photo.getURIPath()));
        dateLabel.setText("Date: " + convertIntDateToString(photo.getDateTaken()));
        captionLabel.setText(photo.getCaption());
        tagTilePane.getChildren().clear();
        showTags();
    }

    // Runs when the user clicks on the next photo button
    // Increases the photo index and shows the picture at that index, checks if the right button should be disabled
    public void nextPicture() {
        photoIndex++;
        leftButton.setDisable(false);
        checkRightButtonDisable(photoIndex);
        setupPicture();
    }

    // Runs when the user clicks on the previous photo button
    // Decreases the photo index and shows the picture at that index, checks if the left button should be disabled
    public void previousPicture() {
        photoIndex--;
        rightButton.setDisable(false);
        checkLeftButtonDisable(photoIndex);
        setupPicture();
    }

    // If the photo is the first photo in the album, disable the previous photo button
    public void checkLeftButtonDisable(int photoIndex) {
        if (photoIndex == 0) {
            leftButton.setDisable(true);
        }
    }

    // If the photo is the last photo in the album, disable the next photo button
    public void checkRightButtonDisable(int photoIndex) {
        if (photoIndex == album.getPhotos().size() - 1) {
            rightButton.setDisable(true);
        }
    }

    // Runs when the user clicks on the new caption button
    // It is a pop-up window that allows the user to enter a new caption for the photo
    public void newCaption() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewCaptionScene.fxml"));
            popupStage = new Stage();

            Parent root = loader.load();

            NewCaptionSceneController controller = loader.getController();
            controller.initialize(photo);

            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initStyle(StageStyle.UNDECORATED);
            popupStage.setScene(new Scene(root));
            popupStage.showAndWait();

            captionLabel.setText(photo.getCaption());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Runs when the user clicks on the new tag button
    // It is a pop-up window that allows the user to enter a new tag for the photo
    public void newTag() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewTagScene.fxml"));
            popupStage = new Stage();

            Parent root = loader.load();

            NewTagSceneController controller = loader.getController();
            controller.initialize(photo);

            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initStyle(StageStyle.UNDECORATED);
            popupStage.setScene(new Scene(root));
            popupStage.showAndWait();

            tagTilePane.getChildren().clear();
            showTags();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Runs when the user clicks on the move/copy button
    // It is a pop-up window that allows the user to move or copy the photo to another album
    public void moveCopy() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MoveCopyScene.fxml"));
            popupStage = new Stage();

            Parent root = loader.load();

            MoveCopySceneController controller = loader.getController();
            controller.initialize(photo, album, anchorPane);

            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initStyle(StageStyle.UNDECORATED);
            popupStage.setScene(new Scene(root));
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Runs when the user clicks on the Go Back button
    // Goes back to the album scene
    public void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AlbumScene.fxml"));
            Parent root = loader.load();
            AlbumSceneController controller = loader.getController();
            controller.initialize(album);
            Scene scene = new Scene(root);
            Stage stage = (Stage) photoView.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Runs when the user clicks on the Exit button, exits the program
    public void exitProgram() {
        System.exit(0);
    }
}
