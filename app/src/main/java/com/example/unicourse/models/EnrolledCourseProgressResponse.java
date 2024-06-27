package com.example.unicourse.models;

import java.util.ArrayList;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrolledCourseProgressResponse {
    private String message;
    private int status;
    private ArrayList<EnrolledCourse> data;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EnrolledCourse {
        private String _id;
        private User user;
        private Course course;
        private float progress;
        private Date enrollDate;
        private boolean completed;
        private ArrayList<Object> trackProgress; // Assuming trackProgress is a list of unknown objects
        private Date created_at;
        private Date updated_at;
        private int __v;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class User {
            private String _id;
            private String email;
            private String fullName;
            private String profileName;
            private String profile_image;
        }

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Course {
            private String _id;
            private String title;
            private String titleDescription;
            private String subTitle;
            private ArrayList<String> subTitleDescription;
            private int enrollmentCount;
            private String type;
            private String thumbnail;
            private Lecture lecture;
            private int semester_number;

            @Data
            @AllArgsConstructor
            @NoArgsConstructor
            public static class Lecture {
                private String _id;
                private String email;
                private String fullName;
                private String profileName;
                private String profile_image;
                private Date created_at;
                private Date updated_at;
            }
        }
    }

}
