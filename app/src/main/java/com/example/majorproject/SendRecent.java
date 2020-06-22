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
                fileExtNum = 2;
                resourceFile = R.drawable.xls;
                break;
            case "pptx":
                fileExtNum = 3;
                resourceFile = R.drawable.ppt;
                break;
            case "pdf":
                fileExtNum = 4;
                resourceFile = R.drawable.pdf;
                break;
            case "jpeg":
            case "jpg":
            case "png":
            case "bmp":
            case "gif":
                fileExtNum = 5;
                break;
            case "mp4":
                fileExtNum = 7;
                break;
            default:
                fileExtNum = 6;
                resourceFile = R.drawable.folder;
                break;
        }
    }

//    public void run(int fileExtNum){
//        switch(fileExtNum){
//            case 0:
//                // Ext : .hwp
//                thumbPath = thumbPathArr[0];
//                break;
//            case 1:
//                // Ext : .doc
//                thumbPath = thumbPathArr[1];
//                break;
//            case 2:
//                // Ext : .xlsx
//                thumbPath = thumbPathArr[2];
//                break;
//            case 3:
//                // Ext : .pptx
//                thumbPath = thumbPathArr[3];
//                break;
//            case 4:
//                // Ext : .pdf
//                thumbPath = thumbPathArr[4];
//                break;
//            case 5:
//            case 6:
//            case 7:
//                // Ext : image files
//                thumbPath = filePath;
//                break;
//            default:
//                break;
//        }
//
//    }

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
