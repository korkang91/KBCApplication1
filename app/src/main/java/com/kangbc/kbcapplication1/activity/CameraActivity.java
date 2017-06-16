package com.kangbc.kbcapplication1.activity;

import android.Manifest;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdView;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.kangbc.kbcapplication1.R;
import com.sackcentury.shinebuttonlib.ShineButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import io.ghyeok.stickyswitch.widget.StickySwitch;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


/**
 * Created by mac on 2017. 6. 9..
 */

public class CameraActivity extends AppCompatActivity {
    private BackPressCloseHandler backPressCloseHandler;    //뒤로가기 클래스

    public Camera camera;
    public boolean isFlashOn = false;
    public boolean permission = false;
    public Parameters params;

    public ShineButton shineButton;
    public ImageButton imageButton;
    public String TAG = "KBC LOG";
    public static boolean FLASH_STATUS = false;

    private InterstitialAd interstitialAd;  // ADmob


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_activity);

//        AdView mAdView = (AdView) findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        // ADmob 배너
        AdView adView = (AdView)this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("55F98698C32B5AE8C3DC14ACCA36C26A")
                .build();
        adView.loadAd(adRequest);

        setFullAd();
        backPressCloseHandler = new BackPressCloseHandler(this);

//        shineButton = (ShineButton) findViewById(R.id.shine_button);
//        shineButton.init(this);
//        shineButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!permission) {
//                    setPermission();
//                    return;
//                }
//                if (FLASH_STATUS == false) {
//                    turnOnFlash();
//                    FLASH_STATUS = true;
//                } else {
//                    turnOffFlash();
//                    FLASH_STATUS = false;
//                }
//            }
//        });

        imageButton = (ImageButton) findViewById(R.id.image_button);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!permission) {
                    setPermission();
                    return;
                }
                if (FLASH_STATUS == false) {
                    turnOnFlash();
                    imageButton.setImageResource(R.drawable.ic_flash_on_yellow);
                    FLASH_STATUS = true;
                } else {
                    turnOffFlash();
                    imageButton.setImageResource(R.drawable.ic_flash_off_black);
                    FLASH_STATUS = false;
                }
            }
        });

        // Set Selected Change Listener
        /*StickySwitch stickySwitch = (StickySwitch) findViewById(R.id.sticky_switch);
        stickySwitch.setOnSelectedChangeListener(new StickySwitch.OnSelectedChangeListener() {
            @Override
            public void onSelectedChange(@NotNull StickySwitch.Direction direction, @NotNull String text) {
//                Log.d(TAG, "Now Selected : " + direction.name() + ", Current Text : " + text);
                if (!permission) {
                    setPermission();
                    return;
                }
                if (direction.name().equals("RIGHT") && FLASH_STATUS == false) {
                    turnOnFlash();
                    FLASH_STATUS = true;
                } else {
                    turnOffFlash();
                    FLASH_STATUS = false;
                }
            }
        });*/


        setPermission();

        //

        final FloatingActionButton actionA = (FloatingActionButton) findViewById(R.id.action_enable);
        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                actionA.setTitle("Action A clicked");
                displayAD();
            }
        });

    }

    public void setPermission(){
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                permission = true;
//                Toast.makeText(CameraActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
//                Toast.makeText(CameraActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA)
                .check();
    }

    public void getCamera() {
        if (camera == null) {
            try {
                camera = Camera.open();
                params = camera.getParameters();
            }catch (Exception e) {
                Log.d(TAG, "getCamera: " + e);
            }
        }
    }

    public void turnOnFlash() {
        if(!isFlashOn) {
            if(camera == null || params == null) {
                getCamera();
//                return;
            }
            params = camera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            isFlashOn = true;
        }

    }

    public void turnOffFlash() {
        if (isFlashOn) {
            if (camera == null || params == null) {
                return;
            }
            params = camera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
            isFlashOn = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // on pause turn off the flash
        turnOffFlash();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // on resume turn on the flash
        if(isFlashOn)
            turnOnFlash();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // on starting the app get the camera params
        getCamera();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // on stop release the camera
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

    private void setFullAd(){
        interstitialAd = new InterstitialAd(this); //새 광고를 만듭니다.
        interstitialAd.setAdUnitId(getResources().getString(R.string.fullAd)); //이전에 String에 저장해 두었던 광고 ID를 전면 광고에 설정합니다.
        AdRequest adRequest1 = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("55F98698C32B5AE8C3DC14ACCA36C26A")
                .build();
        interstitialAd.loadAd(adRequest1); //요청한 광고를 load 합니다.
        interstitialAd.setAdListener(new AdListener() { //전면 광고의 상태를 확인하는 리스너 등록

            @Override
            public void onAdClosed() { //전면 광고가 열린 뒤에 닫혔을 때
                AdRequest adRequest1 = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        .addTestDevice("55F98698C32B5AE8C3DC14ACCA36C26A")
                        .build(); //새 광고요청
                interstitialAd.loadAd(adRequest1); //요청한 광고를 load 합니다.
            }
        });
    }

    public void displayAD(){
        if(interstitialAd.isLoaded()) { //광고가 로드 되었을 시
            interstitialAd.show(); //보여준다
        }
    }

}
