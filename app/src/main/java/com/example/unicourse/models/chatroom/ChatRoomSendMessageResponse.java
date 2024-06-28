package com.example.unicourse.models.chatroom;

import com.example.unicourse.models.course.Course;

import java.util.List;

public class ChatRoomSendMessageResponse {
    private String message;
    private Number status;
    private List<Message> data;

    public ChatRoomSendMessageResponse() {}

    public ChatRoomSendMessageResponse(String message, Number status, List<Message> data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Number getStatus() {
        return status;
    }

    public void setStatus(Number status) {
        this.status = status;
    }

    public List<Message> getData() {
        return data;
    }

    public void setData(List<Message> data) {
        this.data = data;
    }
}
