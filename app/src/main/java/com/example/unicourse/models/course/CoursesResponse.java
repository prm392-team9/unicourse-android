package com.example.unicourse.models.course;

import java.util.ArrayList;
import java.util.List;

public class CoursesResponse {
    private String message;
    private Number status;
    private List<Course> data;

    public CoursesResponse() {
    }

    public CoursesResponse(String message, Number status, List<Course> data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Number getStatus() {
        return status;
    }

    public void setStatus(Number status) {
        this.status = status;
    }

    public ArrayList<Course> getData() {
        return (ArrayList<Course>) data;
    }

    public void setData(List<Course> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CoursesResponse{" +
                "message='" + message + '\'' +
                ", status=" + status +
                ", data=" + data +
                '}';
    }
}

