package photos.controller;

import java.io.IOException;
import java.util.List;
import static photos.controller.Utils.CURRENT_USER;
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

    /**
     * Initializes the controller.
     * This method is automatically called after the FXML file has been loaded.
     * It clears the searchResultsPane and adds a tile for each photo in the search results.
     */
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

    /**
     * Sets the search results.
     * This method is called to set the search results and update the UI.
     *
     * @param searchResults The list of photos representing the search results.
     */
    public void setSearchResults(List<Photo> searchResults) {
        this.searchResults = searchResults;
        initialize();
    }

    /**
     * Displays a photo in the search results.
     * This method loads the FXML file for the photo tile, initializes the photo tile controller,
     * and adds the photo tile to the searchResultsPane.
     *
     * @param photo The photo to be displayed.
     */
    public void displayPhoto(Photo photo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/view/PhotoTile.fxml"));
            Node photoTile = loader.load();
            PhotoTileController photoTileController = loader.getController();
            photoTileController.initialize(photo, photo.getAlbums().get(0), photoTile, scrollPane);
            photoTileController.getImageThumbnail().setOnMouseClicked(e -> photoTileController.switchView());
            searchResultsPane.getChildren().add(photoTile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a new album.
     * This method is called when the create album button is pressed.
     * It checks if an album with the same name already exists, displays an alert if it does,
     * creates a new album with the name from the text field, adds the album to the user's list of albums,
     * and updates the data file.
     */
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

    /**
     * Goes back to the user scene.
     * This method is called when the go back button is pressed.
     * It loads the FXML file for the user scene, initializes the user scene controller,
     * and sets the scene to the user scene.
     */
    @FXML
    public void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/view/UserScene.fxml"));
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

    /**
     * Exits the program.
     * This method is called when the exit button is pressed.
     * It exits the application safely by calling System.exit(0).
     */
    @FXML
    public void exitProgram() {
        System.exit(0);
    }

}
