package com.inco.model;

import org.json.simple.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Dialog {
    private long targetUserId;
    private ArrayList<Message> messages = new ArrayList<>();

    public Dialog setTarget(final long targetUserId) {
        this.targetUserId = targetUserId;
        return this;
    }

    public Dialog setMessages(ArrayList<Message> messages) {
        this.messages = messages;
        return this;
    }

    public Dialog setMessages(Message[] messages) {
        return this.setMessages(Arrays
                .stream(messages)
                .collect(Collectors.toCollection(ArrayList::new)));
    }

    public Dialog addMessage(final String text, final Timestamp date) {
        this.messages.add(new Message(text, date));
        return this;
    }

    public Dialog addMessage(final Message message) {
        this.messages.add(message);
        return this;
    }

    @Override
    public String toString() {
        Dialog _this = this;
        return new JSONObject(){{
            put("target", _this.targetUserId);
            put("messages", _this.messages);
        }}.toJSONString();
    }
}
