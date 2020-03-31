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

import java.util.ArrayList;
import java.util.Date;

public class SendTabAlbumFragment extends Fragment {

    private RecyclerView albumRecyclerview;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<SendAlbum> sendAlbumArrayList;
    private SendAlbumRecyclerViewAdapter albumRecyclerViewAdapter;

    private int pictureCount = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.send_viewpager_recyclerview, container, false);
        Log.d("sendAlbumFragment :", "Yes");

        sendAlbumArrayList = new ArrayList<>();

        albumRecyclerview = (RecyclerView) view.findViewById(R.id.send_recyclerview);
        layoutManager = new LinearLayoutManager(this.getContext());
        albumRecyclerview.setLayoutManager(layoutManager);
        albumRecyclerViewAdapter = new SendAlbumRecyclerViewAdapter(sendAlbumArrayList, getActivity());
        albumRecyclerview.setAdapter(albumRecyclerViewAdapter);

        ArrayList<SendAlbum> result = queryAllAlbum();
        albumRecyclerViewAdapter.setItems(result);
        albumRecyclerViewAdapter.notifyDataSetChanged();
        return view;
    }
    private ArrayList<SendAlbum> queryAllAlbum(){
        ArrayList<SendAlbum> result = new ArrayList<>();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = { MediaStore.Images.Media._ID, MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media._COUNT};


        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, MediaStore.MediaColumns.DATE_ADDED + " DESC");

        int colDataIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
        int colNameIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        int colIdIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_ID);
        int colCntIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._COUNT);

        pictureCount = 0;
        while(cursor.moveToNext())
        {
            String path = cursor.getString(colDataIndex);
            String displayName = cursor.getString(colNameIndex);
            String albumId = cursor.getString(colIdIndex);
            int albumCount = cursor.getInt(colCntIndex);

            if(!TextUtils.isEmpty(path)){
                SendAlbum sendVideo = new SendAlbum(albumId, displayName, albumCount);
                result.add(sendVideo);
            }
            pictureCount++;
        }
        Log.d("Picturecount : ", Integer.toString(pictureCount));

        for(SendAlbum sendAlbum : result){
            Log.d("SendAlbum : ", sendAlbum.toString());
        }
        return result;
    }

//    private void AlbumData(){
//        if(MainActivity.albumList.size() == 0){
//            return;
//        }
//        for(int i = 0; i < MainActivity.albumList.size(); i++){
//            Log.d("albumData : ", MainActivity.albumList.get(i).getAlbumName());
//            Log.d("albumIdx : ", Integer.toString(MainActivity.albumList.get(i).getStartIdx()));
//            String path = MainActivity.imageList.get(MainActivity.albumList.get(i).getStartIdx() - 1).getFile().getAbsolutePath();
//            String name = MainActivity.albumList.get(i).getAlbumName();
//            int sum = MainActivity.albumList.get(i).getAlbumCnt();
//            sendAlbumArrayList.add(new SendAlbum(name, sum, path));
//            // sendPhotoArrayList.get(i).setImagePath(imageFile.get(i).getAbsolutePath());
//            sendAlbumArrayList.get(i).setIndex(i);
//            Log.d("sendarray ", sendAlbumArrayList.get(i).getImagePath());
//        }
//    }
}
