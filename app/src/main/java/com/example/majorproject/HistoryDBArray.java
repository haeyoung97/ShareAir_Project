package com.example.majorproject;

public class HistoryDBArray {
    private String deviceName;
    private String fileSize;
    private String date;
    private String sucOrFail;
    private boolean check;

    public HistoryDBArray(String deviceName, String fileSize, String date, String sucOrFail) {
        this.deviceName = deviceName;
        this.fileSize = fileSize;
        this.date = date;
        this.sucOrFail = sucOrFail;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSucOrFail() {
        return sucOrFail;
    }

    public void setSucOrFail(String sucOrFail) {
        this.sucOrFail = sucOrFail;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
