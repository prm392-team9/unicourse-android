package com.example.unicourse.services;

import com.example.unicourse.models.course.Course;
import com.example.unicourse.models.course.CoursesResponse;
import com.example.unicourse.models.common.CommonResponse;
import com.example.unicourse.models.course.EnrollCourse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CourseApiService {
    @GET("course/free-course")
    Call<CoursesResponse> getCourses();

    @GET("course/get-all-course-fee")
    Call<CoursesResponse> getProCourses();

    @GET("course/{id}")
    Call<CommonResponse<Course>> getDetailCourse(@Path("id") String courseId);

    @GET("user/{userId}/get-enrolled-course")
    Call<CommonResponse<List<EnrollCourse>>> getEnrolledCourse(@Path("userId") String userId);
}
