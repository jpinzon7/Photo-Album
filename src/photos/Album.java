package photos;

import java.io.Serializable;
import java.util.*;

public class Album implements Serializable {
    private String name;
    private List<Photo> photos;
    private String earlyDate;
    private String lateDate;

    public Album(String name) {
        this.name = name;
        photos = new ArrayList<Photo>();
        earlyDate = "00/00/0000";
        lateDate = "00/00/0000";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void addPhoto(Photo photo) {
        photos.add(photo);
    }

    public void removePhoto(Photo photo) {
        photos.remove(photo);
    }

    public String getEarlyDate() {
        return earlyDate;
    }

    public void setEarlyDate(String earlyDate) {
        this.earlyDate = earlyDate;
    }

    public String getLateDate() {
        return lateDate;
    }

    public void setLateDate(String lateDate) {
        this.lateDate = lateDate;
    }
}
