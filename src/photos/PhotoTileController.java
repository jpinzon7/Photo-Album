package photos;

import static photos.Utils.CURRENT_USER;
import static photos.Utils.saveUsers;

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

/**
 * This class controls the photo tile.
 * 
 * @author Maxim Vyshnevsky
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

    // Runs during the initialization of the Album Scene
    public void initialize(Photo photo, Album album, Node photoTileNode, ScrollPane scrollPane) {
        this.photo = photo;
        this.album = album;
        this.photoTileNode = photoTileNode;
        this.scrollPane = scrollPane;
        setImageThumbnail(new Image(photo.getURIPath()));
    }

    public void setImageThumbnail(Image image) {
        imageThumbnail.setImage(image);
    }

    // Runs when the remove button is clicked
    public void removePhoto() {
        album.removePhoto(photo); 
        // Remove the date of the photo from the album, the earlyDate and lateDate will be updated
        album.removeDate(photo.getDateTaken());
        photo.removeAlbum(album);
        // If the photo is not in any album, remove it from the user
        if (photo.getAlbums().isEmpty()) {
            CURRENT_USER.removePhotoFromUser(photo);
        }
        saveUsers();

        double oldScrollPos = scrollPane.getVvalue();

        // Remove the photo tile from the album scene
        ((Pane) photoTileNode.getParent()).getChildren().remove(photoTileNode);

        /**
         * ChangeListener for the scrollbar position
         * Whenever deleting an album javafx changes the scrollbar position to the top
         * which is annoying
         * This listener will keep the scrollbar position at the same place
         */
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

    // Runs when the user clicks on the photo thumbnail
    // Switches to the large photo view
    public void switchView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PhotoDisplayScene.fxml"));
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
