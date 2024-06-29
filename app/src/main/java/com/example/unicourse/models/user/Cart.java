package com.example.unicourse.models.user;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @SerializedName("_id")
    private String id;
    @SerializedName("user_id")
    private User userId;
    private int amount;
    @SerializedName("created_at")
    private String createdAt;
    private ArrayList<Item> items;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("__v")
    private int version;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User {
        @SerializedName("_id")
        private String id;
        private String email;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {
        @SerializedName("_id")
        private String id;
        private String title;
        private int amount;
        private String thumbnail;
        private boolean isSelected = false;
    }
}
