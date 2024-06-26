package com.example.unicourse.ui.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.unicourse.R;
import com.example.unicourse.ui.fragments.CourseDetailFragment;
import com.example.unicourse.ui.fragments.LandingFragment;
import com.example.unicourse.ui.fragments.ProfileFragment;
import com.example.unicourse.viewmodels.CourseDetailViewModel;
import com.example.unicourse.viewmodels.LandingViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ControllerActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    private LandingViewModel landingViewModel;
    private CourseDetailViewModel courseDetailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        frameLayout = findViewById(R.id.framLayout);

        landingViewModel = new ViewModelProvider(this).get(LandingViewModel.class);
        courseDetailViewModel = new ViewModelProvider(this).get(CourseDetailViewModel.class);
        landingViewModel.loadCourses(); // Load the courses when the activity is created



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                Fragment selectedFragment = null;

                if (itemId == R.id.navHome) {
                    loadFragment(new LandingFragment(), false);
                } else if (itemId == R.id.navCourses) {
                    String courseId = "65d2d3e6b50b001e0f52e999";
                    courseDetailViewModel.loadCourse(courseId);
                    loadFragment(CourseDetailFragment.newInstance(courseId), false);
                } else if (itemId == R.id.navCommunity) {
                    // loadFragment(new CommunityFragment(), false);
                } else {
                    loadFragment(new ProfileFragment(), false);
                }

                return true;
            }
        });

        loadFragment(new LandingFragment(), true);
    }

    private void loadFragment(Fragment fragment, boolean isAppInitialized) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (isAppInitialized) {
            fragmentTransaction.add(R.id.framLayout, fragment);
        } else {
            fragmentTransaction.replace(R.id.framLayout, fragment);
        }
        fragmentTransaction.commit();
    }
}
