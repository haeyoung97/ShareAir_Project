package com.example.majorproject;

import java.io.File;

public class FileNode {
//    private File file;
//    private int fileExt;
    private String FilePath;
    private int FileKind;

    public FileNode(String filePath, int fileKind) {
        FilePath = filePath;
        FileKind = fileKind;
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
