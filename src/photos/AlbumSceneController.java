package photos;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;


public class AlbumSceneController {
    @FXML
    private GridPane gridPane;

    public void addPhoto(Photo photo) {
        Image image = new Image(photo.getFilePath());
        Label label = new Label(photo.getCaption());
        Button deleteButton = new Button("Delete");

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(new ImageView(image));
        borderPane.setBottom(label);

        HBox hbox = new HBox(deleteButton);
        hbox.setAlignment(Pos.TOP_RIGHT);
        borderPane.setTop(hbox);

        deleteButton.setOnAction(e -> deletePhoto(borderPane));

        int numberOfCells = gridPane.getChildren().size();
        int newRow = numberOfCells / gridPane.getColumnConstraints().size();
        int newColumn = numberOfCells % gridPane.getColumnConstraints().size();

        gridPane.add(borderPane, newColumn, newRow);
    }

    public void deletePhoto(BorderPane borderPane) {
        gridPane.getChildren().remove(borderPane);
    }
}