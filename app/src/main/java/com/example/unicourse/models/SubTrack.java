package com.example.unicourse.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubTrack {
    private String title;
    private String content_url;
    private Number position;
    private Number duration;
    private String type;
    private String platform;


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
