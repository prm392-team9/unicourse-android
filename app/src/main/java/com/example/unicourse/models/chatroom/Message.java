package com.example.unicourse.models.chatroom;

import com.example.unicourse.models.authentication.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Message {
    private String _id;
    private User user;
    private String message;
    private Date date;
    private String status;


    public Message() {}

    public Message(String _id, User user, String message, Date date, String status) {
        this._id = _id;
        this.user = user;
        this.message = message;
        this.date = date;
        this.status = status;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void fromJson(JSONObject jsonObject) {
        try {
            this.message = jsonObject.getString("message");
            if (jsonObject.has("user")) {
                this.user = new User();
                this.user.fromJson(jsonObject.getJSONObject("user"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
