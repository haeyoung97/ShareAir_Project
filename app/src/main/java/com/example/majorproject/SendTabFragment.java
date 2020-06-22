package com.example.majorproject;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class SendTabFragment extends Fragment {

    private TabLayout tabLayout;
    static CustomViewPager viewPager;
    private MainActivity mainActivity;
    private Context context;
    private Button sendButton;
    private Button recvButton;
    private ButtonEventListener buttonEventListener;

    static LinearLayout dynamicLinearLayout;
    static LinearLayout tabStrip;
//    static boolean selecting;

    private int prevTabPosition;

    public SendTabFragment(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.send_frame_fragment, container, false);


//        Log.d("TAG: ", thread.getImagefiles().get(1).getAbsolutePath());
        this.context = mainActivity.getApplicationContext();

//
//        sendButton = (Button)view.findViewById(R.id.send_frame_transferButton);
//        recvButton = (Button)view.findViewById(R.id.recv_frame_transferButton);

        tabLayout = (TabLayout)view.findViewById(R.id.send_frame_tablayout);
//        viewPager = (ViewPager)view.findViewById(R.id.send_frame_viewpager);
        viewPager = (CustomViewPager)view.findViewById(R.id.send_frame_viewpager);
        dynamicLinearLayout = (LinearLayout)view.findViewById(R.id.send_dynamic_linearlayout);
        tabStrip = ((LinearLayout)tabLayout.getChildAt(0));

        buttonEventListener = new ButtonEventListener(mainActivity, context);
//        sendButton.setOnClickListener(buttonEventListener);
//        recvButton.setOnClickListener(buttonEventListener);

        SendTabViewPagerAdapter adapter = new SendTabViewPagerAdapter(getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        prevTabPosition = viewPager.getCurrentItem();
       // tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {



            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                MainActivity.selectList.clear();
                if(MainActivity.selectList.size() != 0) {
//                    MainActivity.selectList.clear();
//                    viewPager.beginFakeDrag();
                    Log.e("tab size not null", "");
                    viewPager.setPagingEnabled(false);
                    resizePager(prevTabPosition);
                    viewPager.setCurrentItem(prevTabPosition);
                 }
                else {
                    Log.e("tab size null", "");
                    viewPager.setPagingEnabled(true);
                    resizePager(tab.getPosition());
                    viewPager.setCurrentItem(tab.getPosition());
                    prevTabPosition = viewPager.getCurrentItem();
                }
//                    viewPager.endFakeDrag();
//                if(dynamicLinearLayout != null){
//                    dynamicLinearLayout.removeAllViews();
//                }
                Log.d("tabLayout : ", String.valueOf(tab.getPosition()));


//                modifyTab = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
//                MainActivity.selectList.clear();
                Log.d("reselected : ", Integer.toString(tab.getPosition()));
                viewPager.setCurrentItem(tab.getPosition());

            }
        });
        adapter.notifyDataSetChanged();
        return view;
    }

    private void resizePager(int position) {
        View view = viewPager.findViewWithTag(position);
        if(view == null)
            return;
        view.measure(ViewPager.LayoutParams.WRAP_CONTENT, ViewPager.LayoutParams.WRAP_CONTENT);
        int width = view.getMeasuredWidth();
        int height = view.getMeasuredHeight();

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
    }
}
