package com.example.unicourse.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.unicourse.models.course.Course;
import com.example.unicourse.models.course.CoursesResponse;
import com.example.unicourse.contants.ApiConstants;
import com.example.unicourse.services.CourseApiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LandingViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<Course>> courses = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Course>> proCourses = new MutableLiveData<>();
    private final Retrofit retrofit;
    private final CourseApiService courseApiService;

    private boolean isDataLoaded = false;
    private boolean isDataLoaded1 = false;

    public LandingViewModel() {
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        courseApiService = retrofit.create(CourseApiService.class);
    }

    public LiveData<ArrayList<Course>> getCourses() {
        return courses;
    }

    public LiveData<ArrayList<Course>> getProCourses() {
        return proCourses;
    }

    public void loadCourses() {
        if (isDataLoaded && isDataLoaded1) return;

        Call<CoursesResponse> call = courseApiService.getCourses();
        Call<CoursesResponse> call1 = courseApiService.getProCourses();

        call.enqueue(new Callback<CoursesResponse>() {
            @Override
            public void onResponse(Call<CoursesResponse> call, Response<CoursesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    courses.setValue(response.body().getData());
                    isDataLoaded = true;
                } else {
                    courses.setValue(new ArrayList<>()); // Set empty list on failure
                }
            }

            @Override
            public void onFailure(Call<CoursesResponse> call, Throwable t) {
                courses.setValue(new ArrayList<>()); // Set empty list on failure
                // Handle failure (e.g., log error, show message)
            }
        });

        call1.enqueue(new Callback<CoursesResponse>() {
            @Override
            public void onResponse(Call<CoursesResponse> call, Response<CoursesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    proCourses.setValue(response.body().getData());
                    isDataLoaded1 = true;
                } else {
                    proCourses.setValue(new ArrayList<>()); // Set empty list on failure
                }
            }

            @Override
            public void onFailure(Call<CoursesResponse> call, Throwable t) {
                proCourses.setValue(new ArrayList<>()); // Set empty list on failure
                // Handle failure (e.g., log error, show message)
            }
        });
    }

    public void refreshCourses() {
        isDataLoaded = false;
        isDataLoaded1 = false;
        loadCourses();
    }
}