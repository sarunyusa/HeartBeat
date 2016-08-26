package com.tmx.heartbeat.dto;

import com.google.gson.annotations.SerializedName;

public class MessageDTO {

    @SerializedName("name")
    private String name;

    @SerializedName("command")
    private String command;

    @SerializedName("server")
    private String server;

    @SerializedName("port")
    private String port;

    @SerializedName("isActiveStatus")
    private Boolean isActiveStatus;

    @SerializedName("lastUpdateData")
    private String lastUpdateData;

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

    public Boolean getIsActiveStatus() {
        return isActiveStatus;
    }

    public void setIsActiveStatus(Boolean isActiveStatus) {
        this.isActiveStatus = isActiveStatus;
    }

    public String getLastUpdateData() {
        return lastUpdateData;
    }

    public void setLastUpdateData(String lastUpdateData) {
        this.lastUpdateData = lastUpdateData;
    }
}
