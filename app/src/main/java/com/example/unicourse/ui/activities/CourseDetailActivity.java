package com.example.unicourse.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.unicourse.R;
import com.example.unicourse.adapters.CoursePageAdapter;
import com.example.unicourse.models.course.Course;
import com.example.unicourse.viewmodels.CourseDetailViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;

public class CourseDetailActivity extends AppCompatActivity {
    private static final String ARG_COURSE_ID = "COURSE_ID";
    private static final String EXTRA_COURSE = "EXTRA_COURSE";
    private String courseId;
    private CourseDetailViewModel courseDetailViewModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private LottieAnimationView loadingAnimation;
    private CardView addToCartCard;
    private Button enrollButton;

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
        loadingAnimation = findViewById(R.id.loadingAnimation);
        ImageView backButton = findViewById(R.id.profileBackButton);
        addToCartCard = findViewById(R.id.addToCartCard);
        enrollButton = findViewById(R.id.enrollButton);

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
                    originalPrice.setText("0 VND"); // Update with actual original price if available

                    // Set lecture details
                    lectureName.setText(course.getLecture().getFullName());
                    lectureCoursesCount.setText(course.getLecture().getLecture_info().getMy_course().size() + " khóa học");
                    Glide.with(CourseDetailActivity.this).load(course.getLecture().getProfile_image()).into(lectureAvatar);

                    // Hide add to cart if the course is free
                    if ("free".equalsIgnoreCase(course.getType())) {
                        addToCartCard.setVisibility(View.GONE);
                    } else {
                        addToCartCard.setVisibility(View.VISIBLE);
                    }

                    hideLoading(); // Hide loading animation

                    // When the user clicks "Học ngay"
                    enrollButton.setOnClickListener(v -> {
                        Intent intent = new Intent(CourseDetailActivity.this, CourseVideoActivity.class);
                        intent.putExtra("COURSE_ID", courseId);
                        startActivity(intent);
                    });

                } else {
                    Toast.makeText(CourseDetailActivity.this, "Failed to load course details", Toast.LENGTH_SHORT).show();
                }
            }
        });

        courseDetailViewModel.isLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading) {
                    showLoading(); // Show loading animation
                } else {
                    hideLoading(); // Hide loading animation
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

    private void showLoading() {
        loadingAnimation.setVisibility(View.VISIBLE);
        loadingAnimation.playAnimation();

    }

    private void hideLoading() {
        loadingAnimation.setVisibility(View.GONE);
        loadingAnimation.pauseAnimation();
    }
}
