package msku.ceng.madlab.week3;

import android.graphics.Bitmap;
import android.location.Location;

public class Post {
    private String message;
    private Location Location;
    private Bitmap image;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public android.location.Location getLocation() {
        return Location;
    }

    public void setLocation(android.location.Location location) {
        Location = location;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}