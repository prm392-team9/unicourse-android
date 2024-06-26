package com.example.unicourse.models;

import com.example.unicourse.models.authentication.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Feeback {
    private String _id;
    private String content;
    private Number rating;
    private String course_id;
    private User user_id;
    private String status;
    private Date created_at;
    private Date updated_at;

    public Feeback() {
    }

    public Feeback(String _id, String content, Number rating, String course_id, User user_id, String status, Date created_at, Date updated_at) {
        this._id = _id;
        this.content = content;
        this.rating = rating;
        this.course_id = course_id;
        this.user_id = user_id;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Number getRating() {
        return rating;
    }

    public void setRating(Number rating) {
        this.rating = rating;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public User getUser_id() {
        return user_id;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
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
        return "Feeback{" +
                "_id='" + _id + '\'' +
                ", content='" + content + '\'' +
                ", rating=" + rating +
                ", course_id='" + course_id + '\'' +
                ", user_id=" + user_id +
                ", status='" + status + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
