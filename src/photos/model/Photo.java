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

public class Photo implements Serializable {
    private String uriString;
    private List<Album> inAlbums;
    private Calendar dateTaken;
    private String caption;
    private List<Tag> tags;

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

    public String getURIPath() {
        return uriString;
    }

    public void addAlbum(Album album) {
        inAlbums.add(album);
    }

    public void removeAlbum(Album album) {
        inAlbums.remove(album);
    }

    public List<Album> getAlbums() {
        return inAlbums;
    }

    public int getDateTaken() {
        return dateTaken.get(Calendar.YEAR) * 10000 + (dateTaken.get(Calendar.MONTH) + 1) * 100
                + dateTaken.get(Calendar.DAY_OF_MONTH);
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
    }

    public List<Tag> getTags() {
        return tags;
    }

    public boolean hasTag(String tagName, String tagValue) {
        for (Tag tag : tags) {
            if (tag.getTagName().equals(tagName) && tag.getTagValue().equals(tagValue)) {
                return true;
            }
        }
        return false;
    }

}