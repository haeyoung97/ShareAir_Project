package com.example.majorproject;

import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class SendTabVideoFragment extends Fragment {
    private RecyclerView videoRecyclerview;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<SendVideo> sendVideosArrayList;
    private SendVideoRecyclerViewAdapter videoRecyclerViewAdapter;
    private int pictureCount = 0;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private LinearLayout dynamicLinearLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.send_video_recyclerview, container, false);
        videoRecyclerview = (RecyclerView) view.findViewById(R.id.send_video_recyclerview);
         layoutManager = new LinearLayoutManager(this.getContext());
        videoRecyclerview.setLayoutManager(layoutManager);
//        dynamicLinearLayout = (LinearLayout)view.findViewById(R.id.send_dynamic_linearlayout);
        dynamicLinearLayout = SendTabFragment.dynamicLinearLayout;
        Log.e("dynamic_video?","");
//        dynamicLinearLayout.removeAllViews();
        videoRecyclerViewAdapter = new SendVideoRecyclerViewAdapter(sendVideosArrayList, getActivity(), dynamicLinearLayout);
        videoRecyclerview.setAdapter(videoRecyclerViewAdapter);

        ArrayList<SendVideo> result = queryAllVideo();
        videoRecyclerViewAdapter.setItems(result);
        videoRecyclerViewAdapter.notifyDataSetChanged();
        return view;
    }
    private String getPlayTime(String path) {
        String result;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(path);
        String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        long timeInmillisec = Long.parseLong( time );
        long duration = timeInmillisec / 1000;
        long hours = duration / 3600;
        long minutes = (duration - hours * 3600) / 60;
        long seconds = duration - (hours * 3600 + minutes * 60);
        String h, m, s;
        if(hours == 0){
            h = "";
        }else if(hours < 10){
            h = "0" + hours + " : ";
        }else{
            h = String.valueOf(hours) + " : ";
        }

        if(minutes == 0){
            m = "00" + " : ";
        }else if(minutes < 10){
            m = "0" + minutes + " : ";
        }else{
            m = String.valueOf(minutes) + " : ";
        }

        if(seconds == 0){
            s = "00";
        }else if(seconds < 10){
            s = "0" + seconds;
        }else{
            s = String.valueOf(seconds);
        }
        result = h + m + s;
        return result;
    }


    private ArrayList<SendVideo> queryAllVideo(){
        ArrayList<SendVideo> result = new ArrayList<>();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = { MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DISPLAY_NAME, MediaStore.MediaColumns.DATE_ADDED, MediaStore.Video.Media.DURATION};

        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, MediaStore.MediaColumns.DATE_ADDED + " DESC");

        int colDataIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        int colNameIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME);
        int colDateIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_ADDED);

        pictureCount = 0;
        while(cursor.moveToNext())
        {
            String path = cursor.getString(colDataIndex);
            String displayName = cursor.getString(colNameIndex);
            String outDate = cursor.getString(colDateIndex);
            String duration = getPlayTime(path);
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
