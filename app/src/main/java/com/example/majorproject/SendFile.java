package com.example.majorproject;

public class SendFile {
    private String filename;
    private String filepath;
    private String date;
    private int fileExtNum;
    private int resourceFile;
    private boolean check;
    private int index;

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
            default:
                fileExtNum = 6;
                resourceFile = R.drawable.folder;
                break;
        }
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