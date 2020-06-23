package com.example.majorproject;

public class SendFile {
    private String filename;
    private String filepath;
    private String date;
    private int fileExtNum;
    private int resourceFile;
    private boolean check;
    private int index;
    private boolean isSelected = false;

    public SendFile(String filepath, String extension, String filename, String date) {
        this.filepath = filepath;
        this.filename = filename;
        this.date = date;
        run(extension);
    }

    private String[] stringExt= {".hwp", ".doc", ".xlsx", ".pptx", ".pdf"};

    public int getResourceFile() {
        return resourceFile;
    }

    public void setResourceFile(int resourceFile) {
        this.resourceFile = resourceFile;
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
            case "xml":
                fileExtNum = 4;
                resourceFile = R.drawable.xml;
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
            case "mp3":
                fileExtNum = 4;
                resourceFile = R.drawable.mp3;
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

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
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

    public int getFileExtNum() {
        return fileExtNum;
    }

    public void setFileExtNum(int fileExtNum) {
        this.fileExtNum = fileExtNum;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
