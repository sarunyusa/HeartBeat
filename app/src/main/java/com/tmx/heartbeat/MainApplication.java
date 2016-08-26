package com.tmx.heartbeat;

import android.app.Application;

import com.tmx.heartbeat.data.HeartBeatDB;
import com.tmx.heartbeat.dto.HeartBeatDTO;
import com.tmx.heartbeat.util.Const;
import com.tmx.heartbeat.util.Contextor;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Contextor.getInstance().init(getApplicationContext());
        prepareDB();
    }

    private void prepareDB(){
        insertWhenNotExists(Const.JOB_AUTH);
        insertWhenNotExists(Const.JOB_BPAY);
        insertWhenNotExists(Const.JOB_TOPUP);
        insertWhenNotExists(Const.JOB_BCI);
        insertWhenNotExists(Const.JOB_BCI_KBANK);
        insertWhenNotExists(Const.JOB_BCI_BBL);
    }

    private void insertWhenNotExists(String name){
        HeartBeatDB heartBeatDB = HeartBeatDB.getInstance();
        if(!heartBeatDB.isJobExists(name)){
            HeartBeatDTO dto = new HeartBeatDTO();
            dto.setName(name);
            dto.setCommand("unknown");
            dto.setServer("unknown");
            dto.setPort("unknown");
            dto.setStatus(true);
            dto.setLastUpdatedDate("unknown");
            heartBeatDB.insertServer(dto);
        }
    }

}
