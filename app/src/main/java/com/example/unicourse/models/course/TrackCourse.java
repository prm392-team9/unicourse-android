package com.example.unicourse.models.course;

import java.util.ArrayList;

// TrackCourse and SubTrack classes should also be defined accordingly
public class TrackCourse {
    private String chapterTitle;
    private ArrayList<SubTrack> track_steps;
    private Number position;

    public TrackCourse() {
    }

    public TrackCourse(String chapterTitle, ArrayList<SubTrack> track_steps, Number position) {
        this.chapterTitle = chapterTitle;
        this.track_steps = track_steps;
        this.position = position;
    }

    public String getChapterTitle() {
        return chapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    public ArrayList<SubTrack> getTrack_steps() {
        return track_steps;
    }

    public void setTrack_steps(ArrayList<SubTrack> track_steps) {
        this.track_steps = track_steps;
    }

    public Number getPosition() {
        return position;
    }

    public void setPosition(Number position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "TrackCourse{" +
                "chapterTitle='" + chapterTitle + '\'' +
                ", track_steps=" + track_steps +
                ", position=" + position +
                '}';
    }
}
