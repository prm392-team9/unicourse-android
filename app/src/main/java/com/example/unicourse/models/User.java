package com.example.unicourse.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class User {
    private String _id;
    private String email;
    private String fullName;
    private String profileName;
    private String profile_image;

    private String role;
    private Date created_at;
    private Date updated_at;

    public User() {
    }

    public User(String _id, String email, String fullName, String profileName, String profile_image, String role, Date created_at, Date updated_at) {
        this._id = _id;
        this.email = email;
        this.fullName = fullName;
        this.profileName = profileName;
        this.profile_image = profile_image;
        this.role = role;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public User(String _id, String email, String fullName, String profileName, String profile_image, String role) {
        this._id = _id;
        this.email = email;
        this.fullName = fullName;
        this.profileName = profileName;
        this.profile_image = profile_image;
        this.role = role;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getProfileImage() {
        return profile_image;
    }

    public void setProfileImage(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public void setCreated_at(String created_at_str) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        this.created_at = dateFormat.parse(created_at_str);
    }

    public void setUpdated_at(String updated_at_str) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        this.updated_at = dateFormat.parse(updated_at_str);
    }

    @Override
    public String toString() {
        return "User{" +
                "_id='" + _id + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", profileName='" + profileName + '\'' +
                ", profile_image='" + profile_image + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
