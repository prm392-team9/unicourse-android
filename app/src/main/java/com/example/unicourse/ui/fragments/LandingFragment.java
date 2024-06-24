package com.example.unicourse.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.unicourse.R;
import com.example.unicourse.adapters.CourseAdapter;
import com.example.unicourse.models.Course;
import com.example.unicourse.models.CoursesResponse;
import com.example.unicourse.services.ApiConstants;
import com.example.unicourse.services.CourseApiService;
import com.example.unicourse.ui.activities.ProfileActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LandingFragment extends Fragment {
    private static final String BASE_URL = ApiConstants.BASE_URL;
    private ArrayList<Course> mCourses;
    private ArrayList<Course> mFreeCourses;
    private RecyclerView mRecyclerFreeCourse;
    private CourseAdapter mCourseAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_landing, container, false);

        // Ánh xạ các view trong layout
        CardView profileCardView = view.findViewById(R.id.profileCardView);
        ImageSlider imageSlider = view.findViewById(R.id.imageSlider);
        TextView profileName = view.findViewById(R.id.profileName);
        ImageView avatar = view.findViewById(R.id.avatar);
        Button role = view.findViewById(R.id.role);

        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.activity_landing_carosel1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.activity_landing_carosel2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.activity_landing_carosel3, ScaleTypes.FIT));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        // If you want to set the toolbar in the Fragment, setHasOptionsMenu(true) is needed
        setHasOptionsMenu(true);
        // The hosting Activity should handle setting the toolbar if needed
        // Init data for free course
        mRecyclerFreeCourse = view.findViewById(R.id.recyclerFreeCourseView);
        mFreeCourses = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        CourseApiService courseApiService = retrofit.create(CourseApiService.class);
        Call<CoursesResponse> courseListCall = courseApiService.getCourses();

        courseListCall.enqueue(new Callback<CoursesResponse>() {
            @Override
            public void onResponse(Call<CoursesResponse> call, Response<CoursesResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(requireActivity(), "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                CoursesResponse coursesResponse = response.body();
                if (coursesResponse != null && coursesResponse.getData() != null) {
                    mFreeCourses.addAll(coursesResponse.getData());
                    mCourseAdapter = new CourseAdapter(requireActivity(), mFreeCourses);
                    mRecyclerFreeCourse.setAdapter(mCourseAdapter);
                    mRecyclerFreeCourse.setLayoutManager(new LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false));
                } else {
                    Toast.makeText(requireActivity(), "No courses found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CoursesResponse> call, Throwable throwable) {
                Toast.makeText(requireActivity(), "Request failed: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        profileCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click event, navigate to ProfileActivities.class
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        // Fetch user data from SharedPreferences and display it
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String savedProfileName = sharedPreferences.getString("profile_name", "No Name");
        String savedProfileImage = sharedPreferences.getString("profile_image", null);
        String savedRole = sharedPreferences.getString("role", "unknown");

        profileName.setText(savedProfileName);
        if (savedProfileImage != null) {
            Glide.with(this).load(savedProfileImage).into(avatar);
        }
        switch (savedRole) {
            case "student":
                role.setText("Thành viên");
                break;
            case "lecture":
                role.setText("Giảng viên");
                break;
            case "admin":
                role.setText("Admin");
                break;
            default:
                role.setText("Unknown role");
                break;
        }

        return view;
    }
}