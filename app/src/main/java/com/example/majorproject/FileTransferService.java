// Copyright 2011 Google Inc. All Rights Reserved.

package com.example.majorproject;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A service that process each file transfer request i.e Intent by opening a
 * socket connection with the WiFi Direct Group Owner and writing the file
 */
public class FileTransferService extends IntentService {

    private static final int SOCKET_TIMEOUT = 10000;
    public String EXTRAS_FILE_PATH = "file_url";
    public String EXTRAS_GROUP_OWNER_ADDRESS = "go_host";
    public String EXTRAS_GROUP_OWNER_PORT = "go_port";
    public int port;
    private String filepath;
    private JSch jsch;
    private Session session;
    private ChannelSftp channel;
    HistoryDatabase historyDatabase;

    private Context context;

    public FileTransferService(Context context) {
        super("FileTransferService");
        this.context = context;
        historyDatabase = new HistoryDatabase(this, MainActivity.dbName, null, MainActivity.dbVersion);
    }

    public FileTransferService() {
        super("FileTransferService");
    }

    void setEXTRAS_FILE_PATH(String EXTRAS_FILE_PATH){
        this.EXTRAS_FILE_PATH = EXTRAS_FILE_PATH;
    }

    void setEXTRAS_GROUP_OWNER_ADDRESS(String EXTRAS_GROUP_OWNER_ADDRESS){
        this.EXTRAS_GROUP_OWNER_ADDRESS = EXTRAS_GROUP_OWNER_ADDRESS;
    }

    void setEXTRAS_GROUP_OWNER_PORT(String EXTRAS_GROUP_OWNER_PORT){
        this.EXTRAS_GROUP_OWNER_PORT = EXTRAS_GROUP_OWNER_PORT;
        this.port = Integer.parseInt(EXTRAS_GROUP_OWNER_PORT);
    }
    /*
     * (non-Javadoc)
     * @see android.app.IntentService#onHandleIntent(android.content.Intent)
     */
    @Override
    protected void onHandleIntent(Intent intent) {

    }

    public void connectionSocket(String filepath) {
        Log.e("JSch-connectionSocket", ": now fuction = connectionSocket");
        Log.e("JSch-Path", EXTRAS_FILE_PATH);
        Log.e("JSch-Address", EXTRAS_GROUP_OWNER_ADDRESS);
        Log.e("JSch-port", EXTRAS_GROUP_OWNER_PORT);
        this.filepath = filepath;
        if (context == null) {
            Log.e("JSch-connectionSocket", ": getApplicationContext = null");
        }
        new Thread() {
            public void run() {
                threadConnect();
            }
        }.start();
    }
    public void threadConnect(){
        Log.e("JSch-threadConnect", ": hello");
       // Context context = getApplicationContext();
//        JSch jsch = new JSch();
//        Session session;
//        ChannelSftp channel;
        //Socket socket = new Socket();
        String sql;
        SQLiteDatabase db;

        try {
            Log.d(WiFiDirectActivity.TAG, "Opening client socket - ");
//            connect();
//            String filepath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
            // 다운로드 파일의 임시 이름 설정
            upload(filepath);
            db = historyDatabase.getWritableDatabase();
            String filename, date, device;
            int size, isSuccess;
//            if(DeviceDetailFragment.copyFile(is, stream)){
//
////                sql = String.format("INSERT INTO history VALUES('" + )
//            }
            Log.d(WiFiDirectActivity.TAG, "Client: Data written");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
        }

    }
    protected void upload(String filepath) throws Exception{
        FileInputStream fis = null;
        connect();
        try {
            // Upload file
            File file = new File(filepath);
            // Change to output directory
            channel.cd(file.getParent());
            // 입력 파일을 가져온다.
            fis = new FileInputStream(file);
            // 파일을 업로드한다.
            channel.put(fis, file.getName());

            fis.close();
            System.out.println("File uploaded successfully - "+ file.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
        disconnect();
    }
    public void connect() throws JSchException {
        jsch = new JSch();
        Log.d("JSCH-upload", "connect: "+ port);
        session = jsch.getSession("Sender", EXTRAS_GROUP_OWNER_ADDRESS, port);
        session.setPassword("password");
        session.connect();

        channel = (ChannelSftp) session.openChannel("sftp");
        channel.connect();
    }
    public void disconnect() {
        if(session.isConnected()){
            System.out.println("disconnecting...");
            channel.disconnect();
            channel.disconnect();
            session.disconnect();
        }
    }

}
