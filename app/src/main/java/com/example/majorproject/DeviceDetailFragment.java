/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.majorproject;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager.ConnectionInfoListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.example.majorproject.DeviceListFragment.DeviceActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;




/**
 * A fragment that manages a particular peer and allows interaction with device
 * i.e. setting up network connection and transferring data.
 */
public class DeviceDetailFragment extends Fragment implements ConnectionInfoListener {
    private static final int SOCKET_TIMEOUT = 10000;
    protected static final int CHOOSE_FILE_RESULT_CODE = 20;
    private View mContentView = null;
    private WifiP2pDevice device;
    private WifiP2pInfo info;
    private LayoutInflater inflater;
    private int port;

    private String deviceName;

    public String getDeviceName() {
        return deviceName;
    }
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }


    ProgressDialog progressDialog = null;

    private Context context;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.inflater = inflater;
        mContentView = inflater.inflate(R.layout.device_detail, null);
        context = mContentView.getContext();
        mContentView.findViewById(R.id.btn_connect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WifiP2pConfig config = new WifiP2pConfig();
                if(ButtonEventListener.isSendOrRecvBtn == 15){
                    config.groupOwnerIntent = 15;
                }
                else{
                    config.groupOwnerIntent = 1;
                }
                config.deviceAddress = device.deviceAddress;
                config.wps.setup = WpsInfo.PBC;
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
//                setDeviceName(device.deviceName);
                progressDialog = ProgressDialog.show(getActivity(), "Press back to cancel",
                        "Connecting to :" + device.deviceName, true, true
//                        new DialogInterface.OnCancelListener() {
//
//                            @Override
//                            public void onCancel(DialogInterface dialog) {
//                                ((DeviceActionListener) getActivity()).cancelDisconnect();
//                            }
//                        }
                );
                ((DeviceActionListener) getActivity()).connect(config);

            }
        });


        mContentView.findViewById(R.id.btn_disconnect).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        ((DeviceActionListener) getActivity()).disconnect();
                    }
                });

        mContentView.findViewById(R.id.btn_start_client).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // Allow user to pick an image from Gallery or other
                        // registered apps
//                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                        intent.setType("image/*");
//                        startActivityForResult(intent, CHOOSE_FILE_RESULT_CODE);
                        if(ButtonEventListener.isSendOrRecvBtn == 1) {
//                            setDeviceName(device.deviceName);
                            onConnectionInfoAvailable(info);
                            startFileTransfer();
                        }

                    }
                });


        return mContentView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }


    public void startFileTransfer(){
        ArrayList<String> uriString = new ArrayList<>();
        TextView statusText = (TextView) mContentView.findViewById(R.id.status_text);
//        setDeviceName(device.deviceName);
        FileTransferService fileTransferService = new FileTransferService(context);
        fileTransferService.setFILE_COUNT(MainActivity.selectList.size());
        fileTransferService.setEXTRAS_GROUP_OWNER_ADDRESS(info.groupOwnerAddress.getHostAddress());
        Log.e("JSch-Address", info.groupOwnerAddress.getHostAddress());
        fileTransferService.setEXTRAS_GROUP_OWNER_PORT("8988");
        Log.e("JSch-port", "8988");
        fileTransferService.connectionSocket();
    }

    @Override
    public void onConnectionInfoAvailable(final WifiP2pInfo info) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        this.info = info;
        this.mContentView.setVisibility(View.VISIBLE);

        // The owner IP is now known.
        TextView view = (TextView) mContentView.findViewById(R.id.group_owner);
        view.setText(getResources().getString(R.string.group_owner_text)
                + ((info.isGroupOwner == true) ? getResources().getString(R.string.yes)
                : getResources().getString(R.string.no)));

        // InetAddress from WifiP2pInfo struct.
        view = (TextView) mContentView.findViewById(R.id.device_info);
        view.setText("Group Owner IP - " + info.groupOwnerAddress.getHostAddress());

        // After the group negotiation, we assign the group owner as the file
        // server. The file server is single threaded, single connection server
        // socket.
        if (info.groupFormed && info.isGroupOwner) {
            new FileServerAsyncTask(getActivity(), mContentView.findViewById(R.id.status_text))
                    .threadStart();
        } else if (info.groupFormed) {
            // The other device acts as the client. In this case, we enable the
            // get file button.
            mContentView.findViewById(R.id.btn_start_client).setVisibility(View.VISIBLE);
            ((TextView) mContentView.findViewById(R.id.status_text)).setText(getResources()
                    .getString(R.string.client_text));
        }

        // hide the connect button
        mContentView.findViewById(R.id.btn_connect).setVisibility(View.GONE);
    }

    /**
     * Updates the UI with device data
     *
     * @param device the device to be displayed
     */
    public void showDetails(WifiP2pDevice device) {
        this.device = device;
        this.mContentView.setVisibility(View.VISIBLE);
        TextView view = (TextView) mContentView.findViewById(R.id.device_address);
        view.setText(device.deviceAddress);
        view = (TextView) mContentView.findViewById(R.id.device_info);
        view.setText(device.toString());

    }

    /**
     * Clears the UI fields after a disconnect or direct mode disable operation.
     */
    public void resetViews() {
        mContentView.findViewById(R.id.btn_connect).setVisibility(View.VISIBLE);
        TextView view = (TextView) mContentView.findViewById(R.id.device_address);
        view.setText(R.string.empty);
        view = (TextView) mContentView.findViewById(R.id.device_info);
        view.setText(R.string.empty);
        view = (TextView) mContentView.findViewById(R.id.group_owner);
        view.setText(R.string.empty);
        view = (TextView) mContentView.findViewById(R.id.status_text);
        view.setText(R.string.empty);
        mContentView.findViewById(R.id.btn_start_client).setVisibility(View.GONE);
        this.getView().setVisibility(View.GONE);
    }

    /**
     * A simple server socket that accepts connection and writes some data on
     * the stream.
     */
    // server socket download
    public static class FileServerAsyncTask {
        private Context context;
        private TextView statusText;

        /**
         * @param context
         * @param statusText
         */
        public FileServerAsyncTask(Context context, View statusText) {
            this.context = context;
            this.statusText = (TextView) statusText;
        }

        // 파일 다운로드
        public void threadStart(){
            new Thread() {
                public void run() {
                    doInBackground();
                }
            }.start();
        }

        public void doInBackground() {
            try {
//                String filepath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
                int flag = 1, i;
                Date current;
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                String date;
                HistoryDatabase helper;
                File dirs = getSaveFolder();
                ServerSocket serverSocket = new ServerSocket(8988);
                Log.d(WiFiDirectActivity.TAG, "Server: Socket opened");
                Socket client = serverSocket.accept();
                FileOutputStream fos;

                Log.d(WiFiDirectActivity.TAG, "Server: connection done");
                DataInputStream dis = new DataInputStream(client.getInputStream());
                DataOutputStream stream = new DataOutputStream(client.getOutputStream());
                // 파일 개수
                int count = dis.readInt();
                Log.e("", "doInBackground: " + String.valueOf(count));
                for(i = 0; i < count; i++) {
                    Log.e(WiFiDirectActivity.TAG, "For_Server: connection done");

                    // 파일 확장자
                    String file_extension = dis.readUTF();
                    Log.e(WiFiDirectActivity.TAG, "For_Server: file_extension");
                    File f = new File(dirs.getAbsolutePath() + "/" + System.currentTimeMillis() + "." + file_extension);

                    // 파일 길이
                    long length = dis.readLong();
                    Log.e(WiFiDirectActivity.TAG, "For_Server: length");

                    int len = 0;
                    byte buf[] = new byte[1024];
                    long data = 0;
                    Log.d(WiFiDirectActivity.TAG, "server: copying files " + f.toString());
                    fos = new FileOutputStream(f);
                    while ((len = dis.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        data += len;
                        if(data == length) break;

                    }
                    Log.e("", "doInBackground: " + "file_copy finish");
                    current = new Date();
                    date = dateFormat.format(current);
                    Log.e("", "threadConnect: " + date);

                    helper = new HistoryDatabase(context, MainActivity.dbName, null, 3);
                    Log.e("", "doInBackground: " + "test point" );
                    helper.insert_values(date, "Device", f.getName(), 1, 1);

                    if(dis.available() == 0) {
                        stream.writeInt(1);
//                        if(count != (i+1))
//                            flag = 0;
//                        break;
                    }
                    else {
                        stream.writeInt(0);
                        if(count != (i+1))
                            flag = 0;
                        break;
                    }
                }
                Handler mHandler = new Handler(Looper.getMainLooper());
                if(flag == 0){
                    // file transfer fail
                    current = new Date();
                    date = dateFormat.format(current);
                    helper = new HistoryDatabase(context, MainActivity.dbName, null, 3);
                    int false_count = count - i;
                    for(int j = false_count; j < count; j++) {
                        String false_filename = MainActivity.selectList.get(i).getFilePath();
                        helper.insert_values(date, "Device", false_filename, 1, 0);
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
                client.close();
                serverSocket.close();
                MainActivity.wifiActivity.customDisconnect();
//                MainActivity.selectList.clear();
//                MainActivity.selectCnt = 0;
                MainActivity.wifiActivity.finish();
                return ;
            } catch (IOException e) {
                return ;
            }

        }

        private File getSaveFolder(){
            File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/shairAir");
            if(!dir.exists()){
                dir.mkdirs();
            }
            return dir;
        }

        /*
         * (non-Javadoc)
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        protected void onPostExecute(String result) {
            if (result != null) {
                statusText.setText("File copied - " + result);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Log.e("provider : ", result);
                File f = new File(result);

                intent.setDataAndType(FileProvider.getUriForFile(context,"com.example.majorproject.fileprovider", f), "image/*");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                context.startActivity(intent);
            }

        }

        /*
         * (non-Javadoc)
         * @see android.os.AsyncTask#onPreExecute()
         */
        protected void onPreExecute() {
            statusText.setText("Opening a server socket");
        }

    }

}

