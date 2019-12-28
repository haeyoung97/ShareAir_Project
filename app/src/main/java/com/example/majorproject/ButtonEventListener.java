package com.example.majorproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.aware.WifiAwareManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.nio.channels.AcceptPendingException;

public class ButtonEventListener implements View.OnClickListener {

//
//    private FragmentManager fragmentManager;
//    private FragmentTransaction transaction;
    private MainActivity mainActivity;
    private Context context;
    private WiFiDirectActivity wiFiDirectActivity;
//    private Fragment fragment;
//    public ButtonEventListener(MainActivity mainActivity) {
//        this.mainActivity = mainActivity;
//        fragmentManager = mainActivity.getSupportFragmentManager();
//        transaction = fragmentManager.beginTransaction();
//    }
//    public ButtonEventListener(Fragment fragment){
//        this.fragment = fragment;
//    }
//

    public ButtonEventListener(MainActivity mainActivity, Context context) {
        this.mainActivity = mainActivity;
        this.context = context;
    }

    public ButtonEventListener(WiFiDirectActivity wiFiDirectActivity){
        this.wiFiDirectActivity = wiFiDirectActivity;
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_frame_transferButton:
                // 전송 버튼
                Intent intent = new Intent(context, WiFiDirectActivity.class);
                mainActivity.startActivity(intent);
                mainActivity.finish();

                break;
            case R.id.wifi_direct_onoff_btn:
                // wifi direct on/off 버튼

                if (wiFiDirectActivity.getManager() == null) {
                    Log.d("getman", "null");
                }
                if (wiFiDirectActivity.getChannel() == null) {
                    Log.d("getChan", "null");
                }
                if (wiFiDirectActivity.getManager() != null && wiFiDirectActivity.getChannel() != null) {
                    mainActivity.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                } else {
                    Log.e(wiFiDirectActivity.TAG, "channel or manager is null");
                }
                break;

            case R.id.atn_direct_enable:

                if (wiFiDirectActivity.getManager() == null) {
                    Log.d("getman", "null");
                }
                if (wiFiDirectActivity.getChannel() == null) {
                    Log.d("getChan", "null");
                }
                if (wiFiDirectActivity.getManager() != null && wiFiDirectActivity.getChannel() != null) {
                    mainActivity.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                } else {
                    Log.e(wiFiDirectActivity.TAG, "channel or manager is null");
                }
                break;

            case R.id.atn_direct_discover:
                if (!wiFiDirectActivity.getIsWifiP2pEnabled()) {
                    Toast.makeText(wiFiDirectActivity, R.string.p2p_off_warning,
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                final DeviceListFragment fragment = (DeviceListFragment) wiFiDirectActivity.getFragmentManager().findFragmentById(R.id.frag_list);
                fragment.onInitiateDiscovery();
                wiFiDirectActivity.getManager().discoverPeers(wiFiDirectActivity.getChannel(), new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(wiFiDirectActivity, "Discovery Initiated",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int reasonCode) {
                        Toast.makeText(wiFiDirectActivity, "Discovery Failed : " + reasonCode,
                                Toast.LENGTH_SHORT).show();
                    }
                });
                break;
//            굳이 메인 화면의 탭뷰로 구현하지 않고, 버튼 추가해서 버튼 이벤트 처리로 구현도 가능~~
//            이건 그냥 입맛대로 하믄 될듯?
//            혹시 쓸까봐 구냥 추가하고 주석처리함

//            case R.id.wifi_direct_connect_button :
//                // wifi-direct On/Off button
//                wifiDirectFragment = new wifiDirectFragment();
//                fragment.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.영역, wifiDirectFragment);
//                break;
//            case R.id.wifi_direct_discover_button :
//                // wifi-direct discover button
//                break;
//
            default:
                break;

        }

    }
}
