package photos.controller;

import static photos.controller.Utils.saveUsers;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import photos.model.Album;
import photos.model.Photo;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;

/**
 * This class controls the move copy tile.
 * Allows the user to move or copy a photo to another album.
 * It is used in the Move Copy Scene to display the albums the photo is not in.
 * @author Maxim Vyshnevsky and Jorge Pinzon
 */
public class MoveCopyTileController {
    @FXML
    Label nameLabel; // The label for the album name

    private Album fromAlbum;
    private Album toAlbum;
    private Photo photo;
    private Node albumTileNode;
    private ScrollPane scrollPane;
    private AnchorPane anchorPane;

    /**
     * Initializes the move/copy tile with the albums and photo.
     * Sets the name of the album.
     * @param fromAlbum
     * @param toAlbum
     * @param photo
     * @param albumTileNode
     * @param scrollPane
     * @param anchorPane
     */
    public void initialize(Album fromAlbum, Album toAlbum, Photo photo, Node albumTileNode, ScrollPane scrollPane, AnchorPane anchorPane) {
        this.fromAlbum = fromAlbum;
        this.toAlbum = toAlbum;
        this.photo = photo;
        this.albumTileNode = albumTileNode;
        this.scrollPane = scrollPane;
        this.anchorPane = anchorPane;

        nameLabel.setText(toAlbum.getName());
    }

    /**
     * Moves the photo to the album.
     * Deletes the photo from the current album and adds it to the new album.
     * Updates the date of the new album.
     * Saves the users to the file and returns to the album scene.
     */
    public void move() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Move Photo");
        alert.setHeaderText("Move Photo to Another Album");
        alert.setContentText(
                "The photo will be deleted from the current album and you will be returned to the showcase of all pictures in the current album. Do you want to proceed?");

        ButtonType proceedButton = new ButtonType("Proceed");
        ButtonType cancelButton = new ButtonType("Cancel");

        alert.getButtonTypes().setAll(proceedButton, cancelButton);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == proceedButton) {
                toAlbum.addPhoto(photo);
                fromAlbum.removePhoto(photo);
                toAlbum.setDate(photo.getDateTaken());
                fromAlbum.removeDate(photo.getDateTaken());

                saveUsers();

                try {
                    Stage stage = (Stage) scrollPane.getScene().getWindow();
                    stage.close();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/view/AlbumScene.fxml"));
                    Parent root = loader.load();
                    AlbumSceneController controller = loader.getController();
                    controller.initialize(fromAlbum);
                    Scene scene = new Scene(root);
                    stage = (Stage) anchorPane.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * Copies the photo to a new album
     */
    public void copy() {
        toAlbum.addPhoto(photo);
        toAlbum.setDate(photo.getDateTaken());
        saveUsers();

        double oldScrollPos = scrollPane.getVvalue();

        ((TilePane) albumTileNode.getParent()).getChildren().remove(albumTileNode);

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
}
