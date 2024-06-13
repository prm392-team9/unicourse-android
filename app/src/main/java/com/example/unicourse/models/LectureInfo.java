package com.example.unicourse.models;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LectureInfo {
    private String _id;
    private ArrayList<String> my_course;
    private ArrayList<String> my_schedule;
    private ArrayList<Feeback> feedback;
    private String description;

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
