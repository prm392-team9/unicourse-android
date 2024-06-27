package com.example.unicourse.services;

import com.example.unicourse.models.course.Course;
import com.example.unicourse.models.course.CoursesResponse;
import com.example.unicourse.models.common.CommonResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CourseApiService {
    @GET("course/free-course")
    Call<CoursesResponse> getCourses();

    @GET("course/{id}")
    Call<CommonResponse<Course>> getDetailCourse(@Path("id") String courseId);
}
