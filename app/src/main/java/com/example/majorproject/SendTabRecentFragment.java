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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SendTabRecentFragment extends Fragment {
    private RecyclerView recentRecyclerview;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<SendRecent> sendRecentArrayList;
    private SendRecentRecyclerViewAdapter recentRecyclerViewAdapter;
    private SimpleDateFormat sdf;

    //private ArrayList<File> imageFile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.send_viewpager_recyclerview, container, false);

        sdf = new SimpleDateFormat("yyyy-MM-dd");
        //imageFile = MainActivity.imageList;
        sendRecentArrayList = new ArrayList<SendRecent>();

       recentData();
       recentRecyclerview = (RecyclerView) view.findViewById(R.id.send_recyclerview);
       layoutManager = new LinearLayoutManager(this.getContext());
//        layoutManager = new GridLayoutManager(this.getContext(), 3);
        recentRecyclerview.setLayoutManager(layoutManager);
        recentRecyclerViewAdapter = new SendRecentRecyclerViewAdapter(sendRecentArrayList, getActivity());
        recentRecyclerview.setAdapter(recentRecyclerViewAdapter);
        return view;
    }

    private void recentData(){
        if(MainActivity.sortedFileList.size() == 0){
            return;
        }
        for(int i = 0; i < 15; i++){
            Log.d("RecentData : ", MainActivity.sortedFileList.get(i).getFile().getAbsolutePath());
            String path = MainActivity.sortedFileList.get(i).getFile().getAbsolutePath();
            String name = MainActivity.sortedFileList.get(i).getFile().getName();
            Date date = new Date(MainActivity.sortedFileList.get(i).getFile().lastModified());
            int extNum = MainActivity.sortedFileList.get(i).getFileExt();
            Log.d("recentExtnum : ", Integer.toString(extNum));
            String strDate = sdf.format(date);
            sendRecentArrayList.add(new SendRecent(path, name, strDate, extNum));
           // sendRecentArrayList.get(i).setImagePath(imageFile.get(i).getAbsolutePath());
            sendRecentArrayList.get(i).setIndex(i);
            Log.d("sendRecentarray ", sendRecentArrayList.get(i).getFilePath());
            Log.d("sendRecentArr : ", Integer.toString(sendRecentArrayList.get(i).getFileExtNum()));
        }
    }
}
