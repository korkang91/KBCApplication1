package com.kangbc.kbcapplication1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.kangbc.kbcapplication1.activity.BackPressCloseHandler;
import com.kangbc.kbcapplication1.activity.CameraActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private BackPressCloseHandler backPressCloseHandler;    //뒤로가기 클래스
    private String TAG = "KBC";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");

//        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backPressCloseHandler = new BackPressCloseHandler(this);

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }
}
