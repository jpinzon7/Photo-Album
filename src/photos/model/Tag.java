package photos.model;
import java.io.Serializable;

/**
 * Represents a tag associated with a photo.
 */
public class Tag implements Serializable {
    private String tagName;
    private String tagValue;

    /**
     * Constructs a new Tag object with the specified tag name and tag value.
     *
     * @param tagName  the name of the tag
     * @param tagValue the value of the tag
     */
    public Tag(String tagName, String tagValue) {
        this.tagName = tagName;
        this.tagValue = tagValue;
    }

    /**
     * Returns the name of the tag.
     *
     * @return the name of the tag
     */
    public String getTagName() {
        return tagName;
    }

    /**
     * Sets the name of the tag.
     *
     * @param tagName the name of the tag
     */
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    /**
     * Returns the value of the tag.
     *
     * @return the value of the tag
     */
    public String getTagValue() {
        return tagValue;
    }

    /**
     * Sets the value of the tag.
     *
     * @param tagValue the value of the tag
     */
    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }
}
