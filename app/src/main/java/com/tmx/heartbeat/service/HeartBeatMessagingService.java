package com.tmx.heartbeat.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.tmx.heartbeat.MainActivity;
import com.tmx.heartbeat.data.ServerList;
import com.tmx.heartbeat.dto.MessageDTO;
import com.tmx.heartbeat.dto.ServerDTO;

public class HeartBeatMessagingService extends FirebaseMessagingService {

    private static final String TAG = HeartBeatMessagingService.class.getName();

    public static final String INTENT_FILTER = "Heart Beat Message";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            Gson gson = new Gson();
            MessageDTO dto = gson.fromJson(remoteMessage.getData().toString(),MessageDTO.class);
            ServerList serverlist = ServerList.getInstance();
            if(!serverlist.isServerExists(dto.getServer())){
                ServerDTO insertDto = new ServerDTO();
                insertDto.setUrl(dto.getServer());
                insertDto.setStatus(dto.getIsActiveStatus());
                serverlist.insertServer(insertDto);
            }else{
                ServerDTO updateDto = new ServerDTO();
                updateDto.setStatus(dto.getIsActiveStatus());
                serverlist.updateServer(updateDto,dto.getServer());
            }

            Intent intent = new Intent(INTENT_FILTER);
            sendBroadcast(intent);
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("FCM Message")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}
