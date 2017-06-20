package com.kangbc.kbcapplication1;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;


/**
 * Implementation of App Widget functionality.
 */
public class FlashlightWidget extends AppWidgetProvider {

    private static final String ACTION_FLASH_ON ="com.kangbc.kbcapplication1.FlashlightWidget.ON";
    private static final String ACTION_FLASH_OFF ="com.kangbc.kbcapplication1.FlashlightWidget.OFF";
    private ComponentName flashWidget;
    private RemoteViews views = null;

    private static Camera camera;

    int flashControl = 0;

    private Intent intent;

    private CameraManager mCameraManager;
    private String mCameraId;
    private Boolean isTorchOn = false;


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        flashWidget = new ComponentName(context, FlashlightWidget.class);
        views = new RemoteViews(context.getPackageName(), R.layout.flashlight_widget);

        if(flashControl == 0){
            views.setImageViewResource(R.id.flash_btn, R.drawable.ic_flash_off_black);
            intent = new Intent(ACTION_FLASH_ON);
        }else if(flashControl == 1){
            views.setImageViewResource(R.id.flash_btn, R.drawable.ic_flash_on_yellow);
            intent = new Intent(ACTION_FLASH_OFF);
        }

        // Flash Intent
        PendingIntent onPendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.flash_btn, onPendingIntent);

        appWidgetManager.updateAppWidget(flashWidget, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if(action.equals(ACTION_FLASH_ON)){
            try{
                flashControl = 1;

                AppWidgetManager manager = AppWidgetManager.getInstance(context);
                this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, FlashlightWidget.class)));

                // Open the default i.e. the first rear facing camera.
                camera = Camera.open();

                if(camera == null) {
                    Toast.makeText(context, "no_camera", Toast.LENGTH_SHORT).show();
                } else {
                    // Set the torch flash mode
                    Camera.Parameters param = camera.getParameters();
                    param.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    try {
                        camera.setParameters(param);
                        camera.startPreview();
                    } catch (Exception e) {
                        Toast.makeText(context, "no_flash", Toast.LENGTH_SHORT).show();
                    }
                }

                // 수정소스
//                mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
//                try {
//                    mCameraId = mCameraManager.getCameraIdList()[0];
//                } catch (CameraAccessException e) {
//                    e.printStackTrace();
//                }


            }catch (Exception e) {
                // TODO: handle exception
                Log.e("Flash state", "Flash ON Exception");
            }
        }else if(action.equals(ACTION_FLASH_OFF)){
            try{

                flashControl = 0;

                AppWidgetManager manager = AppWidgetManager.getInstance(context);
                this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, FlashlightWidget.class)));

                if (camera != null) {
                    camera.stopPreview();
                    camera.release();
                    camera = null;
                }

                //수정소스

            }catch (Exception e) {
                // TODO: handle exception
                Log.e("Flash state", "Flash OFF Exception");
            }
        }else{
            super.onReceive(context, intent);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    /*
    public void turnOnFlashLight() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mCameraManager.setTorchMode(mCameraId, true);
//                playOnOffSound();
                imageButton.setImageResource(R.drawable.ic_flash_on_yellow);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void turnOffFlashLight() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mCameraManager.setTorchMode(mCameraId, false);
//                playOnOffSound();
                imageButton.setImageResource(R.drawable.ic_flash_off_black);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */
}

