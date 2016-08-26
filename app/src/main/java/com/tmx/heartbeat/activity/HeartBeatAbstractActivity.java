package com.tmx.heartbeat.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.tmx.heartbeat.R;
import com.tmx.heartbeat.service.HeartBeatMessagingService;

public abstract class HeartBeatAbstractActivity extends Activity {

    private BroadcastReceiver heartbeatReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateUI();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        initView();

        registerReceiver(heartbeatReceiver
                , new IntentFilter(HeartBeatMessagingService.INTENT_FILTER));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(heartbeatReceiver);
    }

    protected abstract void initView();

    protected void updateUI(){

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
