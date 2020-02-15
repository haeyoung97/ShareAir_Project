package com.example.majorproject;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class LoadFilesActivity extends Activity {

       @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_load);
           Handler hd = new Handler();

           LoadDocumentFiles documentThread;
           documentThread = new LoadDocumentFiles();

           documentThread.start();
           try {
               documentThread.join();
               MainActivity.sortedFileList.addAll(MainActivity.imageList);

               MainActivity.sortedFileList.addAll(MainActivity.documentList);
               Collections.sort(MainActivity.sortedFileList, new Comparator<File>() {
                   @Override
                   public int compare(File o1, File o2) {
                       Date d1 = new Date(o1.lastModified());
                       Date d2 = new Date(o2.lastModified());
                       return -(d1.compareTo(d2));
                   }
               });

               for(int i = 0; i < MainActivity.imageList.size()/2; i++){
                   Log.d("LoadImage??? : ", MainActivity.imageList.get(i).getAbsolutePath());
               }
               hd.postDelayed(new splashhandler(), 10000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }

       }
    private class splashhandler implements Runnable{

        @Override
        public void run() {
            startActivity(new Intent(getApplication(), MainActivity.class));
            LoadFilesActivity.this.finish();
        }
    }
}
