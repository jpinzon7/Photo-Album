package photos;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Photo implements Serializable {
    private String uriString;
    private List<Album> inAlbums;
    // private String caption;

    public Photo(String URI) {
        uriString = URI;
        inAlbums = new ArrayList<Album>();
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

    // public String getCaption() {
    //     return caption;
    // }
}