package com.tmx.heartbeat.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.tmx.heartbeat.util.Contextor;
import com.tmx.heartbeat.dto.HeartBeatDTO;

public class HeartBeatDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_SERVER_NAME = "server";
    private static final String TABLE_SERVER_CREATE
            = "create table " + TABLE_SERVER_NAME
            + " (name text, command text, server text"
            + ", port text, status bool, lastUpdatedDate text);";


    private static HeartBeatDB instance;

    public static HeartBeatDB getInstance(){
        if(instance == null) instance = new HeartBeatDB(Contextor.getInstance().getContext());
        return instance;
    }

    private HeartBeatDB(Context context) {
        super(context, "heartbeat.db", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_SERVER_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query  = "DROP TABLE IF EXISTS " + TABLE_SERVER_NAME;
        db.execSQL(query);
        onCreate(db);
    }

    public void insertServer(HeartBeatDTO dto){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",dto.getName());
        values.put("command",dto.getCommand());
        values.put("server",dto.getServer());
        values.put("port",dto.getPort());
        values.put("status",dto.getStatus());
        values.put("lastUpdatedDate",dto.getLastUpdatedDate());

        database.insert(TABLE_SERVER_NAME,null,values);
    }

    public void updateServer(HeartBeatDTO dto, String name){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("command",dto.getCommand());
        values.put("server",dto.getServer());
        values.put("port",dto.getPort());
        values.put("status",dto.getStatus());
        values.put("lastUpdatedDate",dto.getLastUpdatedDate());
        database.update(TABLE_SERVER_NAME,values,"name = ?",new String[]{name});
    }

    public Cursor selectServer(String name){
        SQLiteDatabase database = this.getReadableDatabase();
        return database.query(TABLE_SERVER_NAME,null,"name = ?",new String[]{name},null,null,null);
    }

    public Boolean isJobExists(String name){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor =  database.query(TABLE_SERVER_NAME,null,"name = ?",new String[]{name},null,null,null);
        Boolean result = cursor != null && cursor.getCount() > 0;
        if(cursor != null) cursor.close();
        return result;
    }

}
