package photos.controller;

import static photos.controller.Utils.saveUsers;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import photos.model.Photo;
import photos.model.Tag;

/**
 * This class controls the tag tile.
 * 
 * @author Maxim Vyshnevsky
 */
public class TagTileController {
    @FXML
    Label tagNameLabel; // The label for the tag name
    @FXML
    Label tagValuesLabel; // The label for the tag values

    private String tagName;
    private Photo photo;
    Node tagNode;
    ScrollPane tagScrollPane;

    // Runs during the initialization of the Photo Display Scene
    public void initialize(String tagName, String tagValues, Photo photo, Node tagNode, ScrollPane tagScrollPane) {
        this.tagName = tagName;
        this.photo = photo;
        this.tagNode = tagNode;
        this.tagScrollPane = tagScrollPane;

        // Set the tag lable name and values
        tagNameLabel.setText(tagName);
        tagValuesLabel.setText(tagValues.toString());
    }

    // Runs when the delete button is clicked
    // Deletes the tag from the photo
    public void deleteTag() {
        List<Tag> tags = photo.getTags();
        tags.remove(tags.stream().filter(tag -> tag.getTagName().equals(tagName)).findFirst().get());
        saveUsers();

        double oldScrollPos = tagScrollPane.getVvalue();

        // Remove the tag from the tile pane
        ((Pane) tagNode.getParent()).getChildren().remove(tagNode);

        /**
         * ChangeListener for the scrollbar position
         * Whenever deleting an album javafx changes the scrollbar position to the top
         * which is annoying
         * This listener will keep the scrollbar position at the same place
         */
        ChangeListener<Number> listener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (tagScrollPane.heightProperty().get() != 0) {
                    tagScrollPane.setVvalue(oldScrollPos);
                }
                tagScrollPane.vvalueProperty().removeListener(this);
            }
        };

        tagScrollPane.vvalueProperty().addListener(listener);
    }
}
