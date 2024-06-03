package com.example.unicourse.models;
import java.util.List;

import java.util.List;

public class Course {
    private String title;
    private String titleDescription;
    private String subTitle;
    private String subTitleDescription;
    private List<TrackCourse> tracks;
    private int enrollmentCount;
    private String status;
    private String type;
    private double amount;
    private String thumbnail;
    private String lecture;
    private int semesterNumber;

    public Course() {}

    public Course(String title, String titleDescription, String subTitle, String subTitleDescription, List<TrackCourse> tracks, int enrollmentCount, String status, String type, double amount, String thumbnail, String lecture, int semesterNumber) {
        this.title = title;
        this.titleDescription = titleDescription;
        this.subTitle = subTitle;
        this.subTitleDescription = subTitleDescription;
        this.tracks = tracks;
        this.enrollmentCount = enrollmentCount;
        this.status = status;
        this.type = type;
        this.amount = amount;
        this.thumbnail = thumbnail;
        this.lecture = lecture;
        this.semesterNumber = semesterNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleDescription() {
        return titleDescription;
    }

    public void setTitleDescription(String titleDescription) {
        this.titleDescription = titleDescription;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getSubTitleDescription() {
        return subTitleDescription;
    }

    public void setSubTitleDescription(String subTitleDescription) {
        this.subTitleDescription = subTitleDescription;
    }

    public List<TrackCourse> getTracks() {
        return tracks;
    }

    public void setTracks(List<TrackCourse> tracks) {
        this.tracks = tracks;
    }

    public int getEnrollmentCount() {
        return enrollmentCount;
    }

    public void setEnrollmentCount(int enrollmentCount) {
        this.enrollmentCount = enrollmentCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getLecture() {
        return lecture;
    }

    public void setLecture(String lecture) {
        this.lecture = lecture;
    }

    public int getSemesterNumber() {
        return semesterNumber;
    }

    public void setSemesterNumber(int semesterNumber) {
        this.semesterNumber = semesterNumber;
    }

    @Override
    public String toString() {
        return "Course{" +
                "title='" + title + '\'' +
                ", titleDescription='" + titleDescription + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", subTitleDescription='" + subTitleDescription + '\'' +
                ", tracks=" + tracks +
                ", enrollmentCount=" + enrollmentCount +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", thumbnail='" + thumbnail + '\'' +
                ", lecture='" + lecture + '\'' +
                ", semesterNumber=" + semesterNumber +
                '}';
    }
}

