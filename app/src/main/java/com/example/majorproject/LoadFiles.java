package com.example.majorproject;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

public class LoadFiles extends Thread {
    private ArrayList<File> imagefiles;
    private File file;
    private String externalPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Screenshots";
    private int imagecnt = 0;

    public LoadFiles() {
        imagefiles = new ArrayList<>();
    }

    @Override
    public void run() {
        super.run();
        Log.d("path : ", externalPath);
        ImagePathArrayCount(externalPath);
        Log.d("imageCnt : ", Integer.toString(imagecnt));

    }

    public int ImagePathArrayCount(String path){
        int cnt = 0;

        file = new File(path);
        if(file.isDirectory()){
            Log.d("filed", "dir");
        }
        File [] files = file.listFiles();
        Log.d("filed", files[0].getName());

        String innerpath;


        for(int i = 0; i < files.length; i++){
            if(files[i].isDirectory()){
                Log.d("Directory "+ i + " : ", files[i].getName());
                innerpath = files[i].getAbsolutePath();
                imagecnt += ImagePathArrayCount(innerpath);
                Log.d("innercnt : ", Integer.toString(imagecnt));
                Log.d("innerpath : ", innerpath);
            }
            else{
                if(files[i].isFile()) {
                    imagefiles.add(files[i]);
                    Log.d("file " + i + " : ", files[i].getName());
                    cnt++;
                }
            }
        }
        return cnt;
    }

    public ArrayList<File> getImagefiles() {
        return imagefiles;
    }

    public int getImagecnt() {
        return imagecnt;
    }

    public void setImagecnt(int imagecnt) {
        this.imagecnt = imagecnt;
    }
}
