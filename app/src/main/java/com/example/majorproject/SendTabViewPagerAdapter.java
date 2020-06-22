package com.example.majorproject;

import android.util.Log;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class SendTabViewPagerAdapter extends FragmentStatePagerAdapter {
    private int mPageCount;

    private ArrayList<File> imageFile;
    private SendTabRecentFragment recentFragment;
    private SendTabPhotoFragment photoFragment;
//    private SendTabAlbumFragment albumFragment;
    private SendTabVideoFragment videoFragment;
    private SendTabFileFragment fileFragment;


//    public SendTabViewPagerAdapter(@NonNull FragmentManager fm, int mPageCount) {
//        super(fm, mPageCount);
//        this.mPageCount = mPageCount;
//        Log.d("mPageCount : ", Integer.toString(mPageCount));
//    }

    public SendTabViewPagerAdapter(@NonNull FragmentManager fm, int mPageCount) {
        super(fm, mPageCount);
        this.mPageCount = mPageCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                //최근 탭
                recentFragment = new SendTabRecentFragment();
                Log.e("dynamictab_recent?","");
                return recentFragment;

            case 1:
                //사진 탭

                Log.e("dynamictab_photo?","");
                photoFragment = new SendTabPhotoFragment();
                return photoFragment;

            case 2:
                //영상 탭

                videoFragment = new SendTabVideoFragment();
                Log.e("dynamictab_video?","");
                return videoFragment;
            case 3:
                //파일 탭

                fileFragment = new SendTabFileFragment();
                return fileFragment;
            default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return mPageCount;
    }
}
