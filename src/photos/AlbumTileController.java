package photos;

import photos.UserSceneController;
import photos.Utils;

import static photos.Utils.CURRENT_USER;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
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

    private Album album;
    private Node albumTileNode;

    public void initialize(Album album, Node albumTileNode) {
        setAlbum(album);
        setAlbumTileNode(albumTileNode);
        setAlbumName(album.getName());
        setNumberPhotos(album.getPhotos().size());
        setEarlyDate(album.getEarlyDate());
        setLateDate(album.getLateDate());
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public void setAlbumTileNode(Node albumTileNode) {
        this.albumTileNode = albumTileNode;
    }

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
        CURRENT_USER.getAlbums().remove(album);
        Utils.saveUsers();

        ((Pane) albumTileNode.getParent()).getChildren().remove(albumTileNode);
    }

    public void renameAlbum() {
        album.setName(renameField.getText());
        Utils.saveUsers();

        albumName.setText(album.getName());
    }
}   
