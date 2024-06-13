package com.example.unicourse.services;

import com.example.unicourse.models.CoursesResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CourseApiService {
    @GET("course/free-course")
    Call<CoursesResponse> getCourses();
}
