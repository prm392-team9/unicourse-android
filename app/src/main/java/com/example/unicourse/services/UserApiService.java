package com.example.unicourse.services;

import com.example.unicourse.models.EnrolledCourseProgressResponse;
import com.example.unicourse.models.ProfileResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApiService {
    @GET("user/{userId}")
    Call<ProfileResponse> getUser(@Path("userId") String userId);

    @GET("user/{userId}/get-enrolled-course")
    Call<EnrolledCourseProgressResponse> getCourseProgress(@Path("userId") String userId);
}
