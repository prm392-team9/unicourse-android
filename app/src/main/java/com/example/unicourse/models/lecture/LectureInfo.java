package com.example.unicourse.models.lecture;

import com.example.unicourse.models.Feeback;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class LectureInfo {
    private String _id;
    private ArrayList<String> my_course;
    private ArrayList<String> my_schedule;
    private ArrayList<Feeback> feedback;
    private String description;

    public LectureInfo() {
    }

    public LectureInfo(String _id, ArrayList<String> my_course, ArrayList<String> my_schedule, ArrayList<Feeback> feedback, String description) {
        this._id = _id;
        this.my_course = my_course;
        this.my_schedule = my_schedule;
        this.feedback = feedback;
        this.description = description;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public ArrayList<String> getMy_course() {
        return my_course;
    }

    public void setMy_course(ArrayList<String> my_course) {
        this.my_course = my_course;
    }

    public ArrayList<String> getMy_schedule() {
        return my_schedule;
    }

    public void setMy_schedule(ArrayList<String> my_schedule) {
        this.my_schedule = my_schedule;
    }

    public ArrayList<Feeback> getFeedback() {
        return feedback;
    }

    public void setFeedback(ArrayList<Feeback> feedback) {
        this.feedback = feedback;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "LectureInfo{" +
                "_id='" + _id + '\'' +
                ", my_course=" + my_course +
                ", my_schedule=" + my_schedule +
                ", feedback=" + feedback +
                ", description='" + description + '\'' +
                '}';
    }
}
