package com.example.majorproject;

import android.app.IntentService;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;


//image file load thread

public class LoadFiles extends Thread {
    private ArrayList<File> imagefiles;
    private ArrayList<AlbumNode> albumList;
    private File file;
    private String externalPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
    private int imagecnt = 0;
    private int AlbumIdx = 0;

    public LoadFiles() {
        imagefiles = new ArrayList<>();
        albumList = new ArrayList<AlbumNode>();
    }

    @Override
    public void run() {
        super.run();
        Log.d("path : ", externalPath);
        ImagePathArrayCount(externalPath);
        Log.d("imageCnt : ", Integer.toString(imagecnt));

    }

    public class AlbumNode{
        private String albumName;
        private int albumCnt;
        private int startIdx;

        public AlbumNode(String albumName, int albumCnt, int startIdx){
            this.albumName = albumName;
            this.albumCnt = albumCnt;
            this.startIdx = startIdx;
        }

        public String getAlbumName() {
            return albumName;
        }

        public void setAlbumName(String albumName) {
            this.albumName = albumName;
        }

        public int getAlbumCnt() {
            return albumCnt;
        }

        public void setAlbumCnt(int albumCnt) {
            this.albumCnt = albumCnt;
        }

        public int getStartIdx() {
            return startIdx;
        }

        public void setStartIdx(int startIdx) {
            this.startIdx = startIdx;
        }
    }

    public int ImagePathArrayCount(String path){
        int cnt = 0;


        file = new File(path);
        if(file.isDirectory()){
            Log.d("filed", "dir");
        }
        File [] files = file.listFiles();
        //Log.d("filed", files[0].getName());

        String innerpath;


        for(int i = 0; i < files.length; i++){
            if(files[i].isDirectory()){
                Log.d("Directory "+ i + " : ", files[i].getName());
                innerpath = files[i].getAbsolutePath();
                int tmp = imagecnt;
                imagecnt += ImagePathArrayCount(innerpath);
                Log.d("i : ", Integer.toString(i));
                if(imagecnt - tmp == 0){
                    continue;
                }
                albumList.add(new AlbumNode(files[AlbumIdx].getName(), imagecnt-tmp, imagecnt));

                Log.d("innercnt : ", Integer.toString(imagecnt));
                Log.d("innerpath : ", innerpath);
                Log.d("i : ", Integer.toString(i));
                Log.d("fileName : ", files[i].getName());
                Log.d("albumName : ", albumList.get(AlbumIdx).getAlbumName());
                Log.d("albumCnt : ", Integer.toString(albumList.get(AlbumIdx).getAlbumCnt()));
                AlbumIdx++;
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

    public ArrayList<AlbumNode> getAlbumList() {
        return albumList;
    }
}
