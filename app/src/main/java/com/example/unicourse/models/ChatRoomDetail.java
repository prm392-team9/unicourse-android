package com.example.unicourse.models;

import com.example.unicourse.models.authentication.User;
import com.google.gson.annotations.SerializedName;

import java.util.List;

// ChatRoomDetail.java
public class ChatRoomDetail {
    @SerializedName("_id")
    private String id;
    private String name;
    private String status;
    private int memberCount;
    private String thumbnail;
    private String createdAt;
    private String updatedAt;
    private List<Message> messages;
    private List<User> users;

    public ChatRoomDetail() {
    }

    public ChatRoomDetail(String id, String name, String status, int memberCount, String thumbnail, String createdAt, String updatedAt, List<Message> messages, List<User> users) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.memberCount = memberCount;
        this.thumbnail = thumbnail;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.messages = messages;
        this.users = users;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}

