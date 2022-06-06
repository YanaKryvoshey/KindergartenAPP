package com.classy.myapplication.Object;

public class Message {
    private String date;
    private String message;

    public Message(String date, String message) {
        this.date = date;
        this.message = message;
    }

    public Message() {

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
