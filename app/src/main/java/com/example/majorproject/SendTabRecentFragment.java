package com.example.majorproject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class SendTabRecentFragment extends Fragment {
    private RecyclerView recentRecyclerview;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<SendRecent> sendRecentArrayList;
    private SendRecentRecyclerViewAdapter recentRecyclerViewAdapter;

    private ArrayList<File> imageFile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.send_viewpager_recyclerview, container, false);

        imageFile = MainActivity.imageList;
        sendRecentArrayList = new ArrayList<SendRecent>();

        imageData();
       recentRecyclerview = (RecyclerView) view.findViewById(R.id.send_recyclerview);
       // layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager = new GridLayoutManager(this.getContext(), 3);
        recentRecyclerview.setLayoutManager(layoutManager);
        recentRecyclerViewAdapter = new SendRecentRecyclerViewAdapter(sendRecentArrayList, getActivity());
        recentRecyclerview.setAdapter(recentRecyclerViewAdapter);
        return view;
    }

    private void imageData(){
        for(int i = 0; i < 20; i++){
            Log.d("imageData : ", imageFile.get(i).getAbsolutePath());
            sendRecentArrayList.add(new SendRecent(imageFile.get(i).getAbsolutePath()));
            Log.d("sendarray ", sendRecentArrayList.get(i).getImagePath());
        }
    }
}
