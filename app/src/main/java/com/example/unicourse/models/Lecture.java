package com.example.unicourse.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lecture {
    private String _id;
    private String email;
    private String fullName;
    private String profileName;
    private String profile_image;
    private LectureInfo lecture_info;

    @Override
    public String toString() {
        return "Lecture{" +
                "_id='" + _id + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", profileName='" + profileName + '\'' +
                ", profile_image='" + profile_image + '\'' +
                ", lecture_info=" + lecture_info +
                '}';
    }
}

