package photos;

import static photos.Utils.CURRENT_USER;

import java.io.IOException;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;


/**
 * This class controls the album tile.
 *
 * @author Maxim Vyshnevsky
 */
public class AlbumTileController {
    @FXML
    private ImageView albumThumbnail;
    @FXML
    private Label albumName;
    @FXML
    private Label numberPhotos;
    @FXML
    private Label earlyDate;
    @FXML
    private Label lateDate;
    @FXML
    private Button deleteButton;
    @FXML
    private TextField renameField;
    @FXML
    private Button renameButton;

    private Album album;
    private Node albumTileNode;
    private ScrollPane scrollPane;

    public void initialize(Album album, Node albumTileNode, ScrollPane scrollPane) {
        setAlbum(album);
        setAlbumTileNode(albumTileNode);
        setScrollPane(scrollPane);
        setAlbumName(album.getName());
        setNumberPhotos(album.getPhotos().size());
        setEarlyDate(album.getEarlyDate());
        setLateDate(album.getLateDate());

        if (album.getPhotos().size() > 0) {
            setAlbumThumbnail(album.getPhotos().get(0).getURIPath());
        }
    }

    public void setScrollPane(ScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public void setAlbumTileNode(Node albumTileNode) {
        this.albumTileNode = albumTileNode;
    }

    public void setAlbumThumbnail(String path) {
        albumThumbnail.setImage(new Image(path));
    }

    public void setAlbumName(String albumName) {
        this.albumName.setText(albumName);
    }

    public void setNumberPhotos(int numberPhotos) {
        this.numberPhotos.setText(Integer.toString(numberPhotos));
    }

    public void setEarlyDate(String earlyDate) {
        this.earlyDate.setText(earlyDate);
    }

    public void setLateDate(String lateDate) {
        this.lateDate.setText(lateDate);
    }

    public void deleteAlbum() {
        for (Photo photo : album.getPhotos()) {
            photo.removeAlbum(album);
            if (photo.getAlbums().isEmpty()) {
                CURRENT_USER.removePhotoFromUser(photo);
            }
        }
        CURRENT_USER.getAlbums().remove(album);
        Utils.saveUsers();

        double oldScrollPos = scrollPane.getVvalue();

        ((Pane) albumTileNode.getParent()).getChildren().remove(albumTileNode);

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

    public void renameAlbum() {
        album.setName(renameField.getText());
        Utils.saveUsers();

        albumName.setText(album.getName());
    }

    public void openAlbum() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AlbumScene.fxml"));
            Parent root = loader.load();
            Scene albumScene = new Scene(root);

            Stage stage = (Stage) albumName.getScene().getWindow();

            AlbumSceneController albumSceneController = loader.getController();
            albumSceneController.initialize(album);

            stage.setScene(albumScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
