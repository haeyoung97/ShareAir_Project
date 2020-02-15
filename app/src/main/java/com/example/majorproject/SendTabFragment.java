package com.example.majorproject;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class SendTabFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MainActivity mainActivity;
    private Context context;
    private Button sendButton;
    private Button recvButton;
    private ButtonEventListener buttonEventListener;

    public SendTabFragment(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.send_frame_fragment, container, false);


//        Log.d("TAG: ", thread.getImagefiles().get(1).getAbsolutePath());
        this.context = mainActivity.getApplicationContext();


        sendButton = (Button)view.findViewById(R.id.send_frame_transferButton);
        recvButton = (Button)view.findViewById(R.id.recv_frame_transferButton);

        tabLayout = (TabLayout)view.findViewById(R.id.send_frame_tablayout);
        viewPager = (ViewPager)view.findViewById(R.id.send_frame_viewpager);

        buttonEventListener = new ButtonEventListener(mainActivity, context);
        sendButton.setOnClickListener(buttonEventListener);
        recvButton.setOnClickListener(buttonEventListener);

        SendTabViewPagerAdapter adapter = new SendTabViewPagerAdapter(getChildFragmentManager(), tabLayout.getTabCount());
        if(adapter == null){
            Log.d("adapter null? : ", "null!");
        }
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

       // tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                resizePager(tab.getPosition());
                Log.d("tabLayout : ", String.valueOf(tab.getPosition()));
               viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }
        });
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
