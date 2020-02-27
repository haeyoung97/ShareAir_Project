package com.example.majorproject;

import java.io.File;

public class FileNode {
    private File file;
    private int fileExt;

    public FileNode(File file, int fileExt) {
        this.file = file;
        this.fileExt = fileExt;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getFileExt() {
        return fileExt;
    }

    public void setFileExt(int fileExt) {
        this.fileExt = fileExt;
    }
}
