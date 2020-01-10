package com.example.majorproject;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class LoadActivity extends Activity {

    private int permissioncheck = 1;
    private String[] permissionArr = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        Handler hd = new Handler();

        LoadFiles thread;
        thread = new LoadFiles();

        if(!hasPermissions(this, permissionArr)){
            ActivityCompat.requestPermissions(this, permissionArr, permissioncheck);
        }
        else{
            thread.start();
            while (true){
                if (!thread.isAlive()) {
                    MainActivity.imageList = thread.getImagefiles();
                    hd.postDelayed(new splashhandler(), 5000);
                    break;
                }
            }
        }


    }
//    public void checkPermission(){
//        permissioncheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
//        if(permissioncheck != PackageManager.PERMISSION_GRANTED){
//            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
//
//            }
//            else
//            {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
//                permissioncheck = PackageManager.PERMISSION_GRANTED;
//            }
//        }
//        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
//
//            }
//            else
//            {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//            }
//        }
//        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
//            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)){
//
//            }
//            else
//            {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
//            }
//        }
//
//    }
    public boolean hasPermissions(Context context, String...permissionArr){
        if(context != null && permissionArr !=null){
            for(String permission : permissionArr){
                if(ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        return true;
    }
    private void getPermission(){
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION
        }, 1000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(Build.VERSION.SDK_INT >= 23){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("permission", permissionArr[0] + "was " + grantResults[0]);
                recreate();
            }
            else
            {
                Log.d("permission", "denied");
                    Toast.makeText(LoadActivity.this, "앱을 사용하기 위해서는 메모리 접근 권한과 위치 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                    getPermission();

            }
        }
    }
    private class splashhandler implements Runnable{

        @Override
        public void run() {
            startActivity(new Intent(getApplication(), MainActivity.class));
            LoadActivity.this.finish();
        }
    }
}
