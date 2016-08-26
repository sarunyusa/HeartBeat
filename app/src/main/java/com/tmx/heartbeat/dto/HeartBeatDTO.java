package com.tmx.heartbeat.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class HeartBeatDTO implements Parcelable {

    private String name;
    private String command;
    private String server;
    private String port;
    private Boolean status;
    private String lastUpdatedDate;

    public HeartBeatDTO(){}

    protected HeartBeatDTO(Parcel in) {
        name = in.readString();
        command = in.readString();
        server = in.readString();
        port = in.readString();
        status = Boolean.valueOf(in.readString());
        lastUpdatedDate = in.readString();
    }

    public static final Creator<HeartBeatDTO> CREATOR = new Creator<HeartBeatDTO>() {
        @Override
        public HeartBeatDTO createFromParcel(Parcel in) {
            return new HeartBeatDTO(in);
        }

        @Override
        public HeartBeatDTO[] newArray(int size) {
            return new HeartBeatDTO[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(command);
        dest.writeString(server);
        dest.writeString(port);
        dest.writeString(status.toString());
        dest.writeString(lastUpdatedDate);
    }
}
