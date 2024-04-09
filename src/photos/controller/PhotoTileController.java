package photos.controller;

import static photos.controller.Utils.CURRENT_USER;
import static photos.controller.Utils.saveUsers;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import photos.model.Album;
import photos.model.Photo;


/**
 * The controller class for the photo tile in the album scene.
 * It handles the display and interaction of a single photo tile.
 * @author Maxim Vyshnevsky and Jorge Pinzon
 */
public class PhotoTileController {
    @FXML
    ImageView imageThumbnail; // The thumbnail for the photo
    @FXML
    Button removeButton; // The button to remove the photo

    private Photo photo;
    private Album album;
    private Node photoTileNode;
    private ScrollPane scrollPane;

    /**
     * Initializes the photo tile controller with the specified photo, album, photo tile node, and scroll pane.
     * This method is called during the initialization of the album scene.
     *
     * @param photo          The photo associated with the photo tile.
     * @param album          The album that contains the photo.
     * @param photoTileNode  The JavaFX node representing the photo tile.
     * @param scrollPane     The scroll pane that contains the photo tiles.
     */
    public void initialize(Photo photo, Album album, Node photoTileNode, ScrollPane scrollPane) {
        this.photo = photo;
        this.album = album;
        this.photoTileNode = photoTileNode;
        this.scrollPane = scrollPane;
        setImageThumbnail(new Image(photo.getURIPath()));
    }

    /**
     * Sets the image thumbnail for the photo tile.
     *
     * @param image The image to be set as the thumbnail.
     */
    public void setImageThumbnail(Image image) {
        imageThumbnail.setImage(image);
    }

    /**
     * Removes the photo from the album and updates the necessary data.
     * This method is called when the remove button is clicked.
     */
    public void removePhoto() {
        // Remove the photo from the album
        album.removePhoto(photo);

        // Remove the date of the photo from the album, the earlyDate and lateDate will be updated
        album.removeDate(photo.getDateTaken());

        // Remove the album reference from the photo
        photo.removeAlbum(album);

        // If the photo is not in any album, remove it from the user
        if (photo.getAlbums().isEmpty()) {
            CURRENT_USER.removePhotoFromUser(photo);
        }

        saveUsers();

        double oldScrollPos = scrollPane.getVvalue();

        // Remove the photo tile from the album scene
        ((Pane) photoTileNode.getParent()).getChildren().remove(photoTileNode);

        // ChangeListener for the scrollbar position
        // Whenever deleting an album, JavaFX changes the scrollbar position to the top
        // This listener will keep the scrollbar position at the same place
        ChangeListener<Number> listener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (scrollPane.heightProperty().get() != 0) {
                    scrollPane.setVvalue(oldScrollPos);
                }
                scrollPane.vvalueProperty().removeListener(this);
            }
        };

        scrollPane.vvalueProperty().addListener(listener);
    }

    /**
     * Gets the image thumbnail of the photo tile.
     *
     * @return The image thumbnail.
     */
    public ImageView getImageThumbnail() {
        return imageThumbnail;
    }

    /**
     * Switches to the large photo view when the user clicks on the photo thumbnail.
     */
    public void switchView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/view/PhotoDisplayScene.fxml"));
            Parent root = loader.load();
            Scene photoDisplayScene = new Scene(root);

            Stage stage = (Stage) imageThumbnail.getScene().getWindow();

            PhotoDisplaySceneController photoDisplaySceneController = loader.getController();
            photoDisplaySceneController.initialize(album, album.getPhotos().indexOf(photo));

            stage.setScene(photoDisplayScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
