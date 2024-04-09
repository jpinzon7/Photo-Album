package photos.controller;

import static photos.Utils.CURRENT_USER;
import static photos.controller.Utils.saveUsers;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import photos.model.Album;
import photos.model.Photo;
import javafx.scene.control.Alert.AlertType;

/**
 * Controller for the album scene.
 * Handles the UI and logic for the album scene.
 * Allows the user to add photos to the album, display the photos, and go back to the user scene.
 * @author Maxim Vyshnevsky and Jorge Pinzon
 */
public class AlbumSceneController {

    @FXML
    private TilePane photoPane; // The TilePane to display the photos
    @FXML 
    private ScrollPane scrollPane; // The scroll pane for the TilePane
    @FXML
    private Label albumLabel; // The label for the album name

    private Album album;


    /**
	 * Initalizes the album scene with the name of the album and the photos in the album.
	 * @param album
	 */

    public void initialize(Album album) {
        this.album = album;
        albumLabel.setText("Photo album: " + album.getName());
        for (Photo photo : album.getPhotos()) {
            displayPhoto(photo);
        }
    }

    /**
     * Adds a photo to the album.
     * If the photo already exists in the user's photos, it is added to the album.
     * If the photo does not exist in the user's photos, a new photo is created and added to the album.
     * The earliest and latest dates of the album are updated if necessary.
     */

    public void addPhoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters()
                .add(new ExtensionFilter("Image Files", "*.png", "*.bmp", "*.jpeg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String imageURI = selectedFile.toURI().toString();
            Photo photo = CURRENT_USER.searchPhoto(imageURI);
            // Check if the photo already exists in the user's photos
            if (photo != null) {
                System.out.println("Photo already exists in an album.");
                // Check if photo is already in the album
                if (album.getPhotos().contains(photo)) {
                    // Show an alert if the photo is already in the album
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Duplicate Photo");
                    alert.setHeaderText(null);
                    alert.setContentText("Photo already exists in this album.");
                    alert.showAndWait();
                    return;
                }
                else {
                    // Add the photo to the album
                    album.addPhoto(photo);
                    // Add the album to the photo
                    photo.addAlbum(album);
                }
            } else {
                // Photo does not exist in the user's photos
                // Create a new photo and add it to the user's photos
                photo = new Photo(imageURI);
                CURRENT_USER.addPhotoToUser(photo);
                album.addPhoto(photo);
                photo.addAlbum(album);
            }
            // Calculate the new earliest and latest dates
            album.setDate(photo.getDateTaken());
            saveUsers();
            displayPhoto(photo);
        }
    }

    /**
     * The method gets the photo from the album and displays it in a PhotoTile.
     * The PhotoTile is then added to the TilePane.
     * @param photo
     */
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

    /**
     * If the user clicks on the Back button, goes back to the User Scene.
     */
    public void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserScene.fxml"));
            Parent root = loader.load();
            Scene userScene = new Scene(root);

            UserSceneController userSceneController = loader.getController();
            userSceneController.initialize(CURRENT_USER.getUsername());

            Stage stage = (Stage) photoPane.getScene().getWindow();
            stage.setScene(userScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * If the user clicks on the Exit button, exits the program.
     */
    public void exitProgram() {
        System.exit(0);
    }
}