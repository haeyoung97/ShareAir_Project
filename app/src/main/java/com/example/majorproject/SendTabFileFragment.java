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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SendTabFileFragment extends Fragment {
    private RecyclerView fileRecyclerview;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<SendFile> sendFileArrayList;
    private SendFileRecyclerViewAdapter fileRecyclerViewAdapter;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private LinearLayout dynamicLinearLayout;

    private int pictureCount = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.send_file_recyclerview, container, false);

        fileRecyclerview = (RecyclerView) view.findViewById(R.id.send_file_recyclerview);
        layoutManager = new LinearLayoutManager(this.getContext());
        fileRecyclerview.setLayoutManager(layoutManager);
//        dynamicLinearLayout = (LinearLayout)view.findViewById(R.id.send_dynamic_linearlayout);
        dynamicLinearLayout = SendTabFragment.dynamicLinearLayout;
        Log.e("dynamic_file?","");
        fileRecyclerViewAdapter = new SendFileRecyclerViewAdapter(sendFileArrayList, getActivity(), dynamicLinearLayout);
        fileRecyclerview.setAdapter(fileRecyclerViewAdapter);

        ArrayList<SendFile> result = queryAllFiles();
        fileRecyclerViewAdapter.setItems(result);
        fileRecyclerViewAdapter.notifyDataSetChanged();

        return view;
    }
    private ArrayList<SendFile> queryAllFiles(){
        ArrayList<SendFile> result = new ArrayList<>();
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
                + MediaStore.Files.FileColumns.MIME_TYPE + "=?" + "";
//        String[] selectionnArgs = new String[]{MimeTypeMap.getSingleton().getMimeTypeFromExtension("txt")};
        String[] selectionArgs = new String[]{
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("hwp"),
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("doc"),
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("xlsx"),
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("pptx"),
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("pdf")
        };

        Cursor cursor = getActivity().getContentResolver().query(uri, projection, selection, selectionArgs, MediaStore.Files.FileColumns.DATE_ADDED +" DESC");

        int colDataIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);
        int colNameIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME);
        int colDateIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_ADDED);
        int colMimeIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MIME_TYPE);

        pictureCount = 0;
        while(cursor.moveToNext())
        {
            Log.d("file move", "y");
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
                SendFile sendFile = new SendFile(path, ext, displayName, addedDate);
                result.add(sendFile);
            }
            pictureCount++;
        }
        Log.d("filePicturecount : ", Integer.toString(pictureCount));

        for(SendFile sendFile : result){
            Log.d("SendFile : ", sendFile.toString());
        }
        return result;
    }

}
