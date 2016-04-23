package com.riansousa.foodtrackerv2;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.riansousa.foodtrackerv2.Logic.MyAlerts;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "FoodTracker";

    /**
     * This method will catch the pending intent and fire onReceive
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            /** log entry */
            Log.i(TAG, "AlarmReceiver.onReceive - Started");

            /** declare local variables */
            int MY_NOTIFICATION_ID = 010;

            /** get report */
            MyAlerts myAlert = new MyAlerts();
            String notification = myAlert.CheckAlarm(context);

            /** check for message length before notifying */
            if (notification.length() > 0) {

                /** if there are notifications then show */
                /** research on bigText() from:
                 * http://developer.android.com/reference/android/app/Notification.BigTextStyle.html */
                Notification theNotification = new Notification.Builder(context.getApplicationContext())
                        .setContentTitle("Alert Notification")
                        .setContentText("You Need To Eat More...")
                        .setSmallIcon(R.drawable.ic_warning_black)
                        .setStyle(new Notification.BigTextStyle()
                                .bigText(notification))
                        .build();

                /** post the notification */
                final NotificationManager notificationManager =
                        (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(MY_NOTIFICATION_ID, theNotification);

            }
            /** log success */
            Log.i(TAG, "AlarmReceiver.onReceive - Success");
        } catch (Exception e) {
            /** log to console */
            Log.i(TAG, "AlarmReceiver.onReceive() - ERROR:" + e.getMessage());
        }
    }
}
