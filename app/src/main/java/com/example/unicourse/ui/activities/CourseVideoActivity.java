package com.example.unicourse.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.unicourse.R;
import com.example.unicourse.adapters.TrackAdapter;
import com.example.unicourse.models.course.Course;
import com.example.unicourse.models.course.SubTrack;
import com.example.unicourse.models.course.TrackCourse;
import com.example.unicourse.viewmodels.CourseVideoViewModel;
import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;

public class CourseVideoActivity extends AppCompatActivity {

    private static final String COURSE_ID = "COURSE_ID";
    private WebView videoWebView;
    private FrameLayout videoFullScreenContainer;
    private WebChromeClient.CustomViewCallback customViewCallback;
    private View customView;
    private RecyclerView trackRecyclerView;
    private TrackAdapter trackAdapter;
    private CourseVideoViewModel courseVideoViewModel;
    private TextView courseTitle;
    private TextView lecturerName; // Fix here
    private ImageView lectureAvatar;
    private LottieAnimationView loadingAnimation;
    private View loadingOverlay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_video);

        // Initialize views
        ImageButton backButton = findViewById(R.id.backButton);
        videoWebView = findViewById(R.id.videoWebView);
        videoFullScreenContainer = findViewById(R.id.videoFullScreenContainer);
        trackRecyclerView = findViewById(R.id.trackRecyclerView);
        courseTitle = findViewById(R.id.courseTitle);
        lecturerName = findViewById(R.id.lecturerName); // Fix here
        lectureAvatar = findViewById(R.id.lectureAvatar);
        loadingAnimation = findViewById(R.id.loadingAnimation);
        loadingOverlay = findViewById(R.id.loadingOverlay);

        // Get course ID from intent
        String courseId = getIntent().getStringExtra(COURSE_ID);

        // Initialize ViewModel
        courseVideoViewModel = new ViewModelProvider(this).get(CourseVideoViewModel.class);
        courseVideoViewModel.getCourse().observe(this, new Observer<Course>() {
            @Override
            public void onChanged(Course course) {
                if (course != null) {
                    displayCourseData(course);
                } else {
                    Toast.makeText(CourseVideoActivity.this, "Failed to load course data", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        courseVideoViewModel.isLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading) {
                    showLoading();
                } else {
                    hideLoading();
                }
            }
        });

        // Load course data
        courseVideoViewModel.loadCourse(courseId);

        // Set back button functionality
        backButton.setOnClickListener(v -> finish());
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupWebView(String videoId) {
        String videoUrl = "https://www.youtube.com/embed/" + videoId + "?autoplay=1&fs=1";
        Log.d("CourseVideoActivity", "Loading video URL: " + videoUrl); // Log URL
        WebSettings webSettings = videoWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);

        videoWebView.setWebViewClient(new WebViewClient());
        videoWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                if (customView != null) {
                    callback.onCustomViewHidden();
                    return;
                }

                // Show full-screen view
                customView = view;
                videoFullScreenContainer.addView(customView);
                videoFullScreenContainer.setVisibility(View.VISIBLE);
                videoWebView.setVisibility(View.GONE);
                customViewCallback = callback;
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            }

            @Override
            public void onHideCustomView() {
                if (customView == null) {
                    return;
                }

                // Hide full-screen view
                videoFullScreenContainer.removeView(customView);
                videoFullScreenContainer.setVisibility(View.GONE);
                videoWebView.setVisibility(View.VISIBLE);
                customView = null;
                customViewCallback.onCustomViewHidden();
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        });
        videoWebView.loadUrl(videoUrl);
    }

    private void onTrackSelected(String videoId) {
        setupWebView(videoId);
    }

    private void displayCourseData(Course course) {
        // Set course title
        courseTitle.setText(course.getTitle());
        lecturerName.setText(course.getLecture().getFullName());
        Glide.with(this).load(course.getLecture().getProfile_image()).into(lectureAvatar);

        // Get all track steps from the course
        List<SubTrack> allTrackSteps = new ArrayList<>();
        for (TrackCourse trackCourse : course.getTracks()) {
            allTrackSteps.addAll(trackCourse.getTrack_steps());
        }

        // Setup RecyclerView for track steps
        trackRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        trackAdapter = new TrackAdapter(allTrackSteps, this::onTrackSelected);
        trackRecyclerView.setAdapter(trackAdapter);

        // Setup WebView with the first video
        if (!allTrackSteps.isEmpty()) {
            setupWebView(allTrackSteps.get(0).getContent_url());
        }
    }

    private void showLoading() {
        loadingOverlay.setVisibility(View.VISIBLE);
        loadingAnimation.setVisibility(View.VISIBLE);
        loadingAnimation.playAnimation();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void hideLoading() {
        loadingOverlay.setVisibility(View.GONE);
        loadingAnimation.setVisibility(View.GONE);
        loadingAnimation.cancelAnimation();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}
