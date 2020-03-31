package com.example.majorproject;

import android.media.Image;
import android.net.Uri;

public class SendAlbum {
    private String imageName;
    private int imageSum;
    private String imagePath;
    private boolean imageCheck;
    private int index;


    public SendAlbum(String imagePath, String imageName, int imageSum) {
        this.imagePath = imagePath;
        this.imageName = imageName;
        this.imageSum = imageSum;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getImageSum() {
        return imageSum;
    }

    public void setImageSum(int imageSum) {
        this.imageSum = imageSum;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isImageCheck() {
        return imageCheck;
    }

    public void setImageCheck(boolean imageCheck) {
        this.imageCheck = imageCheck;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
