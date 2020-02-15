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

import java.util.ArrayList;

public class SendTabAlbumFragment extends Fragment {

    private RecyclerView albumRecyclerview;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<SendAlbum> sendAlbumArrayList;
    private SendAlbumRecyclerViewAdapter albumRecyclerViewAdapter;

    private ArrayList<LoadImageFiles.AlbumNode> albumNodes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.send_viewpager_recyclerview, container, false);
        Log.d("sendAlbumFragment :", "Yes");

        albumNodes = MainActivity.albumList;
        sendAlbumArrayList = new ArrayList<>();

        AlbumData();
        albumRecyclerview = (RecyclerView) view.findViewById(R.id.send_recyclerview);
        // layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager = new LinearLayoutManager(this.getContext());
        albumRecyclerview.setLayoutManager(layoutManager);
        albumRecyclerViewAdapter = new SendAlbumRecyclerViewAdapter(sendAlbumArrayList, getActivity());
        albumRecyclerview.setAdapter(albumRecyclerViewAdapter);
        return view;
    }
    private void AlbumData(){
        if(MainActivity.albumList.size() == 0){
            return;
        }
        for(int i = 0; i < MainActivity.albumList.size(); i++){
            Log.d("albumData : ", MainActivity.albumList.get(i).getAlbumName());
            Log.d("albumIdx : ", Integer.toString(MainActivity.albumList.get(i).getStartIdx()));
            String path = MainActivity.imageList.get(MainActivity.albumList.get(i).getStartIdx() - 1).getAbsolutePath();
            String name = MainActivity.albumList.get(i).getAlbumName();
            int sum = MainActivity.albumList.get(i).getAlbumCnt();
            sendAlbumArrayList.add(new SendAlbum(name, sum, path));
            // sendPhotoArrayList.get(i).setImagePath(imageFile.get(i).getAbsolutePath());
            sendAlbumArrayList.get(i).setIndex(i);
            Log.d("sendarray ", sendAlbumArrayList.get(i).getImagePath());
        }
    }
}
