package photos;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

/**
 * Controller for the move/copy scene.
 * Allows the user to move or copy a photo to another album.
 * Displays the albums the photo is not in.
 * Used in the User Scene when the user wants to move or copy a photo.
 * @author Maxim Vyshnevsky and Jorge Pinzon
 */

public class MoveCopySceneController {
    @FXML
    ScrollPane scrollPane;
    @FXML
    TilePane tilePane;

    private Photo photo;
    private Album fromAlbum;
    private AnchorPane anchorPane;

    /**
     * Initializes the move/copy scene with the photo, album, and anchor pane.
     * Displays the albums the photo is not in.
     * @param photo
     * @param album
     * @param anchorPane
     */
    public void initialize(Photo photo, Album album, AnchorPane anchorPane) {
        this.photo = photo;
        this.fromAlbum = album;
        this.anchorPane = anchorPane;
        displayAlbums();
    }

    /**
     * Displays the albums the photo is not in.
     * For each album, a MoveCopyTile is created and added to the tile pane.
     */
    public void displayAlbums() {
        for (Album album : Utils.CURRENT_ALBUMS) {
            if (!album.getPhotos().contains(photo)) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("MoveCopyTile.fxml"));
                    Node albumNode = loader.load();
                    MoveCopyTileController controller = loader.getController();
                    controller.initialize(fromAlbum, album, photo, albumNode, scrollPane, anchorPane);
                    tilePane.getChildren().add(albumNode);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Closes the move/copy scene.
     */
    public void goBack() {
        Stage stage = (Stage) scrollPane.getScene().getWindow();
        stage.close();
    }
}
