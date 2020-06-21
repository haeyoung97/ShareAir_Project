package com.example.majorproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;

    static ArrayList<FileNode> selectList = new ArrayList<>();
//    static ArrayList<Integer> selectList = new ArrayList<>();

//    static ArrayList<File> selectList = new ArrayList<>();
    private SendTabFragment sendTabFragment = new SendTabFragment(this);
    private HistoryTabFragment historyTabFragment = new HistoryTabFragment();
    private FragmentTransaction transaction;


    //db 생성 파일 이름
    final static String dbName = "historyDB.db";
    final static int dbVersion = 2;

    static ButtonEventListener btnEventListener;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_send:
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.fragmentLayout, sendTabFragment).addToBackStack(sendTabFragment.getClass().getSimpleName()).commitAllowingStateLoss();
                    break;
                case R.id.navigation_receive:
                    ButtonEventListener.isSendOrRecvBtn = 15;
                    Intent recvIntent = new Intent(getApplicationContext(), WiFiDirectActivity.class);
                    startActivity(recvIntent);
                    break;
                case R.id.navigation_history:
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.fragmentLayout, historyTabFragment).addToBackStack(historyTabFragment.getClass().getSimpleName()).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    };

    public FragmentTransaction getTransaction() {
        return transaction;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavView = findViewById(R.id.bottom_nav_view);

        btnEventListener= new ButtonEventListener(this, this.getApplicationContext());

//
//        Button wifiOnOffBtn = findViewById(R.id.wifi_direct_onoff_btn);
//        wifiOnOffBtn.setOnClickListener(buttonEventListener);

        fragmentManager = getSupportFragmentManager();

        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentLayout, sendTabFragment).commitAllowingStateLoss();

        bottomNavView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

    public static boolean checkExternalAvailable(){
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            return true;
        }
        return false;
    }

}
