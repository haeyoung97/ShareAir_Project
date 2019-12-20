package com.example.majorproject;

import android.media.Image;
import android.net.Uri;
import android.widget.ImageView;

public class SendRecent {
    private Uri imageUri;
    private boolean check;

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
