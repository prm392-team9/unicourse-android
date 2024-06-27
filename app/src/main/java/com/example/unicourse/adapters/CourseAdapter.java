package com.example.unicourse.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.unicourse.R;
import com.example.unicourse.models.course.Course;
import com.example.unicourse.ui.activities.CourseDetailActivity;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CardViewHolder> {
    private Context mContext;
    private ArrayList<Course> courses;

    public CourseAdapter(Context mContext, ArrayList<Course> courses) {
        this.mContext = mContext;
        this.courses = courses;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.activity_landing_card_item, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Course course = courses.get(position);
        holder.bind(course);
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView authorName;
        private TextView courseTitle;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            authorName = itemView.findViewById(R.id.authorName);
            courseTitle = itemView.findViewById(R.id.courseTitle);
        }

        public void bind(Course course) {
            Glide.with(imageView.getContext())
                    .load(course.getThumbnail())
                    .into(imageView);
            authorName.setText(course.getLecture().getFullName()); // Assuming getLecture() returns the author's name
            courseTitle.setText(course.getTitle());

            // Set OnClickListener to navigate to CourseDetailActivity
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, CourseDetailActivity.class);
                    intent.putExtra("COURSE_ID", course.get_id());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
