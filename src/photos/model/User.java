package photos.model;
import java.io.Serializable;
import java.util.*;

public class User implements Serializable {
    private String username;
    private List <Album> albums;
    private HashMap<String, Photo> photosMap; // uriString -> Photo

    public User(String username) {
        this.username = username;
        this.albums = new ArrayList<>();
        this.photosMap = new HashMap<>();
    }

    
    /** 
     * @return String
     */
    public String getUsername() {
        return username;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void addAlbum(Album album) {
        albums.add(album);
    }

    public HashMap<String, Photo> getPhotosMap() {
        return photosMap;
    }

    public void addPhotoToUser(Photo photo) {
        photosMap.put(photo.getURIPath(), photo);
    }

    public void removePhotoFromUser(Photo photo) {
        photosMap.remove(photo.getURIPath());
    }

    public Photo searchPhoto(String filePath) {
        if (photosMap.containsKey(filePath)) {
            return photosMap.get(filePath);
        }
        return null;
    }
}
