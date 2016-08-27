package com.tmx.heartbeat.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tmx.heartbeat.R;
import com.tmx.heartbeat.dto.HeartBeatDTO;
import com.tmx.heartbeat.util.Const;

public class MainMenuActivity extends HeartBeatAbstractActivity {

    private Button btnTopup;
    private Button btnBPay;
    private Button btnBCI;

    private HeartBeatListener heartBeatButtonListener = new HeartBeatListener() {
        @Override
        public void onHeartBeatMessageListener(Object sender, HeartBeatDTO dto) {
            Button btn = (Button) sender;
            btn.setEnabled(dto.getStatus());
            if (dto.getStatus()) {
                btn.setBackgroundColor(ContextCompat.getColor(MainMenuActivity.this, R.color.colorPrimary));
            } else {
                btn.setBackgroundColor(Color.GRAY);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_main_menu);
        initView();
    }

    protected void initView() {

        btnTopup = (Button) findViewById(R.id.btnTopup);
        btnBPay = (Button) findViewById(R.id.btnBPay);
        btnBCI = (Button) findViewById(R.id.btnBCI);

        btnBCI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBCI_Click();
            }
        });

        addHeartBeat(btnTopup, Const.JOB_TOPUP, heartBeatButtonListener);
        addHeartBeat(btnBPay, Const.JOB_BPAY, heartBeatButtonListener);
        addHeartBeat(btnBCI, Const.JOB_BCI, heartBeatButtonListener);

    }

    private void btnBCI_Click(){
        Intent intent = new Intent(MainMenuActivity.this,BCIActivity.class);
        startActivity(intent);
    }

}
