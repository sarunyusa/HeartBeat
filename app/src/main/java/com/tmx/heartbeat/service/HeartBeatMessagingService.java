package com.tmx.heartbeat.service;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.tmx.heartbeat.data.HeartBeatDB;
import com.tmx.heartbeat.dto.MessageDTO;
import com.tmx.heartbeat.dto.HeartBeatDTO;

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
            HeartBeatDB serverlist = HeartBeatDB.getInstance();

            HeartBeatDTO updateDto = new HeartBeatDTO();
            updateDto.setName(dto.getName());
            updateDto.setCommand(dto.getCommand());
            updateDto.setServer(dto.getServer());
            updateDto.setPort(dto.getPort());
            updateDto.setStatus(dto.getIsActiveStatus());
            updateDto.setLastUpdatedDate(dto.getLastUpdateData());

            if(!serverlist.isJobExists(dto.getName())){
                serverlist.insertServer(updateDto);
            }else{
                serverlist.updateServer(updateDto,dto.getName());
            }

            Intent intent = new Intent(INTENT_FILTER);
            sendBroadcast(intent);
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

    }


}
