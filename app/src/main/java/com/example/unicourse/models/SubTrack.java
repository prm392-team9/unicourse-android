package com.example.unicourse.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class SubTrack {
    private String title;
    private String content_url;
    private Number position;
    private Number duration;
    private String type;
    private String platform;

    public SubTrack() {
    }

    public SubTrack(String title, String content_url, Number position, Number duration, String type, String platform) {
        this.title = title;
        this.content_url = content_url;
        this.position = position;
        this.duration = duration;
        this.type = type;
        this.platform = platform;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent_url() {
        return content_url;
    }

    public void setContent_url(String content_url) {
        this.content_url = content_url;
    }

    public Number getPosition() {
        return position;
    }

    public void setPosition(Number position) {
        this.position = position;
    }

    public Number getDuration() {
        return duration;
    }

    public void setDuration(Number duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    @Override
    public String toString() {
        return "SubTrack{" +
                "title='" + title + '\'' +
                ", content_url='" + content_url + '\'' +
                ", position=" + position +
                ", duration=" + duration +
                ", type='" + type + '\'' +
                ", platform='" + platform + '\'' +
                '}';
    }
}
