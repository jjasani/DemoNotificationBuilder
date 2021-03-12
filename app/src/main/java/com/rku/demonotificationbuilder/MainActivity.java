package com.rku.demonotificationbuilder;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void withImageNotification(View view) {
        String title = "React Native Workshop";
        String message = "Do you want more information about next React Native Workshop?";
        // Pass the intent to switch to the MainActivity
        Intent intent = new Intent(this, Welcome.class);

        // Assign channel ID
        String channel_id = "notification_channel";

        // Here FLAG_ACTIVITY_CLEAR_TOP flag is set to clear
        // the activities present in the activity stack,
        // on the top of the Activity that is to be launched
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Create a Builder object using NotificationCompat
        // class. This will allow control over all the flags
        NotificationCompat.Builder mBuilder
                = new NotificationCompat.Builder(getApplicationContext(), channel_id)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setContentTitle(title)
                .setContentText(message);

        mBuilder.setContentIntent(PendingIntent.getActivity(
                getApplicationContext(),
                0,
                new Intent(getApplicationContext(),Welcome.class),
                0
        ));

        //Yes intent
        Intent callReceive = new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:9999999999"));
        PendingIntent pendingIntentYes = PendingIntent.getActivity(getApplicationContext(), 0, callReceive, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.addAction(R.mipmap.ic_launcher, "Call", pendingIntentYes);

        //Maybe intent
        Intent webReceive = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://rku.ac.in/events/react"));
        PendingIntent pendingIntentMaybe = PendingIntent.getActivity(getApplicationContext(), 0, webReceive, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.addAction(R.mipmap.ic_launcher, "Web", pendingIntentMaybe);


        // Create an object of NotificationManager class to
        // notify the user of events that happen in the background.
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Check if the Android Version is greater than Oreo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    channel_id,
                    "web_app",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(0, mBuilder.build());
    }

    public void simpleNotification(View view) {
        showNotification("Remedial Exam Notice", "Last date of remedial exam form is 20-03-2021.");
    }

    // Method to display the notifications
    public void showNotification(String title, String message) {

        // Pass the intent to switch to the MainActivity
        Intent intent = new Intent(this, Welcome.class);

        // Assign channel ID
        String channel_id = "notification_channel";

        // Here FLAG_ACTIVITY_CLEAR_TOP flag is set to clear
        // the activities present in the activity stack,
        // on the top of the Activity that is to be launched
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Pass the intent to PendingIntent to start the next Activity
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_ONE_SHOT);

        // Create a Builder object using NotificationCompat
        // class. This will allow control over all the flags
        NotificationCompat.Builder builder
                = new NotificationCompat.Builder(getApplicationContext(), channel_id)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(false)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent)
                .setContentTitle(title)
                .setContentText(message);

        // Create an object of NotificationManager class to
        // notify the user of events that happen in the background.
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Check if the Android Version is greater than Oreo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    channel_id,
                    "web_app",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(0, builder.build());
    }
}