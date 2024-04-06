package photos;

import static photos.Utils.CURRENT_USER;
import static photos.Utils.saveUsers;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.control.Alert.AlertType;

public class AlbumSceneController {
    @FXML
    private TilePane photoPane;
    @FXML 
    private ScrollPane scrollPane;


    private Album album;

    public void initialize(Album album) {
        this.album = album;
        for (Photo photo : album.getPhotos()) {
            displayPhoto(photo);
        }
    }

    public void addPhoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters()
                .add(new ExtensionFilter("Image Files", "*.png", "*.bmp", "*.jpeg", "*.gif", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            String imageURI = selectedFile.toURI().toString();
            Photo photo = CURRENT_USER.searchPhoto(imageURI);
            if (photo != null) {
                System.out.println("Photo already exists in an album.");
                // Check if photo is already in the album
                if (album.getPhotos().contains(photo)) {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Duplicate Photo");
                    alert.setHeaderText(null);
                    alert.setContentText("Photo already exists in this album.");
                    alert.showAndWait();
                    return;
                }
                else {
                    album.addPhoto(photo);
                    photo.addAlbum(album);
                }
            } else {
                photo = new Photo(imageURI);
                CURRENT_USER.addPhotoToUser(photo);
                album.addPhoto(photo);
                photo.addAlbum(album);
            }

            // Calculate the new earliest and latest dates
            album.setDate(photo.getDateTaken());

            saveUsers();
            displayPhoto(photo);
        } else {
            System.out.println("Image file selection cancelled.");
        }
    }

    public void displayPhoto(Photo photo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PhotoTile.fxml"));
            Node photoTile = loader.load();
            PhotoTileController photoTileController = loader.getController();
            photoTileController.initialize(photo, album, photoTile, scrollPane);
            photoPane.getChildren().add(photoTile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}