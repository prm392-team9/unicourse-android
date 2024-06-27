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
    private boolean isDataLoaded = false;

    public LiveData<ArrayList<Course>> getCourses() {
        return courses;
    }

    public void loadCourses() {
        if (isDataLoaded) return;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CourseApiService courseApiService = retrofit.create(CourseApiService.class);
        Call<CoursesResponse> call = courseApiService.getCourses();

        call.enqueue(new Callback<CoursesResponse>() {
            @Override
            public void onResponse(Call<CoursesResponse> call, Response<CoursesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    courses.setValue(response.body().getData());
                    isDataLoaded = true;
                }
            }

            @Override
            public void onFailure(Call<CoursesResponse> call, Throwable t) {
                // Handle failure
            }
        });
    }

    public void refreshCourses() {
        isDataLoaded = false;
        loadCourses();
    }
}
