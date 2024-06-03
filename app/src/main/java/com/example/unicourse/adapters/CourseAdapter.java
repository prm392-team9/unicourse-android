package com.example.unicourse.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unicourse.R;
import com.example.unicourse.models.Course;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CardViewHolder> {
    private ArrayList<Course> courses;

    public CourseAdapter(ArrayList<Course> courses) {
        this.courses = courses;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_landing_card_item, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        holder.bind(courses.get(position));
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView authorName;
        private TextView courseTitle;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
//            imageView = itemView.findViewById(R.id.imageView);
//            authorName = itemView.findViewById(R.id.authorName);
//            courseTitle = itemView.findViewById(R.id.courseTitle);
            this.imageView = imageView;
        }

        public void bind(Course course) {
            // Assuming Course class has getImageResource(), getAuthorName(), and getTitle() methods
//            imageView.setImageResource(course.getImageResource());
//            authorName.setText(course.getAuthorName());
//            courseTitle.setText(course.getTitle());
        }
    }
}


