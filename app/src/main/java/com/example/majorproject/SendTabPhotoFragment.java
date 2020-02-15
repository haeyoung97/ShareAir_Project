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
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class SendTabPhotoFragment extends Fragment {

    private RecyclerView photoRecyclerview;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<SendPhoto> sendPhotoArrayList;
    private SendPhotoRecyclerViewAdapter photoRecyclerViewAdapter;

    private ArrayList<File> imageFile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.send_viewpager_recyclerview, container, false);

        imageFile = MainActivity.imageList;
        sendPhotoArrayList = new ArrayList<SendPhoto>();

        imageData();
        photoRecyclerview = (RecyclerView) view.findViewById(R.id.send_recyclerview);
        // layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager = new GridLayoutManager(this.getContext(), 3);
        photoRecyclerview.setLayoutManager(layoutManager);
        photoRecyclerViewAdapter = new SendPhotoRecyclerViewAdapter(sendPhotoArrayList, getActivity());
        photoRecyclerview.setAdapter(photoRecyclerViewAdapter);
        return view;
    }

    private void imageData(){
        if(imageFile.size() == 0){
            return;
        }
        for(int i = 0; i < 15; i++){
            Log.d("imageData : ", imageFile.get(i).getAbsolutePath());
            sendPhotoArrayList.add(new SendPhoto(imageFile.get(i).getAbsolutePath()));
            // sendPhotoArrayList.get(i).setImagePath(imageFile.get(i).getAbsolutePath());
            sendPhotoArrayList.get(i).setIndex(i);
            Log.d("sendarray ", sendPhotoArrayList.get(i).getImagePath());
        }
    }
}
