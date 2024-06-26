package com.example.unicourse.ui.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.unicourse.R;
import com.example.unicourse.adapters.CoursePageAdapter;
import com.example.unicourse.models.course.Course;
import com.example.unicourse.viewmodels.CourseDetailViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class CourseDetailFragment extends Fragment {
    private static final String ARG_COURSE_ID = "courseId";
    private String courseId;
    private CourseDetailViewModel courseDetailViewModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;

    public static CourseDetailFragment newInstance(String courseId) {
        CourseDetailFragment fragment = new CourseDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_COURSE_ID, courseId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            courseId = getArguments().getString(ARG_COURSE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_detail, container, false);

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        ViewPager2 viewPager = view.findViewById(R.id.viewPager);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        progressBar = view.findViewById(R.id.progressBar);

        CoursePageAdapter adapter = new CoursePageAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Chi tiết");
                    break;
                case 1:
                    tab.setText("Lộ trình");
                    break;
                case 2:
                    tab.setText("Đánh giá");
                    break;
            }
        }).attach();

        // Initialize views
        TextView courseTitle = view.findViewById(R.id.courseDescription);
        ImageView courseImage = view.findViewById(R.id.mainImageCourse);
        TextView coursePrice = view.findViewById(R.id.coursePrice);
        TextView originalPrice = view.findViewById(R.id.originalPrice);

        courseDetailViewModel = new ViewModelProvider(requireActivity()).get(CourseDetailViewModel.class);
        courseDetailViewModel.getCourse().observe(getViewLifecycleOwner(), new Observer<Course>() {
            @Override
            public void onChanged(Course course) {
                if (course != null) {
                    courseTitle.setText(course.getTitle());
                    Glide.with(CourseDetailFragment.this).load(course.getThumbnail()).into(courseImage);
                    coursePrice.setText(String.format("%,.0f VND", (double) course.getAmount()));
                    originalPrice.setText("34.982 VND"); // Update with actual original price if available
                    progressBar.setVisibility(View.GONE); // Hide the progress bar
                } else {
                    Toast.makeText(getContext(), "Failed to load course details", Toast.LENGTH_SHORT).show();
                }
            }
        });

        courseDetailViewModel.isLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading) {
                    progressBar.setVisibility(View.VISIBLE); // Show the progress bar
                } else {
                    progressBar.setVisibility(View.GONE); // Hide the progress bar
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {
            courseDetailViewModel.loadCourse(courseId); // Reload course details
            swipeRefreshLayout.setRefreshing(false);
        });

        return view;
    }
}
