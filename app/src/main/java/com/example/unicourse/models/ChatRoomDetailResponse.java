package com.example.unicourse.models;

import java.util.List;

public class ChatRoomDetailResponse {
    private String message;
    private Number status;
    private ChatRoomDetail data;

    public ChatRoomDetailResponse() {}

    public ChatRoomDetailResponse(String message, Number status, ChatRoomDetail data) {
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

    public ChatRoomDetail getData() {
        return data;
    }

    public void setData(ChatRoomDetail data) {
        this.data = data;
    }
}
