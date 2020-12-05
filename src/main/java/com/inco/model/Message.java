package com.inco.model;

import org.json.simple.JSONObject;

import java.sql.Timestamp;

public class Message {
    private final String text;
    private final Timestamp date;

    public Message(String text, Timestamp date) {
        this.text = text;
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public Timestamp getDate() {
        return date;
    }

    @Override
    public String toString() {
        Message _this = this;
        return new JSONObject(){{
            put("text", _this.text);
            put("date", _this.date.toString());
        }}.toJSONString();
    }
}
