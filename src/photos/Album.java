package photos;

import static photos.Utils.convertIntDateToString;

import java.io.Serializable;
import java.util.*;

public class Album implements Serializable {
    private String name;
    private List<Photo> photos;
    private int earlyDateInt;
    private int lateDateInt;
    private String earlyDate;
    private String lateDate;
    private List<Integer> dateList;

    public Album(String name) {
        this.name = name;
        photos = new ArrayList<Photo>();
        dateList = new ArrayList<Integer>();
        earlyDateInt = 99999999;
        lateDateInt = 00000000;
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

    public String getLateDate() {
        return lateDate;
    }

    public void setDate(int earlyDateInt) {
        dateList.add(earlyDateInt);
        Collections.sort(dateList);
        this.earlyDateInt = dateList.get(0);
        this.lateDateInt = dateList.get(dateList.size() - 1);
        this.earlyDate = convertIntDateToString(this.earlyDateInt);
        this.lateDate = convertIntDateToString(this.lateDateInt);
    }

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

    // public String convertIntDateToString(int date) {
    //     String dateStr = Integer.toString(date);
    //     String year = dateStr.substring(0, 4);
    //     String month = dateStr.substring(4, 6);
    //     String day = dateStr.substring(6, 8);
    //     String formattedDate = month + "/" + day + "/" + year;
    //     return formattedDate;
    // }
}
