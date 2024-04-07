package photos;

import static photos.Utils.convertIntDateToString;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PhotoDisplaySceneController {
    @FXML
    ImageView photoView;
    @FXML
    Button leftButton;
    @FXML
    Button rightButton;
    @FXML
    Label dateLabel;
    @FXML
    Label captionLabel;
    @FXML
    ScrollPane tagScrollPane;
    @FXML
    TilePane tagTilePane;
    @FXML
    AnchorPane anchorPane;

    private Album album;
    private int photoIndex;
    private Photo photo;

    private Stage popupStage;

    public void initialize(Album album, int photoIndex) {
        this.album = album;
        this.photoIndex = photoIndex;
        checkLeftButtonDisable(photoIndex);
        checkRightButtonDisable(photoIndex);
        setupPicture();
    }

    public void showTags() {
        for (Map.Entry<String, List<String>> entry : photo.getTags().entrySet()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("TagTile.fxml"));
                Node tagNode = loader.load();
                TagTileController controller = loader.getController();
                String tagName = entry.getKey();
                List<String> tagValues = entry.getValue();
                controller.initialize(tagName, tagValues, photo, tagNode, tagScrollPane);
                tagTilePane.getChildren().add(tagNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setupPicture() {
        photo = album.getPhotos().get(photoIndex);
        photoView.setImage(new Image(photo.getURIPath()));
        dateLabel.setText("Date: " + convertIntDateToString(photo.getDateTaken()));
        captionLabel.setText(photo.getCaption());
        // tagTilePane.getChildren().clear();
        showTags();
    }

    public void nextPicture() {
        photoIndex++;
        leftButton.setDisable(false);
        checkRightButtonDisable(photoIndex);
        setupPicture();
    }

    public void previousPicture() {
        photoIndex--;
        rightButton.setDisable(false);
        checkLeftButtonDisable(photoIndex);
        setupPicture();
    }

    public void checkLeftButtonDisable(int photoIndex) {
        if (photoIndex == 0) {
            leftButton.setDisable(true);
        }
    }

    public void checkRightButtonDisable(int photoIndex) {
        if (photoIndex == album.getPhotos().size() - 1) {
            rightButton.setDisable(true);
        }
    }

    public void newCaption() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewCaptionScene.fxml"));
            popupStage = new Stage();

            Parent root = loader.load();

            NewCaptionSceneController controller = loader.getController();
            controller.initialize(photo);

            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initStyle(StageStyle.UNDECORATED);
            popupStage.setScene(new Scene(root));
            popupStage.showAndWait();

            captionLabel.setText(photo.getCaption());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void newTag() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewTagScene.fxml"));
            popupStage = new Stage();

            Parent root = loader.load();

            NewTagSceneController controller = loader.getController();
            controller.initialize(photo);

            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initStyle(StageStyle.UNDECORATED);
            popupStage.setScene(new Scene(root));
            popupStage.showAndWait();

            tagTilePane.getChildren().clear();
            showTags();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void moveCopy() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MoveCopyScene.fxml"));
            popupStage = new Stage();

            Parent root = loader.load();

            MoveCopySceneController controller = loader.getController();
            controller.initialize(photo, album, anchorPane);

            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initStyle(StageStyle.UNDECORATED);
            popupStage.setScene(new Scene(root));
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AlbumScene.fxml"));
            Parent root = loader.load();
            AlbumSceneController controller = loader.getController();
            controller.initialize(album);
            Scene scene = new Scene(root);
            Stage stage = (Stage) photoView.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exitProgram() {
        System.exit(0);
    }
}
