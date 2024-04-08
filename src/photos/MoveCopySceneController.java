package photos;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class MoveCopySceneController {
    @FXML
    ScrollPane scrollPane;
    @FXML
    TilePane tilePane;

    private Photo photo;
    private Album fromAlbum;
    private AnchorPane anchorPane;

    public void initialize(Photo photo, Album album, AnchorPane anchorPane) {
        this.photo = photo;
        this.fromAlbum = album;
        this.anchorPane = anchorPane;
        displayAlbums();
    }

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

    public void goBack() {
        Stage stage = (Stage) scrollPane.getScene().getWindow();
        stage.close();
    }
}
