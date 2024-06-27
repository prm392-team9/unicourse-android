package com.example.unicourse.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.unicourse.R;
import com.example.unicourse.adapters.ProfileAdapter;
import com.example.unicourse.models.EnrolledCourseProgressResponse;
import com.example.unicourse.models.ProfileCourse;
import com.example.unicourse.models.ProfileResponse;
import com.example.unicourse.services.ApiConstants;
import com.example.unicourse.services.UserApiService;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileFragment extends Fragment {

    private static final String BASE_URL = ApiConstants.BASE_URL;
    private String accessToken = null;
    private String userId = null;
    private ProfileResponse userProfileData = null;
    private ProfileAdapter mProfileAdapter;
    private ArrayList<ProfileCourse> profileCourses = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView usernameTxt = view.findViewById(R.id.usernameTxt);
        ImageView userImage = view.findViewById(R.id.userImage);
        TextView progressAmount = view.findViewById(R.id.progressAmount);
        TextView accomplishAmount = view.findViewById(R.id.accomplishAmount);
        TextView courseAmount = view.findViewById(R.id.courseAmount);
        RecyclerView recentCourseRV = view.findViewById(R.id.recentCourseRV);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        accessToken = sharedPreferences.getString("access_token", null);
        userId = sharedPreferences.getString("user_id", null);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        Request.Builder builder = originalRequest.newBuilder()
                                .header("Authorization", "Bearer " + accessToken);
                        Request newRequest = builder.build();
                        return chain.proceed(newRequest);
                    }
                })
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserApiService userApiService = retrofit.create(UserApiService.class);

//        Load User's information.
        Call<ProfileResponse> userCall = userApiService.getUser(userId);
        userCall.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ProfileResponse profileResponse = response.body();
                    if (profileResponse.getStatus() == 200) {
                        userProfileData = profileResponse;
                        if (userProfileData != null) {
                            usernameTxt.setText(userProfileData.getData().getFullName());
                            Glide.with(requireActivity()).load(userProfileData.getData().getProfileImage()).into(userImage);
                        }
                    } else {
                        Toast.makeText(requireActivity(), "Failed to get User data for Profile Screen", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireActivity(), "Failed to get data from Profile Screen", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable throwable) {
                Toast.makeText(requireActivity(), "Failed get User data for Profile Screen", Toast.LENGTH_SHORT).show();
            }
        });

//        Load User's courses' progress.
        Call<EnrolledCourseProgressResponse> progressCall = userApiService.getCourseProgress(userId);
        progressCall.enqueue(new Callback<EnrolledCourseProgressResponse>() {
            @Override
            public void onResponse(Call<EnrolledCourseProgressResponse> call, Response<EnrolledCourseProgressResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    EnrolledCourseProgressResponse progressResponse = response.body();
                    if (progressResponse.getStatus() == 200) {
                        int progressAmountCount = 0;
                        int accomplishAmountCount = 0;
                        if (progressResponse.getData() != null) {
                            for (EnrolledCourseProgressResponse.EnrolledCourse course : progressResponse.getData()) {
                                profileCourses.add(new ProfileCourse(course.getCourse().getTitle(), course.getCourse().getThumbnail()));
                                progressAmountCount += course.getProgress();
                                if (course.isCompleted()) {
                                    accomplishAmountCount++;
                                }
                            }
                        }
                        progressAmount.setText(String.valueOf(progressAmountCount) + " Giờ");
                        accomplishAmount.setText(String.valueOf(accomplishAmountCount/progressResponse.getData().size() * 100) + "%");
                        courseAmount.setText(String.valueOf(progressResponse.getData().size()) + " Khóa");
                        mProfileAdapter = new ProfileAdapter(requireActivity(), profileCourses);
                        recentCourseRV.setAdapter(mProfileAdapter);
                        recentCourseRV.setLayoutManager(new LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false));
                    } else {
                        Toast.makeText(requireActivity(), "Failed to get User's course progress", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireActivity(), "Failed to get progress data from Profile Screen", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EnrolledCourseProgressResponse> call, Throwable throwable) {
                Toast.makeText(requireActivity(), "Failed to get progress data from Profile Screen", Toast.LENGTH_SHORT).show();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize your views here if necessary
    }
}