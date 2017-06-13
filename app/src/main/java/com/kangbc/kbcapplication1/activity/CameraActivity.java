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

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.kangbc.kbcapplication1.R;
import com.sackcentury.shinebuttonlib.ShineButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import io.ghyeok.stickyswitch.widget.StickySwitch;


/**
 * Created by mac on 2017. 6. 9..
 */

public class CameraActivity extends AppCompatActivity {
    private BackPressCloseHandler backPressCloseHandler;    //뒤로가기 클래스

    private Camera camera;
    private boolean isFlashOn = false;
    public boolean permission = false;
    Parameters params;

    private ShineButton shineButton;
    private String TAG = "KBC LOG";
    public static boolean FLASH_STATUS = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_activity);

        backPressCloseHandler = new BackPressCloseHandler(this);

        shineButton = (ShineButton) findViewById(R.id.shine_button);
        shineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!permission) {
                    setPermission();
                    return;
                }
                if (FLASH_STATUS == false) {
                    turnOnFlash();
                    FLASH_STATUS = true;
                } else {
                    turnOffFlash();
                    FLASH_STATUS = false;
                }
            }
        });

        // Set Selected Change Listener
        StickySwitch stickySwitch = (StickySwitch) findViewById(R.id.sticky_switch);
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
        });


        setPermission();

        //
        final View actionB = findViewById(R.id.action_b);

//        FloatingActionButton actionC = new FloatingActionButton(getBaseContext());
//        actionC.setTitle("Hide/Show Action above");
//        actionC.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                actionB.setVisibility(actionB.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
//            }
//        });
//
//        final FloatingActionsMenu menuMultipleActions = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
//        menuMultipleActions.addButton(actionC);

        final FloatingActionButton actionA = (FloatingActionButton) findViewById(R.id.action_a);
        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionA.setTitle("Action A clicked");
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

    private void getCamera() {
        if (camera == null) {
            try {
                camera = Camera.open();
                params = camera.getParameters();
            }catch (Exception e) {
                Log.d(TAG, "getCamera: " + e);
            }
        }
    }

    private void turnOnFlash() {
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

    private void turnOffFlash() {
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

}
