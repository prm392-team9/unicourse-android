package com.example.unicourse.models;

import java.util.ArrayList;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
// TrackCourse and SubTrack classes should also be defined accordingly
public class TrackCourse {
    private String chapterTitle;
    private ArrayList<SubTrack> track_steps;
    private Number position;

    @Override
    public String toString() {
        return "TrackCourse{" +
                "chapterTitle='" + chapterTitle + '\'' +
                ", track_steps=" + track_steps +
                ", position=" + position +
                '}';
    }
}
