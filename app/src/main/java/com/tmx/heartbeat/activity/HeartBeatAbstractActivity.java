package com.tmx.heartbeat.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tmx.heartbeat.dto.HeartBeatDTO;
import com.tmx.heartbeat.service.HeartBeatMessagingService;

import java.util.ArrayList;
import java.util.List;

public abstract class HeartBeatAbstractActivity extends AppCompatActivity {

    protected interface HeartBeatListener {
        void onHeartBeatMessageListener(Object sender,HeartBeatDTO dto);
    }

    private class HeartBeatContainer{
        private Object object;
        private String job;
        private HeartBeatListener heartBeatListener;

        public Object getObject() {
            return object;
        }

        public void setObject(Object object) {
            this.object = object;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public HeartBeatListener getHeartBeatListener() {
            return heartBeatListener;
        }

        public void setHeartBeatListener(HeartBeatListener heartBeatListener) {
            this.heartBeatListener = heartBeatListener;
        }
    }

    private List<HeartBeatContainer> heartBeatContainerList = new ArrayList<>();

    private BroadcastReceiver heartbeatReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            HeartBeatDTO dto = intent.getParcelableExtra("heartbeat");
            updateUI(dto);
        }
    };

    protected void addHeartBeat(Object object, String job, HeartBeatListener heartBeatListener){
        HeartBeatContainer heartBeatContainer = new HeartBeatContainer();
        heartBeatContainer.setObject(object);
        heartBeatContainer.setJob(job);
        heartBeatContainer.setHeartBeatListener(heartBeatListener);
        heartBeatContainerList.add(heartBeatContainer);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerReceiver(heartbeatReceiver
                , new IntentFilter(HeartBeatMessagingService.INTENT_FILTER));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(heartbeatReceiver);
    }

    protected void updateUI(HeartBeatDTO dto){
        for (HeartBeatContainer h:heartBeatContainerList) {
            if(h.getJob().equals(dto.getName()) && h.getHeartBeatListener() != null){
                h.getHeartBeatListener().onHeartBeatMessageListener(h.getObject(), dto);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
