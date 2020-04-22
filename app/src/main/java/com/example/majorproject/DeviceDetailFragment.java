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
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager.ConnectionInfoListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.FileProvider;

import com.example.majorproject.DeviceListFragment.DeviceActionListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

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
    ProgressDialog progressDialog = null;

    private Context context;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

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
                progressDialog = ProgressDialog.show(getActivity(), "Press back to cancel",
                        "Connecting to :" + device.deviceAddress, true, true
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
                            onConnectionInfoAvailable(info);
                            startFileTransfer();
                        }




                    }
                });


        return mContentView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        // User has picked an image. Transfer it to group owner i.e peer using
        // FileTransferService.
        Uri uri = data.getData();
        TextView statusText = (TextView) mContentView.findViewById(R.id.status_text);
        statusText.setText("Sending: " + uri);
        Log.d(WiFiDirectActivity.TAG, "Intent----------- " + uri);
        FileTransferService fileTransferService = new FileTransferService(context);
        //sshConnectService.setActionSendFile();
        fileTransferService.setEXTRAS_FILE_PATH(uri.toString());
        Log.e("JSch-Path", uri.toString());
        fileTransferService.setEXTRAS_GROUP_OWNER_ADDRESS(info.groupOwnerAddress.getHostAddress());
        Log.e("JSch-Address", info.groupOwnerAddress.getHostAddress());
        fileTransferService.setEXTRAS_GROUP_OWNER_PORT("8988");
        Log.e("JSch-port", "22");
        fileTransferService.connectionSocket();

    }

    /////////////////////////////////////////////////////////
    ////////이 부분 수정함/////////////////////////////////
    /////////////////////////////////////////////////////////
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
    public void startFileTransfer(){
        // selectList에서 여러개 파일 전송하는 것도 구현해야 할 듯.
        // selectList는 [String 파일 경로, int 파일 종류] 리스트로 수정
        String filepath = MainActivity.selectList.get(0).getFilePath();
        Uri uri = getUriFromPath(filepath, MainActivity.selectList.get(0).getFileKind());

        TextView statusText = (TextView) mContentView.findViewById(R.id.status_text);
        statusText.setText("Sending: " + uri);
//        statusText.setText("Sending: " + filepath);
        Log.d(WiFiDirectActivity.TAG, "Intent----------- " + uri);
//        Log.d(WiFiDirectActivity.TAG, "Intent----------- " + filepath);
        FileTransferService fileTransferService = new FileTransferService(context);
        //sshConnectService.setActionSendFile();
        fileTransferService.setEXTRAS_FILE_PATH(uri.toString());
//        fileTransferService.setEXTRAS_FILE_PATH(filepath);
        Log.e("JSch-Path", uri.toString());
//        Log.e("JSch-Path", filepath);
        fileTransferService.setEXTRAS_GROUP_OWNER_ADDRESS(info.groupOwnerAddress.getHostAddress());
        Log.e("JSch-Address", info.groupOwnerAddress.getHostAddress());
        fileTransferService.setEXTRAS_GROUP_OWNER_PORT("8988");
        Log.e("JSch-port", "22");
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
                    .execute();
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
    // server socket
    public static class FileServerAsyncTask extends AsyncTask<Void, Void, String> {

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

        @Override
        protected String doInBackground(Void... params) {
//            String user = "Haeyoung";
//            String password = "1234";
//            try{
//                JSch jsch = new JSch();
//                Session session = jsch.getSession(user, EXTRAS_GROUP_OWNER_ADDRESS, 22);
//                session.setPassword(password);
//
//                // 호스트 정보 검사 X
//                session.setConfig("StrictHostKeyChecking", "no");
//                session.setTimeout(SOCKET_TIMEOUT);
//                session.connect();
//                //ChannelExec channel = (ChannelExec)session.openChannel("sftp");
//                Channel channel = (Channel)session.openChannel("sftp");
//                //channel.setCommand("your ssh command here");
//                channel.connect();
//
//                if(channel.isConnected()){
//                    Log.e("JSCH-Server", "JSch : Connected");
//                }
//                ChannelSftp channelSftp = (ChannelSftp) channel;
//
//                channel.disconnect();
//                if(channel.isClosed()){
//                    Log.e("JSCH-Server", "JSch : Closed");
//                }
//
//
//                Log.e("JSCH-Server", "JSch : SUCCESS");
//            }
//            catch(JSchException e){
//                // show the error in the UI
//                Log.e("JSCH-Server", "JSchException : " + e);
//            }
            try {
                String filepath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
                ServerSocket serverSocket = new ServerSocket(8988);
                Log.d(WiFiDirectActivity.TAG, "Server: Socket opened");
                Socket client = serverSocket.accept();
                Log.d(WiFiDirectActivity.TAG, "Server: connection done");

                File f = new File(filepath + "/wifip2pshared-" + System.currentTimeMillis()
                        + ".jpg");
                Log.e("File Path : ", f.getAbsolutePath());
                Log.e("File Path : ", f.getParent());


                File dirs = new File(f.getParent());
                if (!dirs.exists())
                    dirs.mkdirs();
                f.createNewFile();

                Log.d(WiFiDirectActivity.TAG, "server: copying files " + f.toString());
                InputStream inputstream = client.getInputStream();
                copyFile(inputstream, new FileOutputStream(f));
                serverSocket.close();
                return f.getAbsolutePath();
            } catch (IOException e) {
                Log.e("IOException : ", e.getMessage());
                return null;
            }
        }

        /*
         * (non-Javadoc)
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
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
        @Override
        protected void onPreExecute() {
            statusText.setText("Opening a server socket");
        }

    }

    public static boolean copyFile(InputStream inputStream, OutputStream out) {
        Log.e("DETAIL : "," now copyFile");
        byte buf[] = new byte[1024];
        int len;
        long startTime=System.currentTimeMillis();

        try {
            while ((len = inputStream.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
            out.close();
            inputStream.close();
            long endTime=System.currentTimeMillis()-startTime;
            Log.v("","Time taken to transfer all bytes is : "+endTime);

        } catch (IOException e) {
            Log.d(WiFiDirectActivity.TAG, e.toString());
            return false;
        }
        return true;
    }

}
