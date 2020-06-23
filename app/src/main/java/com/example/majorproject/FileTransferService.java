// Copyright 2011 Google Inc. All Rights Reserved.

package com.example.majorproject;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.ProxyHTTP;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SocketFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * A service that process each file transfer request i.e Intent by opening a
 * socket connection with the WiFi Direct Group Owner and writing the file
 */
public class FileTransferService extends IntentService {

    private static final int SOCKET_TIMEOUT = 10000;
    //    public String ACTION_SEND_FILE = "com.example.android.wifidirectactivity.SEND_FILE";
    public String EXTRAS_FILE_PATH = "file_url";
    public String EXTRAS_FILE_EXTENSION = "file_extension";
    //    public ArrayList<String> EXTRAS_FILE_PATH = new ArrayList<>();
    public String EXTRAS_GROUP_OWNER_ADDRESS = "go_host";
    public String EXTRAS_GROUP_OWNER_PORT = "go_port";
    public int FILE_COUNT = 0;
    public int port;

    ArrayList<String> uriString = new ArrayList<>();
    private Context context;

    public FileTransferService(Context context) {
        super("FileTransferService");
        this.context = context;
    }

    public FileTransferService() {
        super("FileTransferService");
    }

    void setEXTRAS_FILE_PATH(String EXTRAS_FILE_PATH){
        this.EXTRAS_FILE_PATH = EXTRAS_FILE_PATH;
    }
    void setEXTRAS_FILE_EXTENSION(String EXTRAS_FILE_EXTENSION){
        this.EXTRAS_FILE_EXTENSION = EXTRAS_FILE_EXTENSION;
    }
    void setFILE_COUNT(int FILE_COUNT){
        this.FILE_COUNT = FILE_COUNT;
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
    public String getExtension(String filepath) {
        Log.e("", "getExtension: " + filepath);
        int index = filepath.lastIndexOf(".");
        String extension = filepath.substring(index + 1);
        return extension;
    }

    public Uri getUriFromPath(String filepath, int fileKind){
        Cursor cursor;
        int id;
        Uri uri = null;
        switch(fileKind){
            case 0:
            case 3:
                //recent, file tab
                cursor = context.getContentResolver().query(MediaStore.Files.getContentUri("external"), null, "_data ='" + filepath + "'", null, null);
                cursor.moveToNext();
                id = cursor.getInt(cursor.getColumnIndex("_id"));
                uri = ContentUris.withAppendedId(MediaStore.Files.getContentUri("external"), id);
                break;
            case 1:
                //photo tab
                cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, "_data ='" + filepath + "'", null, null);
                cursor.moveToNext();
                id = cursor.getInt(cursor.getColumnIndex("_id"));
                uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
                break;
            case 2:
                //video tab
                cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, "_data ='" + filepath + "'", null, null);
                cursor.moveToNext();
                id = cursor.getInt(cursor.getColumnIndex("_id"));
                uri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id);
                break;
            default:
                break;

        }

        return uri;
    }

    public void connectionSocket() {
        int i = 0;
        while(i < MainActivity.selectList.size()) {
            uriString.add(getUriFromPath(MainActivity.selectList.get(i).getFilePath(), MainActivity.selectList.get(i).getFileTab()-1).toString());

            i++;
        }
//        Log.e("JSch-Path", EXTRAS_FILE_PATH);
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
        Socket socket = new Socket();
        FileInputStream fis;
        BufferedInputStream bis;
        String sql;

        try {

            Log.d(WiFiDirectActivity.TAG, "Opening client socket - ");
            socket.bind(null);
            socket.connect((new InetSocketAddress(EXTRAS_GROUP_OWNER_ADDRESS, port)), SOCKET_TIMEOUT);
            Log.d(WiFiDirectActivity.TAG, "Client socket - " + socket.isConnected());
            DataOutputStream stream = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());

            // 파일 개수 전송
            stream.writeInt(FILE_COUNT);
            Log.e("", "FILE_COUNT: "+ String.valueOf(FILE_COUNT));
            int result = 0, i;
            Date current;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String date;
            HistoryDatabase helper;
            for(i = 0; i < FILE_COUNT; i++) {
                // 파일 경로와 파일 확장자 설정
                result = 0;
                setEXTRAS_FILE_PATH(uriString.get(i));
                setEXTRAS_FILE_EXTENSION(getExtension(MainActivity.selectList.get(i).getFilePath()));
                File f = new File(MainActivity.selectList.get(i).getFilePath());

                Log.d(WiFiDirectActivity.TAG, "Client socket - " + socket.isConnected());

                // 파일 확장자 전송
                Log.e("", "threadConnect: 확장자 " + EXTRAS_FILE_EXTENSION);
                stream.writeUTF(EXTRAS_FILE_EXTENSION);

                // 파일 길이 전송
                stream.writeLong(f.length());
                try {
                    int len = 0;
                    byte buf[] = new byte[1024];

                    fis = new FileInputStream(f);
                    Log.d("Uri.parse - ", Uri.parse(EXTRAS_FILE_PATH).toString());
                    while ((len = fis.read(buf)) != -1) {
                        stream.write(buf, 0, len);
                        stream.flush();
                    }
                    current = new Date();
                    date = dateFormat.format(current);
                    Log.e("", "threadConnect: " + date);
                    Log.d(WiFiDirectActivity.TAG, "Client: Data written");
                    helper = new HistoryDatabase(context, MainActivity.dbName, null, 3);
                    Log.e("", "doInBackground: " + "test point" );
                    helper.insert_values(date, "Device", f.getName(), 0, 1);
                    result = dis.readInt();
                    if(result != 1)
                        break;
                } catch (FileNotFoundException e) {
                    Log.d(WiFiDirectActivity.TAG, e.toString());
                }
                Log.e("", "threadConnect: " + Integer.toString(result) );
            }
            Handler mHandler = new Handler(Looper.getMainLooper());
            if(result != 1 && FILE_COUNT != (i+1)){
                // file transfer fail
                current = new Date();
                date = dateFormat.format(current);
                helper = new HistoryDatabase(context, MainActivity.dbName, null, 3);
                int false_count = FILE_COUNT - i;
                for(int j = 0; j < false_count; j++) {
                    String false_filename = MainActivity.selectList.get(i).getFilePath();
                    helper.insert_values(date, "Device", false_filename, 0, 0);
                    i++;
                }
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 사용하고자 하는 코드
                        Toast.makeText(context, "File Transfer Fail ", Toast.LENGTH_SHORT).show();
                    }
                }, 0);
            } else {
                // file transfer success
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 사용하고자 하는 코드
                        Toast.makeText(context, "File Transfer Success ", Toast.LENGTH_SHORT).show();
                    }
                }, 0);
            }

            dis.close();
            stream.close();
            socket.close();
            MainActivity.wifiActivity.customDisconnect();
//            MainActivity.wifiActivity.finish();
//            MainActivity.selectList.clear();
//            MainActivity.selectCnt = 0;
            MainActivity.wifiActivity.finish();
        } catch (IOException e) {
            Log.e(WiFiDirectActivity.TAG, e.getMessage());

        }

    }

}
