package com.example.unicourse.models.course;
import com.example.unicourse.models.lecture.Lecture;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class Course {

    private String _id;
    private String title;
    private String titleDescription;
    private String subTitle;
    private List<String> subTitleDescription;
    private List<TrackCourse> tracks;
    private int enrollmentCount;
    private String status;
    private String type;
    private double amount;
    private String thumbnail;
    private Lecture lecture;
    private int semester_number;

    private Date created_at;

    public Course() {
    }

    public Course(String _id, String title, String titleDescription, String subTitle, List<String> subTitleDescription, List<TrackCourse> tracks, int enrollmentCount, String status, String type, double amount, String thumbnail, Lecture lecture, int semester_number, Date created_at) {
        this._id = _id;
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
        this.semester_number = semester_number;
        this.created_at = created_at;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public List<String> getSubTitleDescription() {
        return subTitleDescription;
    }

    public void setSubTitleDescription(List<String> subTitleDescription) {
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

    public Lecture getLecture() {
        return lecture;
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }

    public int getSemester_number() {
        return semester_number;
    }

    public void setSemester_number(int semester_number) {
        this.semester_number = semester_number;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at_str) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        this.created_at = dateFormat.parse(created_at_str);
    }

    @Override
    public String toString() {
        return "Course{" +
                "_id='" + _id + '\'' +
                ", title='" + title + '\'' +
                ", titleDescription='" + titleDescription + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", subTitleDescription=" + subTitleDescription +
                ", tracks=" + tracks +
                ", enrollmentCount=" + enrollmentCount +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", thumbnail='" + thumbnail + '\'' +
                ", lecture=" + lecture +
                ", semester_number=" + semester_number +
                ", created_at=" + created_at +
                '}';
    }
}

