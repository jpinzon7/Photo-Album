package photos;

import java.io.Serializable;

public class Photo implements Serializable {
    private String filePath;
    private String caption;

    public Photo(String filePath, String caption) {
        this.filePath = filePath;
        this.caption = caption;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getCaption() {
        return caption;
    }
}