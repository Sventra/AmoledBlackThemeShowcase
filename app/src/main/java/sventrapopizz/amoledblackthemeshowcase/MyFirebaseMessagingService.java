package sventrapopizz.amoledblackthemeshowcase;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "Amoled Black Theme";

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + FirebaseInstanceId.getInstance().getToken());
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "FROM" + remoteMessage.getFrom());

        //check if the message contains data
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data" + remoteMessage.getData());
        }

        //check if the message contains notification
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message body:" + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody());
        }
    }

    //Display the notification
    private void sendNotification(String body) {
        String GROUP_KEY_UPDATES = "ABTheme Updates";
        Intent intent = new Intent(this, MainActivity.class);
        //intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String CHANNEL_ID = "abtheme_channel";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "ABTheme Channel";
            String Description = "Default channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.WHITE);
            mChannel.enableVibration(true);
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);
        }

        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notifiBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_photo)
                .setGroup(GROUP_KEY_UPDATES)
                .setContentTitle("Amoled Black Theme")
                .setContentText(body).setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSound(notificationSound);

        Intent resultIntent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        notifiBuilder.setContentIntent(resultPendingIntent);

        notificationManager.notify(155155155, notifiBuilder.build());
    }
}
