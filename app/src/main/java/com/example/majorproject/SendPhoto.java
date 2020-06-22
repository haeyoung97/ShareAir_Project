package com.example.majorproject;

import android.net.Uri;

public class SendPhoto {
    private String imagePath;
    private boolean check;
    private int index;
    private Uri uri;
    private boolean isSelected = false;

    public Uri getUri(){
        return uri;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public SendPhoto(String imagePath) {
        this.imagePath = imagePath;
        uri = Uri.parse("file://"+imagePath);
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
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
