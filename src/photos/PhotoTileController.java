package photos;

import static photos.Utils.CURRENT_USER;
import static photos.Utils.saveUsers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class PhotoTileController {
    @FXML
    ImageView imageThumbnail;
    @FXML
    Button removeButton;

    private Photo photo;
    private Album album;
    private Node photoTileNode;
    private ScrollPane scrollPane;

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

    public void removePhoto() {
        album.removePhoto(photo);
        photo.removeAlbum(album);
        if (photo.getAlbums().isEmpty()) {
            CURRENT_USER.removePhotoFromUser(photo);
        }
        saveUsers();

        double oldScrollPos = scrollPane.getVvalue();

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
}
