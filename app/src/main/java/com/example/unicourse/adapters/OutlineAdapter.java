package com.example.unicourse.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unicourse.R;
import com.example.unicourse.models.course.SubTrack;
import com.example.unicourse.models.course.TrackCourse;

import java.util.List;

public class OutlineAdapter extends RecyclerView.Adapter<OutlineAdapter.OutlineViewHolder> {
    private List<TrackCourse> Tracks;

    public OutlineAdapter(List<TrackCourse> Tracks) {
        this.Tracks = Tracks;
    }

    @NonNull
    @Override
    public OutlineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_track, parent, false);
        return new OutlineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OutlineViewHolder holder, int position) {
        TrackCourse chapter = Tracks.get(position);
        holder.bind(chapter);
    }

    @Override
    public int getItemCount() {
        return Tracks.size();
    }

    public static class OutlineViewHolder extends RecyclerView.ViewHolder {
        private TextView chapterTitle;
        private TextView steps;

        public OutlineViewHolder(@NonNull View itemView) {
            super(itemView);
            chapterTitle = itemView.findViewById(R.id.chapterTitle);
            steps = itemView.findViewById(R.id.steps);
        }

        public void bind(TrackCourse Track) {
            chapterTitle.setText(Track.getChapterTitle());
            StringBuilder stepsText = new StringBuilder();
            for (SubTrack step : Track.getTrack_steps()) {
                stepsText.append(step.getTitle()).append("\n");
            }
            steps.setText(stepsText.toString().trim());
        }
    }
}
