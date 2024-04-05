package photos;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.TilePane;


public class AlbumTileController {
    @FXML
    private Image albumThumbnail;
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

    public void setAlbumThumbnail(String path) {
        albumThumbnail = new Image(path);
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
        // Delete the album from the user's list of albums
        // Save the users to the data file
    }

    public void renameAlbum() {
        // Rename the album
        // Save the users to the data file
    }
}   
