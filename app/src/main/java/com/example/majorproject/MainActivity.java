package com.example.majorproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;

    static ArrayList<File> imageList = new ArrayList<>();
    static ArrayList<File> selectList = new ArrayList<>();
    static ArrayList<LoadFiles.AlbumNode> albumList = new ArrayList<>();
    private SendTabFragment sendTabFragment = new SendTabFragment(this);
    private FragmentTransaction transaction;


    private ButtonEventListener buttonEventListener;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_send:
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.fragmentLayout, sendTabFragment).commitAllowingStateLoss();
                    break;
                case R.id.navigation_receive:
                    return true;
                case R.id.navigation_history:
                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavView = findViewById(R.id.bottom_nav_view);

//        buttonEventListener = new ButtonEventListener();
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
