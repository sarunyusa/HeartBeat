package com.tmx.heartbeat.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tmx.heartbeat.R;
import com.tmx.heartbeat.dto.HeartBeatDTO;
import com.tmx.heartbeat.util.Const;

public class LoginActivity extends HeartBeatAbstractActivity {

    private final String TAG = LoginActivity.class.getName();

    private EditText etUser;
    private EditText etPassword;
    private Button btnLogin;

    private HeartBeatListener heartBeatButtonListener = new HeartBeatListener() {
        @Override
        public void onHeartBeatMessageListener(Object sender, HeartBeatDTO dto) {
            Button btn = (Button) sender;
            btn.setEnabled(dto.getStatus());
            if (dto.getStatus()) {
                btn.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.colorPrimary));
            } else {
                btn.setBackgroundColor(Color.GRAY);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    protected void initView() {
        etUser = (EditText) findViewById(R.id.etUser);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goMainMenu();
            }
        });

        addHeartBeat(btnLogin, Const.JOB_AUTH, heartBeatButtonListener);
    }


    private void goMainMenu() {
        Log.d(TAG, "log in click");
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }

}
