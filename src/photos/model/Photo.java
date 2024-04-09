package photos.model;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * This class is the model for a Photo.
 * It contains the URI of the photo, the list of albums the photo is in, the date the photo was taken, the caption of the photo, and the list of tags of the photo.
 * The date is stored as a Calendar object.
 * The tags are stored as a list of Tag objects.
 * The URI is stored as a string.
 * The caption is stored as a string.
 * The albums are stored as a list of Album objects.
 * @author Maxim Vyshnevsky and Jorge Pinzon
 */
public class Photo implements Serializable {
    private String uriString;
    private List<Album> inAlbums;
    private Calendar dateTaken;
    private String caption;
    private List<Tag> tags;

    /**
     * Constructor for a Photo.
     * Initializes the URI of the photo, the list of albums the photo is in, the date the photo was taken, the caption of the photo, and the list of tags of the photo.
     * @param URI The URI of the photo
     */
    public Photo(String URI) {
        uriString = URI;
        inAlbums = new ArrayList<Album>();
        dateTaken = Calendar.getInstance();
        caption = "No caption provided.";
        tags = new ArrayList<Tag>();

        try {
            File file = new File(new URI(URI));
            BasicFileAttributes attr = Files.readAttributes(Paths.get(file.getAbsolutePath()),
                    BasicFileAttributes.class);
            dateTaken.setTimeInMillis(attr.creationTime().toMillis());
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        // this.caption = caption;
    }

    
    /** 
     * Getter for the URI of the photo.
     * @return String
     */
    public String getURIPath() {
        return uriString;
    }

    
    /** 
     * Adds an album to the list of albums the photo is in.
     * @param album
     */
    public void addAlbum(Album album) {
        inAlbums.add(album);
    }

    /**
     * Removes an album from the list of albums the photo is in.
     * @param album
     */
    public void removeAlbum(Album album) {
        inAlbums.remove(album);
    }

    /**
     * Getter for the list of albums the photo is in.
     * @return List<Album>
     */
    public List<Album> getAlbums() {
        return inAlbums;
    }

    /**
     * Getter for the date the photo was taken.
     * The date is stored as an integer in the format YYYYMMDD.
     * @return int
     */
    public int getDateTaken() {
        return dateTaken.get(Calendar.YEAR) * 10000 + (dateTaken.get(Calendar.MONTH) + 1) * 100
                + dateTaken.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Getter for the caption of the photo.
     * @return String
     */
    public String getCaption() {
        return caption;
    }

    /**
     * Setter for the caption of the photo.
     * @param caption The caption of the photo
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }

    /**
     * Adds a tag to the list of tags of the photo.
     * @param tag The tag to be added
     */
    public void addTag(Tag tag) {
        tags.add(tag);
    }

    /**
     * Removes a tag from the list of tags of the photo.
     * @param tag The tag to be removed
     */
    public void removeTag(Tag tag) {
        tags.remove(tag);
    }

    /**
     * Getter for the list of tags of the photo.
     * @return List<Tag>
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     * Checks if the photo has a tag with the given name and value.
     * @param tagName The name of the tag
     * @param tagValue The value of the tag
     * @return boolean
     */
    public boolean hasTag(String tagName, String tagValue) {
        for (Tag tag : tags) {
            if (tag.getTagName().equals(tagName) && tag.getTagValue().equals(tagValue)) {
                return true;
            }
        }
        return false;
    }

}