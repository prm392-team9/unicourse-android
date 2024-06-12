package com.example.unicourse.models;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private String lecture;
    private int semester_number;

    private Date created_at;

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
                ", lecture='" + lecture + '\'' +
                ", semester_number=" + semester_number +
                ", created_at=" + created_at +
                '}';
    }
}

