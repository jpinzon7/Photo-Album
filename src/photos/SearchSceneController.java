package photos;

import java.io.IOException;
import java.util.List;
import static photos.Utils.CURRENT_USER;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class SearchSceneController {

    @FXML
    private Label albumLabel;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TilePane searchResultsPane;

    @FXML
    private ScrollPane scrollPane;

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

    public void displayPhoto(Photo photo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PhotoTile.fxml"));
            Node photoTile = loader.load();
            PhotoTileController photoTileController = loader.getController();
            photoTileController.initialize(photo, null, photoTile, scrollPane);
            searchResultsPane.getChildren().add(photoTile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void goBack() {
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
    public void exitProgram() {
        System.exit(0);
    }

}
