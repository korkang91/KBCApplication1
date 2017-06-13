package com.kangbc.kbcapplication1.activity;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.kangbc.kbcapplication1.R;

/**
 * Created by mac on 2017. 6. 13..
 */

public class FlashlightWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {

        Intent receiver = new Intent(context, FlashlightWidgetReceiver.class);
        receiver.setAction("COM_FLASHLIGHT");
        receiver.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, receiver, 0);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        views.setOnClickPendingIntent(R.id.button, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetIds, views);

    }
}