package photos;
import java.io.Serializable;
import java.util.*;

public class User implements Serializable {
    private String username;
    private List <Album> albums;

    public User(String username) {
        this.username = username;
        this.albums = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void addAlbum(Album album) {
        albums.add(album);
    }
}
