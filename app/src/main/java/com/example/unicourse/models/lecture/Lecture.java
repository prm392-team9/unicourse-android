package com.example.unicourse.models.lecture;

public class Lecture {
    private String _id;
    private String email;
    private String fullName;
    private String profileName;
    private String profile_image;
    private LectureInfo lecture_info;

    public Lecture() {
    }

    public Lecture(String _id, String email, String fullName, String profileName, String profile_image, LectureInfo lecture_info) {
        this._id = _id;
        this.email = email;
        this.fullName = fullName;
        this.profileName = profileName;
        this.profile_image = profile_image;
        this.lecture_info = lecture_info;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public LectureInfo getLecture_info() {
        return lecture_info;
    }

    public void setLecture_info(LectureInfo lecture_info) {
        this.lecture_info = lecture_info;
    }

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

