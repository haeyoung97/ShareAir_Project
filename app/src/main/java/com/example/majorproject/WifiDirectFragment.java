package com.example.majorproject;

import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

public class WifiDirectFragment extends Fragment implements WifiP2pManager.ChannelListener, DeviceListFragment.DeviceActionListener {

    public static final String TAG = "wifidirectdemo";
    private WifiP2pManager manager;
    private boolean isWifiP2pEnabled = false;
    private boolean retryChannel = false;
    public Context context;
    private final IntentFilter intentFilter = new IntentFilter();
    private WifiP2pManager.Channel channel;
    private BroadcastReceiver receiver = null;
    private FragmentManager listFragmentManager;
    private androidx.fragment.app.FragmentManager detailFragmentManager;

    private ButtonEventListener enableButtonEventListener;
    private ButtonEventListener discoverButtonEventListener;
    private MainActivity mainActivity;
    static View rootView;

    private TextView textView;

    public FragmentManager getListFragmentManager() {
        return listFragmentManager;
    }


    public androidx.fragment.app.FragmentManager getDetailFragmentManager() {
        return detailFragmentManager;
    }

    public WifiDirectFragment(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void setIsWifiP2pEnabled(boolean isWifiP2pEnabled) {
        this.isWifiP2pEnabled = isWifiP2pEnabled;
    }

    public boolean getIsWifiP2pEnabled() {
        return isWifiP2pEnabled;
    }

    public Context getContext(){
        return this.context;
    }

    public WifiP2pManager getManager() {
        return manager;
    }

    public WifiP2pManager.Channel getChannel() {
        return channel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        if(rootView != null){
//            ViewGroup parent = (ViewGroup) rootView.getParent();
//            if(parent != null)
//                parent.removeView(rootView);
//        }
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.main, container, false);
        }
        if(ButtonEventListener.isSendOrRecvBtn == 1){
            textView = (TextView)rootView.findViewById(R.id.main_frame_title);
            textView.setText("Wifi Direct Send");
        }
        else if(ButtonEventListener.isSendOrRecvBtn == 15){
            textView = (TextView)rootView.findViewById(R.id.main_frame_title);
            textView.setText("Wifi Direct Receive");
        }
        context = rootView.getContext();
        listFragmentManager = getActivity().getFragmentManager();
        detailFragmentManager = getActivity().getSupportFragmentManager();
        // Indicates a change in the Wi-Fi Peer-to-Peer status.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);

        // Indicates a change in the list of available peers.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);

        // Indicates the state of Wi-Fi P2P connectivity has changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);

        // Indicates this device's details have changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        manager = (WifiP2pManager) context.getSystemService(Context.WIFI_P2P_SERVICE);

        channel = manager.initialize(context, context.getMainLooper(), null);

        int result = 0;

        enableButtonEventListener = new ButtonEventListener(this);
        discoverButtonEventListener = new ButtonEventListener(this);

        Button enable = (Button)rootView.findViewById(R.id.atn_direct_enable);
        enable.setOnClickListener(enableButtonEventListener);

        Button discover = (Button)rootView.findViewById(R.id.atn_direct_discover);
        discover.setOnClickListener(discoverButtonEventListener);

        return rootView;
    }

    public void onResume() {
        super.onResume();
        receiver = new WiFiDirectBroadcastReceiver(manager, channel, this);
        context.registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        context.unregisterReceiver(receiver);
    }
    public void resetData() {
        DeviceListFragment fragmentList = (DeviceListFragment) listFragmentManager.findFragmentById(R.id.frag_list);
        DeviceDetailFragment fragmentDetails = (DeviceDetailFragment) detailFragmentManager.findFragmentById(R.id.frag_detail);

        if (fragmentList != null) {
            fragmentList.clearPeers();
        }
        if (fragmentDetails != null) {
            fragmentDetails.resetViews();
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.action_items, menu);
        return true;
    }

    //    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.atn_direct_enable:
//                if (manager != null && channel != null) {
//
//                    // Since this is the system wireless settings activity, it's
//                    // not going to send us a result. We will be notified by
//                    // WiFiDeviceBroadcastReceiver instead.
//
//                    startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
//                } else {
//                    Log.e(TAG, "channel or manager is null");
//                }
//                return true;
//
//            case R.id.atn_direct_discover:
//                if (!isWifiP2pEnabled) {
//                    Toast.makeText(context, R.string.p2p_off_warning,
//                            Toast.LENGTH_SHORT).show();
//                    return true;
//                }
//                final DeviceListFragment fragment = (DeviceListFragment) listFragmentManager.findFragmentById(R.id.frag_list);
//                fragment.onInitiateDiscovery();
//                manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
//
//                    @Override
//                    public void onSuccess() {
//                        Toast.makeText(context, "Discovery Initiated",
//                                Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onFailure(int reasonCode) {
//                        Toast.makeText(context, "Discovery Failed : " + reasonCode,
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
    @Override
    public void onChannelDisconnected() {
        // we will try once more
        if (manager != null && !retryChannel) {
            Toast.makeText(this.context, "Channel lost. Trying again", Toast.LENGTH_LONG).show();
            resetData();
            retryChannel = true;
            manager.initialize(this.context, context.getMainLooper(), this);
        } else {
            Toast.makeText(this.context,  "Severe! Channel is probably lost premanently. Try Disable/Re-Enable P2P.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void showDetails(WifiP2pDevice device) {
        DeviceDetailFragment fragment = (DeviceDetailFragment)detailFragmentManager.findFragmentById(R.id.frag_detail);
        fragment.showDetails(device);
    }

    @Override
    public void cancelDisconnect() {
        /*
         * A cancel abort request by user. Disconnect i.e. removeGroup if
         * already connected. Else, request WifiP2pManager to abort the ongoing
         * request
         */
        if (manager != null) {
            final DeviceListFragment fragment = (DeviceListFragment)listFragmentManager
                    .findFragmentById(R.id.frag_list);
            if (fragment.getDevice() == null
                    || fragment.getDevice().status == WifiP2pDevice.CONNECTED) {
                disconnect();
            } else if (fragment.getDevice().status == WifiP2pDevice.AVAILABLE
                    || fragment.getDevice().status == WifiP2pDevice.INVITED) {

                manager.cancelConnect(channel, new WifiP2pManager.ActionListener() {

                    @Override
                    public void onSuccess() {
                        Toast.makeText(context, "Aborting connection",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int reasonCode) {
                        Toast.makeText(context,
                                "Connect abort request failed. Reason Code: " + reasonCode,
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

    }



    @Override
    public void connect(WifiP2pConfig config) {
        manager.connect(channel, config, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                // WiFiDirectBroadcastReceiver will notify us. Ignore for now.
            }

            @Override
            public void onFailure(int reason) {
                Toast.makeText(context, "Connect failed. Retry.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void disconnect() {
        final DeviceDetailFragment fragment = (DeviceDetailFragment) detailFragmentManager
                .findFragmentById(R.id.frag_detail);
        fragment.resetViews();
        manager.removeGroup(channel, new WifiP2pManager.ActionListener() {

            @Override
            public void onFailure(int reasonCode) {
                Log.d(TAG, "Disconnect failed. Reason :" + reasonCode);
            }

            @Override
            public void onSuccess() {
                fragment.getView().setVisibility(View.GONE);
            }

        });
    }
}
