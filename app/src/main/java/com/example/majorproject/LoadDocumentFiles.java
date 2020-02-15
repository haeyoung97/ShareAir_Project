package com.example.majorproject;

import android.os.Environment;
import android.util.Log;

import androidx.documentfile.provider.DocumentFile;

import java.io.File;
import java.util.ArrayList;

public class LoadDocumentFiles extends Thread {

    private ArrayList<File> documentFiles;
    private File file;
    private String ExternalDocumentPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();
    private String ExternalDownloadPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
    private String ExternalPath = Environment.getExternalStorageDirectory().getAbsolutePath();
    private int fileCnt;

    public LoadDocumentFiles() {
        this.documentFiles = new ArrayList<>();
        Log.d("docPath : ", ExternalDocumentPath);
        documentFiles = MainActivity.documentList;
    }

    @Override
    public void run() {
        super.run();
        DocumentPathArrayCnt(ExternalPath);
//        DocumentPathArrayCnt(ExternalDocumentPath);
//        DocumentPathArrayCnt(ExternalDownloadPath);
    }

    public int DocumentPathArrayCnt(String path)
    {
        int cnt = 0;
        file = new File(path);
        if(file.isDirectory()){
            Log.d("DocDirectory? ", "dir");
        }
        File[] files = file.listFiles();
        String innerPath;

        Log.d("file Length : ", Integer.toString(files.length));

        for(int i = 0; i < files.length; i++){
            if(files[i].isDirectory()) {
                Log.d("DocDirectory " + i + " : ", files[i].getName());
                innerPath = files[i].getAbsolutePath();
                int tmp = fileCnt;
                fileCnt += DocumentPathArrayCnt(innerPath);
                if (fileCnt - tmp == 0) {
                    continue;
                }
            }
            else
            {
                if(files[i].isFile()){
                    if(files[i].getName().endsWith(".hwp") || files[i].getName().endsWith(".doc") || files[i].getName().endsWith(".xlsx") || files[i].getName().endsWith(".pptx") || files[i].getName().endsWith(".pdf"))
                    {
                        documentFiles.add(files[i]);
                        Log.d("file " + i + " : ", files[i].getName());
                        cnt++;
                    }
                }
            }

        }
        return cnt;
    }

    public ArrayList<File> getDocumentFiles() {
        return documentFiles;
    }

    public void setDocumentFiles(ArrayList<File> documentFiles) {
        this.documentFiles = documentFiles;
    }

    public int getFileCnt() {
        return fileCnt;
    }

    public void setFileCnt(int fileCnt) {
        this.fileCnt = fileCnt;
    }
}
