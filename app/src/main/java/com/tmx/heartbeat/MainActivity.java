package com.tmx.heartbeat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.tmx.heartbeat.service.MyFirebaseInstanceIDService;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getName();

    private TextView txtTemp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if(!checkPlayServices()){
//            this.finish();
//        }

        txtTemp = (TextView) findViewById(R.id.txtTemp);

//        txtTemp.setText(FirebaseInstanceId.getInstance().getToken());

//        if (getIntent().getExtras() != null) {
//            for (String key : getIntent().getExtras().keySet()) {
//                String value = getIntent().getExtras().getString(key);
//                Log.d(TAG, "Key: " + key + " Value: " + value);
//            }
//        }
        // [END handle_data_extras]

        Button subscribeButton = (Button) findViewById(R.id.subscribeButton);
        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // [START subscribe_topics]
                FirebaseMessaging.getInstance().subscribeToTopic("news");
                // [END subscribe_topics]

                // Log and toast
                String msg = "sub";
                Log.d(TAG, msg);
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

        Button logTokenButton = (Button) findViewById(R.id.logTokenButton);
        logTokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get token
                String token = FirebaseInstanceId.getInstance().getToken();

                // Log and toast
                String msg = token;
                Log.d(TAG, msg);
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                txtTemp.setText(token);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
//
//    private boolean checkPlayServices() {
//        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
//        int result = googleAPI.isGooglePlayServicesAvailable(this);
//        if(result != ConnectionResult.SUCCESS) {
//            if(googleAPI.isUserResolvableError(result)) {
//                googleAPI.getErrorDialog(this, result,1001).show();
//            }
//
//            return false;
//        }
//
//        return true;
//    }
}
