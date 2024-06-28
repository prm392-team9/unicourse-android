package com.example.unicourse.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.unicourse.R;
import com.example.unicourse.adapters.ProfileAdapter;
import com.example.unicourse.contants.ApiConstants;
import com.example.unicourse.models.EnrolledCourseProgressResponse;
import com.example.unicourse.models.user.ProfileCourse;
import com.example.unicourse.models.user.ProfileResponse;
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

public class ProfileActivity extends AppCompatActivity {

    private static final String BASE_URL = ApiConstants.BASE_URL;
    private final ArrayList<ProfileCourse> profileCourses = new ArrayList<>();
    private String accessToken = null;
    private String userId = null;
    private String username = null;
    private String userAvt = null;
    private ProfileResponse userProfileData = null;
    private ProfileAdapter mProfileAdapter = null;
    private TextView usernameTxt = null;
    private ImageView userImage = null;
    private TextView progressAmount = null;
    private TextView accomplishAmount = null;
    private TextView courseAmount = null;
    private RecyclerView recentCourseRV = null;
    private ImageButton goBackBtn = null;
    private ImageButton cartBtn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        usernameTxt = findViewById(R.id.usernameTxt);
        userImage = findViewById(R.id.userImage);
        progressAmount = findViewById(R.id.progressAmount);
        accomplishAmount = findViewById(R.id.accomplishAmount);
        courseAmount = findViewById(R.id.courseAmount);
        recentCourseRV = findViewById(R.id.recentCourseRV);
        goBackBtn = findViewById(R.id.profileBackButton);
        cartBtn = findViewById(R.id.profileCartBtn);

        getUserPrefs();
        usernameTxt.setText(username);
        Glide.with(this).load(userAvt).into(userImage);

        renderProfileData();

        goBackBtn.setOnClickListener(v -> {
            finish();
        });

        cartBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);
        });
    }

    private void getUserPrefs() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        accessToken = sharedPreferences.getString("access_token", null);
        userId = sharedPreferences.getString("user_id", null);
        username = sharedPreferences.getString("user_full_name", null);
        userAvt = sharedPreferences.getString("profile_image", null);
    }

    private void renderProfileData() {
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
//        Call<ProfileResponse> userCall = userApiService.getUser(userId);
//        userCall.enqueue(new Callback<ProfileResponse>() {
//            @Override
//            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    ProfileResponse profileResponse = response.body();
//                    if (profileResponse.getStatus() == 200) {
//                        userProfileData = profileResponse;
//                        if (userProfileData != null) {
//                            usernameTxt.setText(userProfileData.getData().getFullName());
//                            Glide.with(ProfileActivity.this).load(userProfileData.getData().getProfileImage()).into(userImage);
//                        }
//                    } else {
//                        Toast.makeText(ProfileActivity.this, "Failed to get User data for Profile Screen", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(ProfileActivity.this, "Failed to get data from Profile Screen", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ProfileResponse> call, Throwable throwable) {
//                Toast.makeText(ProfileActivity.this, "Failed get User data for Profile Screen", Toast.LENGTH_SHORT).show();
//            }
//        });

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
                        progressAmount.setText(progressAmountCount + " Giờ");
                        accomplishAmount.setText(accomplishAmountCount / progressResponse.getData().size() * 100 + "%");
                        courseAmount.setText(progressResponse.getData().size() + " Khóa");
                        mProfileAdapter = new ProfileAdapter(ProfileActivity.this, profileCourses);
                        recentCourseRV.setAdapter(mProfileAdapter);
                        recentCourseRV.setLayoutManager(new LinearLayoutManager(ProfileActivity.this, RecyclerView.HORIZONTAL, false));
                    } else {
                        Toast.makeText(ProfileActivity.this, "Failed to get User's course progress", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ProfileActivity.this, "Failed to get progress data from Profile Screen", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EnrolledCourseProgressResponse> call, Throwable throwable) {
                Toast.makeText(ProfileActivity.this, "Failed to get progress data from Profile Screen", Toast.LENGTH_SHORT).show();
            }
        });

    }
}