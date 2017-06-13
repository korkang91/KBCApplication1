package com.kangbc.kbcapplication1.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;


public class SplashActivity extends AppCompatActivity {
    AlertDialog.Builder ab;
    int locationMode;   //GPS모드 확인 변수
    String TAG = "kbc";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);

        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(SplashActivity.this, CameraActivity.class));
                finish();
            }
        }, secondsDelayed * 1000);

    }
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onRestart() {
        super.onRestart();
        startActivity(new Intent(SplashActivity.this, CameraActivity.class));
        finish();
    }

}