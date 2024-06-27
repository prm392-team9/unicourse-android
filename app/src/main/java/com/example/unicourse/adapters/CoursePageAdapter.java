package com.example.unicourse.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.unicourse.ui.activities.CourseDetailActivity;
import com.example.unicourse.ui.fragments.courseFragments.FragmentCourseInfo;
import com.example.unicourse.ui.fragments.courseFragments.FragmentCourseOutLine;
import com.example.unicourse.ui.fragments.courseFragments.FragmentCourseReview;

public class CoursePageAdapter extends FragmentStateAdapter {

    public CoursePageAdapter(@NonNull CourseDetailActivity fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FragmentCourseInfo();
            case 1:
                return new FragmentCourseOutLine();
            case 2:
                return new FragmentCourseReview();
            default:
                return new FragmentCourseInfo();
        }
    }

    @Override
    public int getItemCount() {
        return 3; // Number of tabs
    }
}
