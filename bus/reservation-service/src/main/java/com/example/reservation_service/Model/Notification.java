package com.example.reservation_service.Model;

public class Notification {

    private String message;

    public Notification(String message) {
        this.message = message;
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}