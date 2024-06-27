package com.example.unicourse.ui.fragments.courseFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unicourse.R;
import com.example.unicourse.adapters.OutlineAdapter;
import com.example.unicourse.models.course.Course;
import com.example.unicourse.models.course.TrackCourse;
import com.example.unicourse.viewmodels.CourseDetailViewModel;

import java.util.ArrayList;
import java.util.List;

public class FragmentCourseOutLine extends Fragment {
    private RecyclerView outlineRecyclerView;
    private OutlineAdapter outlineAdapter;
    private List<TrackCourse> chapters = new ArrayList<>();
    private CourseDetailViewModel courseDetailViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_outline, container, false);

        outlineRecyclerView = view.findViewById(R.id.outlineRecyclerView);
        outlineRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        outlineAdapter = new OutlineAdapter(chapters);
        outlineRecyclerView.setAdapter(outlineAdapter);

        courseDetailViewModel = new ViewModelProvider(requireActivity()).get(CourseDetailViewModel.class);
        courseDetailViewModel.getCourse().observe(getViewLifecycleOwner(), new Observer<Course>() {
            @Override
            public void onChanged(Course course) {
                if (course != null) {
                    chapters.clear();
                    chapters.addAll(course.getTracks());
                    outlineAdapter.notifyDataSetChanged();
                }
            }
        });

        return view;
    }
}
