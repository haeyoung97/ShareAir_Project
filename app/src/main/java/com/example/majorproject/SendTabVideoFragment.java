package com.example.majorproject;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
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

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;

public class SendTabVideoFragment extends Fragment {
    private RecyclerView videoRecyclerview;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<SendVideo> sendVideosArrayList;
    private SendVideoRecyclerViewAdapter videoRecyclerViewAdapter;
    private int pictureCount = 0;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.send_viewpager_recyclerview, container, false);
        videoRecyclerview = (RecyclerView) view.findViewById(R.id.send_recyclerview);
         layoutManager = new LinearLayoutManager(this.getContext());
//        layoutManager = new GridLayoutManager(this.getContext(), 3);
        videoRecyclerview.setLayoutManager(layoutManager);
        videoRecyclerViewAdapter = new SendVideoRecyclerViewAdapter(sendVideosArrayList, getActivity());
        videoRecyclerview.setAdapter(videoRecyclerViewAdapter);

        ArrayList<SendVideo> result = queryAllVideo();
        videoRecyclerViewAdapter.setItems(result);
        videoRecyclerViewAdapter.notifyDataSetChanged();
        return view;
    }
    private String convertDuration(int duration){
        int hour = duration/3600;
        int minute = (duration%3600)/60;
        int second = duration%60;
        return Integer.toString(hour) + ":" + Integer.toString(minute) + ":" + Integer.toString(second);
    }

    private ArrayList<SendVideo> queryAllVideo(){
        ArrayList<SendVideo> result = new ArrayList<>();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = { MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DISPLAY_NAME, MediaStore.MediaColumns.DATE_ADDED, MediaStore.Video.Media.DURATION};

        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, MediaStore.MediaColumns.DATE_ADDED + " DESC");

        int colDataIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        int colNameIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME);
        int colDateIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_ADDED);
        int colTLIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);

        pictureCount = 0;
        while(cursor.moveToNext())
        {
            String path = cursor.getString(colDataIndex);
            String displayName = cursor.getString(colNameIndex);
            String outDate = cursor.getString(colDateIndex);
            String duration = convertDuration(colTLIndex);
            String addedDate = dateFormat.format(new Date(new Long(outDate).longValue()*1000L));

            if(!TextUtils.isEmpty(path)){
                SendVideo sendVideo = new SendVideo(path, displayName, addedDate, duration);
                result.add(sendVideo);
            }
            pictureCount++;
        }
        Log.d("Picturecount : ", Integer.toString(pictureCount));

        for(SendVideo sendVideo : result){
            Log.d("SendVideo : ", sendVideo.toString());
        }
        return result;
    }

}
