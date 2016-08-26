package com.tmx.heartbeat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.tmx.heartbeat.data.HeartBeatDB;
import com.tmx.heartbeat.dto.HeartBeatDTO;
import com.tmx.heartbeat.service.HeartBeatMessagingService;

public class TestActivity extends AppCompatActivity {

    private final String TAG = TestActivity.class.getName();

    private TextView tvTemp;
    private TextView tvStatus;

    private EditText etURL;

    private Button btnInitial;
    private Button btnGetStatus;
    private Button btnChangeStatus;

    private HeartBeatDB serverList = HeartBeatDB.getInstance();

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

        registerReceiver(heartbeatReceiver, new IntentFilter(HeartBeatMessagingService.INTENT_FILTER));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(heartbeatReceiver);
    }

    private void initView() {
        tvTemp = (TextView) findViewById(R.id.tvTmp);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        etURL = (EditText) findViewById(R.id.etURL);
        btnInitial = (Button) findViewById(R.id.btnInitial);
        btnGetStatus = (Button) findViewById(R.id.btnGetStatus);
        btnChangeStatus = (Button) findViewById(R.id.btnChangeStatus);

        tvTemp.setText(FirebaseInstanceId.getInstance().getToken());

        btnInitial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDatabase();
            }
        });

        btnGetStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStatus();
            }
        });

        btnChangeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeStatus();
            }
        });
    }

    private void updateUI(){
        getStatus();
    }

    private void initDatabase(){
        //do nothing
    }

    private void getStatus(){
        if(serverList == null) return;
        Cursor cursor = serverList.selectServer(etURL.getText().toString());
        if(cursor != null && cursor.getCount() > 0){
            if(cursor.moveToNext()) {
                tvStatus.setText(cursor.getString(4));
            }else{
                Toast.makeText(this,"not found",Toast.LENGTH_LONG).show();
            }
        }
        if(cursor != null) cursor.close();
    }

    private void changeStatus(){

//        if(serverList == null) return;
//
//        HeartBeatDTO dto = new HeartBeatDTO();
//
//        if(tvStatus.getText().equals("0")){
//            dto.setStatus("1");
//        }else{
//            dto.setStatus("0");
//        }
//
//        serverList.updateServer(dto,etURL.getText().toString());
//        getStatus();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
