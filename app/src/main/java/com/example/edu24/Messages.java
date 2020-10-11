package com.example.edu24;

public class Messages {
    private String message_id;
    private String message_sender;
    private String message_class;

    public Messages(String message_sender, String message_class) {
        this.setMessage_sender(message_sender);
        this.setMessage_class(message_class);
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getMessage_sender() {
        return message_sender;
    }

    public void setMessage_sender(String message_sender) {
        this.message_sender = message_sender;
    }

    public String getMessage_class() {
        return message_class;
    }

    public void setMessage_class(String message_class) {
        this.message_class = message_class;
    }
}
