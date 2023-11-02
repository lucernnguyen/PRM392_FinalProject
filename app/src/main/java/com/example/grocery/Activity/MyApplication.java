package com.example.grocery.Activity;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;

import com.example.grocery.R;

public class MyApplication extends Application {
    public static String CHANNEL_ID="Channel_1";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }
    public void createNotificationChannel(){
        Uri uri=Uri.parse("android.resource://"+getPackageName()+"/"+ R.raw.notification);
        AudioAttributes audioAttributes=new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel.
            String name = getString(R.string.channel_name);
            String descriptionText = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
             NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(descriptionText);
            mChannel.setSound(uri,audioAttributes);
            // Register the channel with the system. You can't change the importance
            // or other notification behaviors after this.
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(mChannel);
        }
    }
}
