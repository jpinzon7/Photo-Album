package photos.controller;

import java.io.IOException;
import java.util.List;
import static photos.Utils.CURRENT_USER;
import static photos.controller.Utils.saveUsers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import photos.model.Album;
import photos.model.Photo;

/**
 * Controller for the search scene.
 * Allows the user to create a new album from the search results and display the search results.
 * Used in the User Scene when the user searches for photos.
 * Contains method to allow user to create album from search results.
 * @author Maxim Vyshnevsky and Jorge Pinzon
 */

public class SearchSceneController {

    @FXML
    private Label albumLabel;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TilePane searchResultsPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextField newAlbumName;

    private List<Photo> searchResults;

    @FXML
    public void initialize() {
        // Clear the searchResultsPane
        searchResultsPane.getChildren().clear();

        // Add a tile for each photo in the search results
        if (searchResults != null) {
            for (Photo photo : searchResults) {
                displayPhoto(photo);
            }
        }
    }

    public void setSearchResults(List<Photo> searchResults) {
        this.searchResults = searchResults;
        initialize();
    }

    public void displayPhoto(Photo photo) { //display the actual photos in the search results
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PhotoTile.fxml"));
            Node photoTile = loader.load();
            PhotoTileController photoTileController = loader.getController();
            photoTileController.initialize(photo, photo.getAlbums().get(0), photoTile, scrollPane);
            photoTileController.getImageThumbnail().setOnMouseClicked(e -> photoTileController.switchView());
            searchResultsPane.getChildren().add(photoTile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createAlbum() {
        String newAlbumNameText = newAlbumName.getText();

        // Check if an album with the same name already exists and display alert
        if (CURRENT_USER.getAlbums().stream().anyMatch(album -> album.getName().equals(newAlbumNameText))) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText(null);
            alert.setContentText("An album with this name already exists.");
            alert.showAndWait();
            return;
        }
        // Create a new album with the name from the text field
        Album album = new Album(newAlbumName.getText(), searchResults);
        // Add the album to the user's list of albums
        CURRENT_USER.addAlbum(album);
        // Update the data file
        saveUsers();
    }

    
    @FXML
    public void goBack() { //go back to the user scene when go back button pressed
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserScene.fxml"));
            Parent root = loader.load();
            Scene userScene = new Scene(root);

            UserSceneController userSceneController = loader.getController();
            userSceneController.initialize(CURRENT_USER.getUsername());

            Stage stage = (Stage) searchResultsPane.getScene().getWindow();
            stage.setScene(userScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void exitProgram() { //exit out of application safely
        System.exit(0);
    }

}
