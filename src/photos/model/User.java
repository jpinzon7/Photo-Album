package photos.model;
import java.io.Serializable;
import java.util.*;

/**
 * Represents a user in the photo management system.
 */
public class User implements Serializable {
    private String username;
    private List<Album> albums;
    private HashMap<String, Photo> photosMap; // uriString -> Photo

    /**
     * Constructs a new User object with the specified username.
     *
     * @param username the username of the user
     */
    public User(String username) {
        this.username = username;
        this.albums = new ArrayList<>();
        this.photosMap = new HashMap<>();
    }

    /**
     * Returns the username of the user.
     *
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the list of albums owned by the user.
     *
     * @return the list of albums owned by the user
     */
    public List<Album> getAlbums() {
        return albums;
    }

    /**
     * Adds the specified album to the user's list of albums.
     *
     * @param album the album to be added
     */
    public void addAlbum(Album album) {
        albums.add(album);
    }

    /**
     * Returns the map of photos owned by the user.
     *
     * @return the map of photos owned by the user
     */
    public HashMap<String, Photo> getPhotosMap() {
        return photosMap;
    }

    /**
     * Adds the specified photo to the user's map of photos.
     *
     * @param photo the photo to be added
     */
    public void addPhotoToUser(Photo photo) {
        photosMap.put(photo.getURIPath(), photo);
    }

    /**
     * Removes the specified photo from the user's map of photos.
     *
     * @param photo the photo to be removed
     */
    public void removePhotoFromUser(Photo photo) {
        photosMap.remove(photo.getURIPath());
    }

    /**
     * Searches for a photo with the specified file path in the user's map of photos.
     *
     * @param filePath the file path of the photo to be searched
     * @return the photo with the specified file path, or null if not found
     */
    public Photo searchPhoto(String filePath) {
        if (photosMap.containsKey(filePath)) {
            return photosMap.get(filePath);
        }
        return null;
    }
}
