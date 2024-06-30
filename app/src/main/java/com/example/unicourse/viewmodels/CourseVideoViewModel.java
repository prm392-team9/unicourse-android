package com.example.unicourse.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.unicourse.models.course.Course;
import com.example.unicourse.models.common.CommonResponse;
import com.example.unicourse.services.CourseApiService;
import com.example.unicourse.contants.ApiConstants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CourseVideoViewModel extends ViewModel {
    private MutableLiveData<Course> course = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public LiveData<Course> getCourse() {
        return course;
    }

    public LiveData<Boolean> isLoading() {
        return isLoading;
    }

    public void loadCourse(String courseId) {
        isLoading.setValue(true);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CourseApiService courseApiService = retrofit.create(CourseApiService.class);
        Call<CommonResponse<Course>> call = courseApiService.getDetailCourse(courseId);

        call.enqueue(new Callback<CommonResponse<Course>>() {
            @Override
            public void onResponse(Call<CommonResponse<Course>> call, Response<CommonResponse<Course>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    course.setValue(response.body().getData());
                } else {
                    course.setValue(null);
                }
                isLoading.setValue(false);
            }

            @Override
            public void onFailure(Call<CommonResponse<Course>> call, Throwable t) {
                course.setValue(null);
                isLoading.setValue(false);
            }
        });
    }
}
