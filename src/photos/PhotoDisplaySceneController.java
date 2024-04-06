package photos;

import static photos.Utils.convertIntDateToString;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PhotoDisplaySceneController {
    @FXML
    ImageView photoView;
    @FXML
    Button leftButton;
    @FXML
    Button rightButton;
    @FXML
    Label dateLabel;

    private Album album;
    private int photoIndex;
    private Photo photo;

    public void initialize(Album album, int photoIndex) {
        this.album = album;
        this.photoIndex = photoIndex;
        this.photo = album.getPhotos().get(photoIndex);
        photoView.setImage(new Image(album.getPhotos().get(photoIndex).getURIPath()));
        checkLeftButtonDisable(photoIndex);
        checkRightButtonDisable(photoIndex);
        dateLabel.setText(convertIntDateToString(photo.getDateTaken()));
    }

    public void nextPicture() {
        photoIndex++;
        leftButton.setDisable(false);
        checkRightButtonDisable(photoIndex);
        photoView.setImage(new Image(album.getPhotos().get(photoIndex).getURIPath()));
    }

    public void previousPicture() {
        photoIndex--;
        rightButton.setDisable(false);
        checkLeftButtonDisable(photoIndex);
        photoView.setImage(new Image(album.getPhotos().get(photoIndex).getURIPath()));
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
}
