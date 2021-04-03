package com.ak.notification_show;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final String NOTIFICATION_REPLY = "Notification_Reply";
    public static final String CHANNNEL_ID = "Channel_ID";
    public static final String CHANNEL_NAME = "Channel_Name";
    public static final String CHANNEL_DESC = "channel for Send Notifications";

    public static final String KEY_INTENT_MORE = "key_more";
    public static final String KEY_INTENT_HELP = "key_help";

    public static final int REQUEST_CODE_MORE = 100;
    public static final int REQUEST_CODE_HELP = 101;
    public static final int NOTIFICATION_ID = 200;

    Button creatNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        creatNotification=findViewById(R.id.buttonNotification);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNNEL_ID, CHANNEL_NAME, importance);
            mChannel.setDescription(CHANNEL_DESC);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(mChannel);
        }

        creatNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNotification();
            }
        });


    }

    public void displayNotification(){

        //Pending intent for a notification button named More
        PendingIntent morePendingIntent = PendingIntent.getBroadcast(
                MainActivity.this,REQUEST_CODE_MORE,
                         new Intent(MainActivity.this, NotificationReceiver.class)
                        .putExtra(KEY_INTENT_MORE, REQUEST_CODE_MORE),
                         PendingIntent.FLAG_UPDATE_CURRENT
        );

        //Pending intent for a notification button help
        PendingIntent helpPendingIntent = PendingIntent.getBroadcast(
                MainActivity.this,
                REQUEST_CODE_HELP,
                new Intent(MainActivity.this, NotificationReceiver.class)
                        .putExtra(KEY_INTENT_HELP, REQUEST_CODE_HELP),
                PendingIntent.FLAG_UPDATE_CURRENT
        );


//        //We need this object for getting direct input from notification
//        RemoteInput remoteInput = new RemoteInput.Builder(NOTIFICATION_REPLY)
//                .setLabel("Please enter your name")
//                .build();
//
//
//        //For the remote input we need this action object
//        NotificationCompat.Action action =
//                new NotificationCompat.Action.Builder(android.R.drawable.ic_delete,
//                        "Reply Now...", helpPendingIntent)
//                        .addRemoteInput(remoteInput)
//                        .build();


        //Creating the notifiction builder object
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_email)
                .setContentTitle("Hey this is akash")
                .setContentText("Please check app for update ")
                .setAutoCancel(true)
                .setContentIntent(helpPendingIntent);
//                .addAction(action)
//                .addAction(android.R.drawable.ic_menu_compass, "More", morePendingIntent)
//                .addAction(android.R.drawable.ic_menu_directions, "Help", helpPendingIntent);


        //finally displaying the notification
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());

    }

}