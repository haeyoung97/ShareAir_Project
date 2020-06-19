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
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SendTabPhotoFragment extends Fragment {

    private RecyclerView photoRecyclerview;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<SendPhoto> sendPhotoArrayList;
    private SendPhotoRecyclerViewAdapter photoRecyclerViewAdapter;
    private LinearLayout dynamicLinearLayout;

//    private ArrayList<FileNode> imageFile;
    private int pictureCount = 0;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.send_viewpager_recyclerview, container, false);

//        imageFile = MainActivity.imageList;
        sendPhotoArrayList = new ArrayList<SendPhoto>();

//        imageData();
        photoRecyclerview = (RecyclerView) view.findViewById(R.id.send_recyclerview);

        dynamicLinearLayout = (LinearLayout)view.findViewById(R.id.send_dynamic_linearlayout);
        // layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager = new GridLayoutManager(this.getContext(), 3);
        photoRecyclerview.setLayoutManager(layoutManager);

        photoRecyclerViewAdapter = new SendPhotoRecyclerViewAdapter(sendPhotoArrayList, getActivity(), dynamicLinearLayout);
        photoRecyclerview.setAdapter(photoRecyclerViewAdapter);

        ArrayList<SendPhoto> result = queryAllPhoto();
        photoRecyclerViewAdapter.setItems(result);
        photoRecyclerViewAdapter.notifyDataSetChanged();
        return view;
    }

    private ArrayList<SendPhoto> queryAllPhoto(){
        ArrayList<SendPhoto> result = new ArrayList<>();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = { MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DISPLAY_NAME, MediaStore.MediaColumns.DATE_ADDED};

        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, MediaStore.MediaColumns.DATE_ADDED);

        int colDataIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        int colNameIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME);
        int colDateIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_ADDED);

        pictureCount = 0;
        while(cursor.moveToNext())
        {
            String path = cursor.getString(colDataIndex);
            String displayName = cursor.getString(colNameIndex);
            String outDate = cursor.getString(colDateIndex);
            String addedDate = dateFormat.format(new Date(new Long(outDate).longValue()*1000L));

            if(!TextUtils.isEmpty(path)){
                SendPhoto sendPhoto = new SendPhoto(path);
                result.add(sendPhoto);
            }
            pictureCount++;
        }
        Log.d("Picturecount : ", Integer.toString(pictureCount));

        for(SendPhoto sendPhoto : result){
            Log.d("SendPhoto : ", sendPhoto.toString());
        }
        return result;
    }

//    private void imageData(){
//        if(imageFile.size() == 0){
//            return;
//        }
//        for(int i = 0; i < 15; i++){
//            Log.d("imageData : ", imageFile.get(i).getFile().getAbsolutePath());
//            sendPhotoArrayList.add(new SendPhoto(imageFile.get(i).getFile().getAbsolutePath()));
//            // sendPhotoArrayList.get(i).setImagePath(imageFile.get(i).getAbsolutePath());
//            sendPhotoArrayList.get(i).setIndex(i);
//            Log.d("sendarray ", sendPhotoArrayList.get(i).getImagePath());
//        }
//    }
//
}
