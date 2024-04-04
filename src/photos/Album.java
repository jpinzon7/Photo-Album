package photos;

import java.io.Serializable;
import java.util.*;

public class Album {
    private String name;
    private List<Photo> photos;

    public Album(String name) {
        this.name = name;
        this.photos = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Photo> getPhotos() {
        return photos;
    }
    
}
