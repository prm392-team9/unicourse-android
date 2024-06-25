package com.example.unicourse.services;

import com.example.unicourse.models.LoginRequest;
import com.example.unicourse.models.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApiService {
    @POST("auth/basic-signIn")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);
}
