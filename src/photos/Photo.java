package photos;

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
    // private String caption;

    public Photo(String URI) {
        uriString = URI;
        inAlbums = new ArrayList<Album>();
        dateTaken = Calendar.getInstance();

        try {
            File file = new File(new URI(URI));
            BasicFileAttributes attr = Files.readAttributes(Paths.get(file.getAbsolutePath()), BasicFileAttributes.class);
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

    // public String getCaption() {
    // return caption;
    // }
}