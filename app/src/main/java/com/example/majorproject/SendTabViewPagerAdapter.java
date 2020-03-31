package com.example.majorproject;

import android.util.Log;

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
    private SendTabAlbumFragment albumFragment;
    private SendTabVideoFragment videoFragment;
    private SendTabFileFragment fileFragment;

    public SendTabViewPagerAdapter(@NonNull FragmentManager fm, int mPageCount) {
        super(fm, mPageCount);
        this.mPageCount = mPageCount;
        Log.d("mPageCount : ", Integer.toString(mPageCount));
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                //최근 탭
                MainActivity.selectList.clear();
                recentFragment = new SendTabRecentFragment();
                return recentFragment;

                // 임시
//                photoFragment = new SendTabPhotoFragment();
//                return photoFragment;
            case 1:
                //사진 탭
                MainActivity.selectList.clear();
                photoFragment = new SendTabPhotoFragment();
                return photoFragment;
            case 2:
                //앨범 탭
                MainActivity.selectList.clear();
//                albumFragment = new SendTabAlbumFragment();
//                return albumFragment;

                //임시
                fileFragment = new SendTabFileFragment();
                return fileFragment;

            case 3:
                //영상 탭
                videoFragment = new SendTabVideoFragment();
                return videoFragment;
            case 4:
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
