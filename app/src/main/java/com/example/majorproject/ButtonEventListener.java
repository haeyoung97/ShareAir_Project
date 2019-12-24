package com.example.majorproject;

import android.view.View;

import androidx.fragment.app.Fragment;

public class ButtonEventListener implements View.OnClickListener {
    @Override
//
//    private FragmentManager fragmentManager;
//    private FragmentTransaction transaction;
//    private MainActivity mainActivity;
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_frame_transferButton :
                // 받기 버튼
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
