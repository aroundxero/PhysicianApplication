package com.appdevplus.physician.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.appdevplus.physician.MainActivity;
import com.appdevplus.physician.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

public class FirebaseInstanceService extends FirebaseMessagingService {

    public static final String TAG = "FirebaseInstanceService";

    public static final int NOTIFICATION_ID = 888;
    private NotificationManagerCompat mNotificationManagerCompat;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("onMessageReceived", "---> onMessageReceived called....");
        super.onMessageReceived(remoteMessage);

        mNotificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());

        if(remoteMessage.getData().isEmpty()){
            showNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
        }
        else{
            showNotification(remoteMessage.getData());
        }
    }

    private void showNotification(Map<String,String> data){
        Log.d("showNotification", "---> showNotification -> (Map, String) called....");

        Intent notifyIntent = new Intent(this, MainActivity.class);
        // notifyIntent.putExtra("title", data.get(?));
        // notifyIntent.putExtra("body", data.get(?));

        // Sets the Activity to start in a new, empty task
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent notifyPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        notifyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID ="example.firebase.services.test";
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification",
                    NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.setDescription("code test");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableLights(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificationBuilder =  new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        notificationBuilder
                // .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("ContentTitle?")
                .setContentText("ContentText?")
                .setContentIntent(notifyPendingIntent)
                .setContentInfo("Info");
        notificationManager.notify(new Random().nextInt(),notificationBuilder.build());
    }

    private void showNotification(String title, String body){
        Log.d("showNotification", "---> showNotification -> (String title:" + title + ", String body:" + body + ") called....");

        Intent notifyIntent = new Intent(this, MainActivity.class);
        notifyIntent.putExtra("title", title);
        notifyIntent.putExtra("body", body);

        // Sets the Activity to start in a new, empty task
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent notifyPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        notifyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID ="example.firebase.services.test";
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification",
                    NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.setDescription("code test");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableLights(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificationBuilder =  new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        notificationBuilder
                // .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(notifyPendingIntent)
                .setContentInfo("Info");
        notificationManager.notify(new Random().nextInt(),notificationBuilder.build());

    }

    //send message
    private PendingIntent getPendingIntent(Context context, String message) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("MESSAGE", message);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);
        return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);

        Log.d("TOKENFIREBASE",s);
    }

}
