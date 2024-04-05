package photos;

import static photos.Utils.CURRENT_USER;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.beans.value.ChangeListener;


/**
 * This class controls the album tile.
 *
 * @author Maxim Vyshnevsky
 */
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
    private ScrollPane scrollPane;

    public void initialize(Album album, Node albumTileNode, ScrollPane scrollPane) {
        setAlbum(album);
        setAlbumTileNode(albumTileNode);
        setScrollPane(scrollPane);
        setAlbumName(album.getName());
        setNumberPhotos(album.getPhotos().size());
        setEarlyDate(album.getEarlyDate());
        setLateDate(album.getLateDate());
    }

    public void setScrollPane(ScrollPane scrollPane) {
        this.scrollPane = scrollPane;
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

        double oldScrollPos = scrollPane.getVvalue();

        ((Pane) albumTileNode.getParent()).getChildren().remove(albumTileNode);

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

    public void renameAlbum() {
        album.setName(renameField.getText());
        Utils.saveUsers();

        albumName.setText(album.getName());
    }
}
