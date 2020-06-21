package com.example.majorproject;

public class HistoryDBArray {
    private String deviceName;
    private String fileName;
    private String date;
    private String kind;
    private String sucOrFail;
    private boolean check;

    public HistoryDBArray(String date, String deviceName, String fileName, String kind, String sucOrFail) {
        this.deviceName = deviceName;
        this.fileName = fileName;
        this.kind = kind;
        this.date = date;
        this.sucOrFail = sucOrFail;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
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