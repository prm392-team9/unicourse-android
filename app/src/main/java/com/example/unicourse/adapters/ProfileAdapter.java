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
import com.example.unicourse.models.user.ProfileCourse;
import com.example.unicourse.ui.activities.CourseDetailActivity;

import java.util.ArrayList;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.CardViewHolder> {

    private final Context pContext;
    private final ArrayList<ProfileCourse> profileCourses;

    public ProfileAdapter(Context pContext, ArrayList<ProfileCourse> profileCourses) {
        this.pContext = pContext;
        this.profileCourses = profileCourses;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(pContext);
        View view = inflater.inflate(R.layout.activity_profile_recent_card_item, parent, false);
        return new CardViewHolder(view, pContext);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        ProfileCourse profileCourse = profileCourses.get(position);
        holder.bind(profileCourse);
    }

    @Override
    public int getItemCount() {
        return profileCourses.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView courseTitle;
        private Context parentContext;

        public CardViewHolder(@NonNull View itemView, Context pContext) {
            super(itemView);
            imageView = itemView.findViewById(R.id.recentCourseImage);
            courseTitle = itemView.findViewById(R.id.recentCourseTitle);
            parentContext = pContext;
        }

        public void bind(ProfileCourse profileCourse) {
            Glide.with(imageView.getContext())
                    .load(profileCourse.getImage())
                    .into(imageView);
            courseTitle.setText(profileCourse.getTitle());
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(parentContext, CourseDetailActivity.class);
                intent.putExtra("COURSE_ID", profileCourse.getId());
                parentContext.startActivity(intent);
            });
        }
    }
}
