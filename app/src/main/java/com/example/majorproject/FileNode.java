package com.example.majorproject;

import java.io.File;

public class FileNode {
//    private File file;
//    private int fileExt;
    private String FilePath;
    private int FileKind;
    private int fileIdx;
    private int fileTab;

    public FileNode(String filePath, int fileKind) {
        FilePath = filePath;
        FileKind = fileKind;
    }

    public FileNode(String filePath, int fileKind, int fileIdx, int fileTab) {
        FilePath = filePath;
        FileKind = fileKind;
        this.fileIdx = fileIdx;
        this.fileTab = fileTab;
    }

    public int getFileTab() {
        return fileTab;
    }

    public void setFileTab(int fileTab) {
        this.fileTab = fileTab;
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }

    public int getFileKind() {
        return FileKind;
    }

    public void setFileKind(int fileKind) {
        FileKind = fileKind;
    }

    public int getFileIdx() {
        return fileIdx;
    }

    public void setFileIdx(int fileIdx) {
        this.fileIdx = fileIdx;
    }

    //    public FileNode(File file, int fileExt) {
//        this.file = file;
//        this.fileExt = fileExt;
//    }
//
//    public File getFile() {
//        return file;
//    }
//
//    public void setFile(File file) {
//        this.file = file;
//    }
//
//    public int getFileExt() {
//        return fileExt;
//    }
//
//    public void setFileExt(int fileExt) {
//        this.fileExt = fileExt;
//    }
}
