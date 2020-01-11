package com.example.majorproject;

import android.media.Image;
import android.net.Uri;

public class SendAlbum {
    private String imageName;
    private int imageSum;
    private String imagePath;
    private boolean imageCheck;

    public SendAlbum(String imageName, int imageSum, String imagePath, boolean imageCheck) {
        this.imageName = imageName;
        this.imageSum = imageSum;
        this.imagePath = imagePath;
        this.imageCheck = imageCheck;
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
}
