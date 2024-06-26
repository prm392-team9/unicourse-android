package com.example.unicourse.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.unicourse.R;
import com.example.unicourse.adapters.CourseAdapter;
import com.example.unicourse.models.course.Course;
import com.example.unicourse.ui.activities.ProfileActivity;
import com.example.unicourse.viewmodels.LandingViewModel;

import java.util.ArrayList;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class LandingFragment extends Fragment {
    private ArrayList<Course> mCourses;
    private ArrayList<Course> mFreeCourses;
    private RecyclerView mRecyclerFreeCourse;
    private RecyclerView mRecyclerFreeCourse1;
    private CourseAdapter mCourseAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LandingViewModel landingViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_landing, container, false);

        // Ánh xạ các view trong layout
        CardView profileCardView = view.findViewById(R.id.profileCardView);
        ImageSlider imageSlider = view.findViewById(R.id.imageSlider);
        TextView profileName = view.findViewById(R.id.profileName);
        ImageView avatar = view.findViewById(R.id.avatar);
        Button role = view.findViewById(R.id.role);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.activity_landing_carosel1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.activity_landing_carosel2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.activity_landing_carosel3, ScaleTypes.FIT));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        setHasOptionsMenu(true);

        mRecyclerFreeCourse = view.findViewById(R.id.recyclerFreeCourseView);
        mRecyclerFreeCourse1 = view.findViewById(R.id.recyclerFreeCourseView1);
        mFreeCourses = new ArrayList<>();
        mCourseAdapter = new CourseAdapter(requireActivity(), mFreeCourses);
        mRecyclerFreeCourse.setAdapter(mCourseAdapter);
        mRecyclerFreeCourse1.setAdapter(mCourseAdapter);
        mRecyclerFreeCourse.setLayoutManager(new LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false));
        mRecyclerFreeCourse1.setLayoutManager(new LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false));

        landingViewModel = new ViewModelProvider(requireActivity()).get(LandingViewModel.class); // Use activity scope
        landingViewModel.getCourses().observe(getViewLifecycleOwner(), new Observer<ArrayList<Course>>() {
            @Override
            public void onChanged(ArrayList<Course> courses) {
                mFreeCourses.clear();
                mFreeCourses.addAll(courses);
                mCourseAdapter.notifyDataSetChanged();
            }
        });

        // Set up swipe to refresh
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                landingViewModel.refreshCourses();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        profileCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
