package edu.udacity.java.nano.chat.model;

import com.alibaba.fastjson.JSON;

public class Message {
    // TODO: add message model.
    public enum Type {
        ENTER, SPEAK, LEAVE;
    }

    private Type type;
    private String username;
    private String msg;
    private int onlineCount;

    public Message(Type type, String username, String msg, int onlineCount) {
        this.type = type;
        this.username = username;
        this.msg = msg;
        this.onlineCount = onlineCount;
    }

    public String toJSONString() {
        return JSON.toJSONString(this);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(int onlineCount) {
        this.onlineCount = onlineCount;
    }
}
