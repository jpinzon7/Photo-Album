package photos.model;

import static photos.controller.Utils.convertIntDateToString;

import java.io.Serializable;
import java.util.*;

/**
 * This class is the model for an Album.
 * It contains the name of the album, a list of photos, and the earliest and latest dates of the photos.
 * The dates are stored as integers in the format YYYYMMDD.
 * @author Maxim Vyshnevsky and Jorge Pinzon
 */
public class Album implements Serializable {
    private String name;
    private List<Photo> photos;
    private int earlyDateInt;
    private int lateDateInt;
    private String earlyDate;
    private String lateDate;
    private List<Integer> dateList;

    /**
     * Constructor for an Album, with only one parameter
     * Initializes the name of the album and the list of photos.
     * @param name The name of the album
     */

    public Album(String name) {
        this.name = name;
        photos = new ArrayList<Photo>();
        dateList = new ArrayList<Integer>();
        earlyDateInt = 99999999;
        lateDateInt = 00000000;
        earlyDate = "00/00/0000";
        lateDate = "00/00/0000";
    }

    /**
     * Constructor for an Album, with two parameters (extra photos parameter)
     * Initializes the name of the album and the list of photos.
     * Also initializes the earliest and latest dates of the photos.
     * @param name The name of the album
     * @param photos The list of photos in the album
     */
    public Album(String name, List<Photo> photos) {
        this.name = name;
        this.photos = photos;
        dateList = new ArrayList<Integer>();
        earlyDateInt = 99999999;
        lateDateInt = 00000000;
        earlyDate = "00/00/0000";
        lateDate = "00/00/0000";
        for (Photo photo : photos) {
            dateList.add(photo.getDateTaken());
        }
        Collections.sort(dateList);
        this.earlyDateInt = dateList.get(0);
        this.lateDateInt = dateList.get(dateList.size() - 1);
        this.earlyDate = convertIntDateToString(this.earlyDateInt);
        this.lateDate = convertIntDateToString(this.lateDateInt);
    }

    /**
     * Getter for the name of the album.
     * @return The name of the album
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name of the album.
     * @param name The name of the album
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the list of photos in the album.
     * @return The list of photos in the album
     */
    public List<Photo> getPhotos() {
        return photos;
    }

    /**
     * Adds a photo to the album.
     * @param photo The photo to be added
     */
    public void addPhoto(Photo photo) {
        photos.add(photo);
    }

    /**
     * Removes a photo from the album.
     * @param photo The photo to be removed
     */
    public void removePhoto(Photo photo) {
        photos.remove(photo);
    }

    /**
     * Getter for the earliest date of the photos in the album.
     * @return The earliest date of the photos in the album
     */
    public String getEarlyDate() {
        return earlyDate;
    }

    /**
     * Getter for the latest date of the photos in the album.
     * @return The latest date of the photos in the album
     */
    public String getLateDate() {
        return lateDate;
    }

    /**
     * Sets the earliest and latest dates of the photos in the album.
     * @param earlyDateInt The earliest date of the photos in the album
     */
    public void setDate(int earlyDateInt) {
        dateList.add(earlyDateInt);
        Collections.sort(dateList);
        this.earlyDateInt = dateList.get(0);
        this.lateDateInt = dateList.get(dateList.size() - 1);
        this.earlyDate = convertIntDateToString(this.earlyDateInt);
        this.lateDate = convertIntDateToString(this.lateDateInt);
    }

    /**
     * Removes a date from the list of dates in the album.
     * @param date The date to be removed
     */
    public void removeDate(int date) {
        dateList.remove(dateList.indexOf(date));
        if (dateList.isEmpty()) {
            this.earlyDateInt = 99999999;
            this.lateDateInt = 00000000;
            this.earlyDate = "00/00/0000";
            this.lateDate = "00/00/0000";
            return;
        }
        Collections.sort(dateList);
        this.earlyDateInt = dateList.get(0);
        this.lateDateInt = dateList.get(dateList.size() - 1);
        this.earlyDate = convertIntDateToString(this.earlyDateInt);
        this.lateDate = convertIntDateToString(this.lateDateInt);
    }
}
