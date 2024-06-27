package com.example.unicourse.models;

import com.example.unicourse.models.authentication.User;

import org.json.JSONException;
import org.json.JSONObject;

public class Message {
    private String message;
    private User user;

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
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
