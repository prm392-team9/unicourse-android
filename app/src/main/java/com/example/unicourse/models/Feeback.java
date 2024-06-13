package com.example.unicourse.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feeback {
    private String _id;
    private String content;
    private Number rating;
    private String course_id;
    private User user_id;
    private String status;
    private Date created_at;
    private Date updated_at;

    public void setCreated_at(String created_at_str) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        this.created_at = dateFormat.parse(created_at_str);
    }

    public void setUpdated_at(String updated_at_str) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        this.updated_at = dateFormat.parse(updated_at_str);
    }

    @Override
    public String toString() {
        return "Feeback{" +
                "_id='" + _id + '\'' +
                ", content='" + content + '\'' +
                ", rating=" + rating +
                ", course_id='" + course_id + '\'' +
                ", user_id=" + user_id +
                ", status='" + status + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
