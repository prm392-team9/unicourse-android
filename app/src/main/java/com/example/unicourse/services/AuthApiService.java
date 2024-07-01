package com.example.unicourse.services;

import com.example.unicourse.models.authentication.LoginRequest;
import com.example.unicourse.models.authentication.LoginResponse;
import com.example.unicourse.models.authentication.RegisterResponse;
import com.example.unicourse.models.common.CommonResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApiService {
    @POST("auth/basic-signIn")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("auth/basic-signUp")
    Call<CommonResponse<RegisterResponse>> registerUser(@Body LoginRequest loginRequest);
}
