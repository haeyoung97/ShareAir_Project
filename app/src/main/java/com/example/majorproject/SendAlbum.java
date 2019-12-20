package com.example.majorproject;

import android.media.Image;
import android.net.Uri;

public class SendAlbum {
    private String imageName;
    private int imageSum;
    private Uri imageUri;
    private boolean imageCheck;

    public SendAlbum(String imageName, int imageSum, Uri imageUri, boolean imageCheck) {
        this.imageName = imageName;
        this.imageSum = imageSum;
        this.imageUri = imageUri;
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

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public boolean isImageCheck() {
        return imageCheck;
    }

    public void setImageCheck(boolean imageCheck) {
        this.imageCheck = imageCheck;
    }
}
