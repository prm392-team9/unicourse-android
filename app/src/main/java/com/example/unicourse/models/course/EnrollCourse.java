package com.example.unicourse.models.course;
import com.example.unicourse.models.authentication.User;
import com.example.unicourse.models.course.Course;
import com.example.unicourse.models.course.TrackCourse;

import java.util.List;

public class EnrollCourse {
    private String _id;
    private User user;
    private Course course;
    private String enrollDate;
    private boolean completed;
    private String createdAt;
    private String updatedAt;

    public EnrollCourse() {}

    public EnrollCourse(String _id, User user, Course course, String enrollDate, boolean completed, String createdAt, String updatedAt) {
        this._id = _id;
        this.user = user;
        this.course = course;
        this.enrollDate = enrollDate;
        this.completed = completed;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getEnrollDate() {
        return enrollDate;
    }

    public void setEnrollDate(String enrollDate) {
        this.enrollDate = enrollDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
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
}
