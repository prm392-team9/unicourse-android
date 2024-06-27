package com.example.unicourse.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProfileResponse {
    private String message;
    private int status;
    private ProfileData data;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public class ProfileData {
        @SerializedName("quiz_interest")
        private ArrayList<Object> quizInterest;
        private ArrayList<Object> interests;
        @SerializedName("device_token")
        private ArrayList<Object> deviceToken;
        @SerializedName("_id")
        private String id;
        private String email;
        private String fullName;
        private String profileName;
        private String dateOfBirth;
        private ArrayList<Object> enrollCourses;
        private String role;
        @SerializedName("is_comment_blocked")
        private boolean isCommentBlocked;
        @SerializedName("is_blocked")
        private boolean isBlocked;
        @SerializedName("is_chat_blocked")
        private boolean isChatBlocked;
        @SerializedName("profile_image")
        private String profileImage;
        @SerializedName("published_at")
        private String publishedAt;
        private int userClass;
        private ArrayList<Object> coins;
        @SerializedName("created_at")
        private String createdAt;
        @SerializedName("updated_at")
        private String updatedAt;
        @SerializedName("__v")
        private int v;
        @SerializedName("quiz_process")
        private ArrayList<Object> quizProcess;
        @SerializedName("wish_list")
        private ArrayList<Object> wishList;
        @SerializedName("recommended_courses")
        private ArrayList<Object> recommendedCourses;

    }

    @Override
    public String toString() {
        return "ProfileResponse{" +
                "message='" + message + '\'' +
                ", status=" + status +
                ", data=" + data +
                '}';
    }
}
