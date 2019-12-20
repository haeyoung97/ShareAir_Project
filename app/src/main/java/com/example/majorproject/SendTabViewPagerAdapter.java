package com.example.majorproject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class SendTabViewPagerAdapter extends FragmentStatePagerAdapter {
    private int mPageCount;

    private SendTabRecentFragment recentFragment;


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
                return recentFragment;
            case 1:
                //사진 탭
                return null;
            case 2:
                //앨범 탭
                return null;
            case 3:
                //영상 탭
                return null;
            case 4:
                //파일 탭
                return null;
            default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return 0;
    }
}
