package com.ts.entity;

public class Message {

    public long id;
    public User receiver;
    public User sender;
    public String type;
    public String datetime;
    public String message;

    public Message() {
    }

    public Message(long id, User receiver, User sender, String type, String datetime, String message) {
        this.id = id;
        this.receiver = receiver;
        this.sender = sender;
        this.type = type;
        this.datetime = datetime;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
