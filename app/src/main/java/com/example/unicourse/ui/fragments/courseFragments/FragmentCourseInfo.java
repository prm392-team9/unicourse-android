package com.example.unicourse.ui.fragments.courseFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.unicourse.R;
import com.example.unicourse.models.course.Course;
import com.example.unicourse.viewmodels.CourseDetailViewModel;

public class FragmentCourseInfo extends Fragment {

    private CourseDetailViewModel courseDetailViewModel;
    private TextView titleDescription;
    private TextView subTitle;
    private TextView subTitleDescription;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_info, container, false);

        // Initialize views
        titleDescription = view.findViewById(R.id.titleDescription);
        subTitle = view.findViewById(R.id.subTitle);
        subTitleDescription = view.findViewById(R.id.subTitleDescription);

        // Get the CourseDetailViewModel
        courseDetailViewModel = new ViewModelProvider(requireActivity()).get(CourseDetailViewModel.class);
        courseDetailViewModel.getCourse().observe(getViewLifecycleOwner(), new Observer<Course>() {
            @Override
            public void onChanged(Course course) {
                if (course != null) {
                    titleDescription.setText(course.getTitleDescription());
                    subTitle.setText(course.getSubTitle());
                    subTitleDescription.setText(formatSubtitleDescriptions(course.getSubTitleDescription().toArray(new String[0])));
                }
            }
        });

        return view;
    }

    private String formatSubtitleDescriptions(String[] subTitleDescriptions) {
        StringBuilder formattedDescription = new StringBuilder();
        for (String desc : subTitleDescriptions) {
            formattedDescription.append("• ").append(desc).append("\n");
        }
        return formattedDescription.toString().trim();
    }
}
