package com.example.majorproject;

import android.media.Image;
import android.net.Uri;
import android.widget.ImageView;

public class SendRecent {
    private String imagePath;
    private boolean check;

    public SendRecent(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
