package com.example.unicourse.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.unicourse.R;
import com.example.unicourse.adapters.CourseAdapter;
import com.example.unicourse.models.Course;
import com.example.unicourse.models.SubTrack;
import com.example.unicourse.models.TrackCourse;
import com.example.unicourse.ui.activities.ProfileActivity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class LandingFragment extends Fragment {

    private ArrayList<Course> mCourses;
    private ArrayList<Course> mFreeCourses;
    private RecyclerView mRecyclerCourse;
    private RecyclerView mRecyclerFreeCourse;
    private CourseAdapter mCourseAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_landing, container, false);

        CardView profileCardView = view.findViewById(R.id.profileCardView);
        ImageSlider imageSlider = view.findViewById(R.id.imageSlider);
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.activity_landing_carosel1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.activity_landing_carosel2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.activity_landing_carosel3, ScaleTypes.FIT));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        // If you want to set the toolbar in the Fragment, setHasOptionsMenu(true) is needed
        setHasOptionsMenu(true);
        // The hosting Activity should handle setting the toolbar if needed

        // Init data for highlight course
        mRecyclerCourse = view.findViewById(R.id.recyclerView);
        mCourses = new ArrayList<>();
        try {
            mCourses = createCourseList();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        mCourseAdapter = new CourseAdapter(requireActivity(), mCourses);
        mRecyclerCourse.setAdapter(mCourseAdapter);
        mRecyclerCourse.setLayoutManager(new LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false));

        // Init data for free course
        mRecyclerFreeCourse = view.findViewById(R.id.recyclerFreeCourseView);
        mFreeCourses = new ArrayList<>();
        try {
            mFreeCourses = createCourseList();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        mCourseAdapter = new CourseAdapter(requireActivity(), mFreeCourses);
        mRecyclerFreeCourse.setAdapter(mCourseAdapter);
        mRecyclerFreeCourse.setLayoutManager(new LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false));

        profileCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click event, navigate to ProfileActivities.class
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    // Khởi tạo fake data courses
    public ArrayList<Course> createCourseList() throws ParseException {
        ArrayList<Course> initCourses = new ArrayList<>();

        // First Course
        List<String> subTitleDescription1 = new ArrayList<>();
        subTitleDescription1.add("Tổng hợp lại kiến thức của PRM392");
        subTitleDescription1.add("Đảm bảo pass 100% khi hoàn thành khóa học");
        subTitleDescription1.add("Nhận biết được các dạng câu hỏi trong bài thi");

        List<TrackCourse> tracksList = new ArrayList<>();
        TrackCourse track1 = new TrackCourse();
        ArrayList<SubTrack> trackStepsList = new ArrayList<>();
        trackStepsList.add(new SubTrack("[PRM392]Bài 1: Tổng quan về Android", "7JyTjObkPuc", 1, 18, "video", "2024-05-27T11:21:42.128Z"));
        trackStepsList.add(new SubTrack("Lập trình Android - Bài 1.0: Hướng dẫn cài đặt và sử dụng Android Studio 2022", "Q1nfS_OC7X8", 2, 5, "video", "2024-05-27T11:22:32.326Z"));
        track1.setChapterTitle("Bài 1: Tổng quan về Android");
        track1.setTrack_steps(trackStepsList);
        tracksList.add(track1);

        Course course1 = new Course();
        course1.set_id("66546ca3d77173375ec3d8d2");
        course1.setTitle("PRM392 - Lập trình ứng dụng di động Android");
        course1.setTitleDescription("Khóa học sưu tầm từ giảng viên của đại học FPT");
        course1.setSubTitle("Bạn sẽ học được gì?");
        course1.setSubTitleDescription(subTitleDescription1);
        course1.setTracks(tracksList);
        course1.setEnrollmentCount(4);
        course1.setStatus("active"); // active || pending || inactive
        course1.setType("free"); // free || fee
        course1.setAmount(0); // Số tiền
        course1.setThumbnail("https://firebasestorage.googleapis.com/v0/b/unicourse-f4020.appspot.com/o/Course%2FCN8%2FPRM392.png?alt=media&token=a2164ded-1dc9-47c6-b952-b499872868b4");
        course1.setLecture("Thầy Uni Cóc");
        course1.setSemester_number(8); // Học kỳ của môn học
        course1.setCreated_at("2024-05-27T11:21:07.768Z");

        initCourses.add(course1);
        initCourses.add(course1);
        initCourses.add(course1);
        initCourses.add(course1);
        return initCourses;
    }
}
