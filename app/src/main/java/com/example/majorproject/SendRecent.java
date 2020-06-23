package com.example.majorproject;

import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

public class SendRecent {
    private String filePath;
    private String thumbPath;
    private String filename;
    private String date;
//    private boolean check;
    private boolean isSelected = false;
    private int index;
    private int fileExtNum;
    private int resourceFile;
    private String fileExtension;

    private String[] stringExt= {".hwp", ".doc", ".xlsx", ".pptx", ".pdf"};

    private String[] thumbPathArr
            = {getURLForResource(R.drawable.hwp),
            getURLForResource(R.drawable.doc),
            getURLForResource(R.drawable.xls),
            getURLForResource(R.drawable.ppt),
            getURLForResource(R.drawable.pdf)};
//    private String[] thumbPathArr
//            = {getURLForResource("hwp.jpg"),
//               getURLForResource("doc.png"),
//               getURLForResource("xls.png"),
//               getURLForResource("ppt.png"),
//               getURLForResource("pdf.png")};

    public SendRecent(String filePath, String fileExtension, String filename, String date) {
        this.filePath = filePath;
        this.fileExtension = fileExtension;
        this.filename = filename;
        this.date = date;
        run(fileExtension);
//        run(fileExtNum);

    }

    public int getResourceFile(){
        return resourceFile;
    }
    public SendRecent(String filePath, String fileExtension) {
        this.filePath = filePath;
        this.fileExtension = fileExtension;
    }

    ///////////////////////////////////////////////////////여기부분
    private String getURLForResource(int Extension){
//        return Uri.parse("android.resource://" + R.class.getPackage().getName() + "/drawable/" + resId).toString();
//        return "data/user/0/" + R.class.getPackage().getName() + "res/drawable/"+Extension;
        return Uri.parse("android.resource://" + R.class.getPackage().getName() + "/drawable-v24/" + Extension).toString();
    }

    public void run(String extension){
        switch(extension){
            case "hwp":
                fileExtNum = 0;
                resourceFile = R.drawable.hwp;
                break;
            case "doc":
                fileExtNum = 1;
                resourceFile = R.drawable.doc;
                break;
            case "xlsx":
            case "xls":
                fileExtNum = 2;
                resourceFile = R.drawable.xls;
                break;
            case "pptx":
            case "ppt":
                fileExtNum = 3;
                resourceFile = R.drawable.ppt;
                break;
            case "pdf":
                fileExtNum = 4;
                resourceFile = R.drawable.pdf;
                break;
            case "xml":
                fileExtNum = 4;
                resourceFile = R.drawable.xml;
                break;
            case "mp3":
                fileExtNum = 4;
                resourceFile = R.drawable.mp3;
                break;
            case "mp4":
                fileExtNum = 4;
                resourceFile = R.drawable.mp4;
                break;
            case "jpeg":
            case "jpg":
            case "png":
            case "bmp":
            case "gif":
                fileExtNum = 5;
                break;
            default:
                fileExtNum = 6;
                resourceFile = R.drawable.extra;
                break;
        }
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getFileExtNum() {
        return fileExtNum;
    }

    public void setFileExtNum(int fileExtNum) {
        this.fileExtNum = fileExtNum;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
//
//    public boolean isCheck() {
//        return check;
//    }
//
//    public void setCheck(boolean check) {
//        this.check = check;
//    }
}
