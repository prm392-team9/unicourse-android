package com.example.unicourse.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.unicourse.R;
import com.example.unicourse.adapters.CoursePageAdapter;
import com.example.unicourse.models.course.Course;
import com.example.unicourse.viewmodels.CourseDetailViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class CourseDetailActivity extends AppCompatActivity {
    private static final String ARG_COURSE_ID = "COURSE_ID";
    private String courseId;
    private CourseDetailViewModel courseDetailViewModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        if (getIntent() != null) {
            courseId = getIntent().getStringExtra(ARG_COURSE_ID);
        }

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        progressBar = findViewById(R.id.progressBar);
        ImageView backButton = findViewById(R.id.backButton);

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
        TextView courseTitle = findViewById(R.id.courseDescription);
        ImageView courseImage = findViewById(R.id.mainImageCourse);
        TextView coursePrice = findViewById(R.id.coursePrice);
        TextView originalPrice = findViewById(R.id.originalPrice);
        ImageView lectureAvatar = findViewById(R.id.lectureAvatar);
        TextView lectureName = findViewById(R.id.lectureName);
        TextView lectureCoursesCount = findViewById(R.id.lectureCoursesCount);

        courseDetailViewModel = new ViewModelProvider(this).get(CourseDetailViewModel.class);
        courseDetailViewModel.getCourse().observe(this, new Observer<Course>() {
            @Override
            public void onChanged(Course course) {
                if (course != null) {
                    courseTitle.setText(course.getTitle());
                    Glide.with(CourseDetailActivity.this).load(course.getThumbnail()).into(courseImage);
                    coursePrice.setText(String.format("%,.0f VND", (double) course.getAmount()));
                    originalPrice.setText("34.982 VND"); // Update with actual original price if available

                    // Set lecture details
                    lectureName.setText(course.getLecture().getFullName());
                    lectureCoursesCount.setText(course.getLecture().getLecture_info().getMy_course().size() + " khóa học");
                    Glide.with(CourseDetailActivity.this).load(course.getLecture().getProfile_image()).into(lectureAvatar);

                    progressBar.setVisibility(View.GONE); // Hide the progress bar
                } else {
                    Toast.makeText(CourseDetailActivity.this, "Failed to load course details", Toast.LENGTH_SHORT).show();
                }
            }
        });

        courseDetailViewModel.isLoading().observe(this, new Observer<Boolean>() {
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

        courseDetailViewModel.loadCourse(courseId); // Load course details on activity start

        // Set the back button functionality
        backButton.setOnClickListener(v -> finish());

        // Disable SwipeRefreshLayout while interacting with ViewPager2
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                swipeRefreshLayout.setEnabled(state == ViewPager2.SCROLL_STATE_IDLE);
            }
        });
    }
}
