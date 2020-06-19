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
import android.webkit.MimeTypeMap;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SendTabRecentFragment extends Fragment {
    private RecyclerView recentRecyclerview;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<SendRecent> sendRecentArrayList;
    private SendRecentRecyclerViewAdapter recentRecyclerViewAdapter;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private LinearLayout dynamicLinearLayout;

    private int pictureCount = 0;

    //private ArrayList<File> imageFile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.send_viewpager_recyclerview, container, false);
        //imageFile = MainActivity.imageList;
        sendRecentArrayList = new ArrayList<SendRecent>();

//       recentData();
       recentRecyclerview = (RecyclerView) view.findViewById(R.id.send_recyclerview);
       layoutManager = new LinearLayoutManager(this.getContext());
//        layoutManager = new GridLayoutManager(this.getContext(), 3);
        recentRecyclerview.setLayoutManager(layoutManager);
        dynamicLinearLayout = (LinearLayout)view.findViewById(R.id.send_dynamic_linearlayout);
        recentRecyclerViewAdapter = new SendRecentRecyclerViewAdapter(sendRecentArrayList, getActivity(), dynamicLinearLayout);
        recentRecyclerview.setAdapter(recentRecyclerViewAdapter);

        ArrayList<SendRecent> result = queryAllFiles();
        recentRecyclerViewAdapter.setItems(result);
        recentRecyclerViewAdapter.notifyDataSetChanged();
        return view;
    }
    private ArrayList<SendRecent> queryAllFiles(){
        ArrayList<SendRecent> result = new ArrayList<>();
        Uri uri = MediaStore.Files.getContentUri("external");
        Log.d("recent Query!", "Y");
        String[] projection = {
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.DISPLAY_NAME,
                MediaStore.Files.FileColumns.DATE_ADDED,
                MediaStore.Files.FileColumns.MIME_TYPE
        };
        String selection = MediaStore.Files.FileColumns.MIME_TYPE + "=?" + ""
                + "OR "
                + MediaStore.Files.FileColumns.MIME_TYPE + "=?" + ""
                +"OR "
                + MediaStore.Files.FileColumns.MIME_TYPE + "=?" + ""
                +"OR "
                + MediaStore.Files.FileColumns.MIME_TYPE + "=?" + ""
                +"OR "
                + MediaStore.Files.FileColumns.MIME_TYPE + "=?" + ""
                +"OR "
                + MediaStore.Files.FileColumns.MEDIA_TYPE;
//        String[] selectionnArgs = new String[]{MimeTypeMap.getSingleton().getMimeTypeFromExtension("txt")};
        String[] selectionArgs = new String[]{
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("hwp"),
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("doc"),
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("xlsx"),
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("pptx"),
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("pdf")
        };

//        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
//                + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
//                + " OR "
//                + MediaStore.Files.FileColumns.MEDIA_TYPE + "="
//                + MediaStore.Files.FileColumns.MEDIA_TYPE_NONE;

        Cursor cursor = getActivity().getContentResolver().query(uri, projection, selection, selectionArgs, MediaStore.Files.FileColumns.DATE_ADDED +" DESC");

        int colDataIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);
        int colNameIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME);
       // int colNameIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.TITLE);
        int colDateIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_ADDED);
        int colMimeIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MIME_TYPE);

        pictureCount = 0;
        while(cursor.moveToNext())
        {
            Log.d("recent move", "y");
            String path = cursor.getString(colDataIndex);
            String displayName = cursor.getString(colNameIndex);
            String outDate = cursor.getString(colDateIndex);
            String ext = MimeTypeMap.getSingleton().getExtensionFromMimeType(cursor.getString(colMimeIndex));
//            String ext = MimeTypeMap.getSingleton().getMimeTypeFromExtension(cursor.getString(colMimeIndex));
            Log.d("path : ", path);
//            Log.d("Extension : ", ext);
            String addedDate = dateFormat.format(new Date(new Long(outDate).longValue()*1000L));

            if(ext == null){
                ext="not";
            }
            if(!TextUtils.isEmpty(path)){
                SendRecent sendRecent = new SendRecent(path, ext, displayName, addedDate);
                result.add(sendRecent);
            }
            pictureCount++;
        }
        Log.d("recentPicturecount : ", Integer.toString(pictureCount));

        for(SendRecent sendRecent : result){
            Log.d("SendRecent : ", sendRecent.toString());
        }
        return result;
    }



//    private void recentData(){
//        if(MainActivity.sortedFileList.size() == 0){
//            return;
//        }
//        for(int i = 0; i < 15; i++){
//            Log.d("RecentData : ", MainActivity.sortedFileList.get(i).getFile().getAbsolutePath());
//            String path = MainActivity.sortedFileList.get(i).getFile().getAbsolutePath();
//            String name = MainActivity.sortedFileList.get(i).getFile().getName();
//            Date date = new Date(MainActivity.sortedFileList.get(i).getFile().lastModified());
//            int extNum = MainActivity.sortedFileList.get(i).getFileExt();
//            Log.d("recentExtnum : ", Integer.toString(extNum));
//            String strDate = sdf.format(date);
//            sendRecentArrayList.add(new SendRecent(path, name, strDate, extNum));
//           // sendRecentArrayList.get(i).setImagePath(imageFile.get(i).getAbsolutePath());
//            sendRecentArrayList.get(i).setIndex(i);
//            Log.d("sendRecentarray ", sendRecentArrayList.get(i).getFilePath());
//            Log.d("sendRecentArr : ", Integer.toString(sendRecentArrayList.get(i).getFileExtNum()));
//        }
//    }
}
