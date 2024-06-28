package com.example.unicourse.services;
import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl, final String accessToken) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

        // Add interceptor to check for access token
        clientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                if (accessToken != null && !accessToken.isEmpty()) {
                    Request.Builder builder = originalRequest.newBuilder()
                            .header("Authorization", "Bearer " + accessToken);
                    Request newRequest = builder.build();
                    return chain.proceed(newRequest);
                }
                return chain.proceed(originalRequest);
            }
        });

        OkHttpClient client = clientBuilder.build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}