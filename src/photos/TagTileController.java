package photos;

import static photos.Utils.saveUsers;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

public class TagTileController {
    @FXML
    Label tagNameLabel;
    @FXML
    Label tagValuesLabel;

    private String tagName;
    private Photo photo;
    Node tagNode;
    ScrollPane tagScrollPane;

    public void initialize(String tagName, String tagValues, Photo photo, Node tagNode, ScrollPane tagScrollPane) {
        this.tagName = tagName;
        this.photo = photo;
        this.tagNode = tagNode;
        this.tagScrollPane = tagScrollPane;

        // Set the tag name
        // Set the tag values
        tagNameLabel.setText(tagName);
        tagValuesLabel.setText(tagValues.toString());
    }

    public void deleteTag() {
        List<Tag> tags = photo.getTags();
        tags.remove(tags.stream().filter(tag -> tag.getTagName().equals(tagName)).findFirst().get());
        saveUsers();

        double oldScrollPos = tagScrollPane.getVvalue();

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
