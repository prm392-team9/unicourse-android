package com.example.unicourse.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unicourse.R;
import com.example.unicourse.models.course.SubTrack;

import java.util.List;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.TrackViewHolder> {

    private List<SubTrack> trackStepList;
    private TrackClickListener listener;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public interface TrackClickListener {
        void onTrackClick(String videoId);
    }

    public TrackAdapter(List<SubTrack> trackStepList, TrackClickListener listener) {
        this.trackStepList = trackStepList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_track_video, parent, false);
        return new TrackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackViewHolder holder, int position) {
        SubTrack trackStep = trackStepList.get(position);
        holder.trackTitle.setText(trackStep.getTitle());
        holder.videoDuration.setText("Độ dài video - Khoảng " + trackStep.getDuration() + " phút");

        // Highlight selected item
        holder.cardView.setCardBackgroundColor(selectedPosition == position ? holder.itemView.getContext().getResources().getColor(R.color.primary200) : holder.itemView.getContext().getResources().getColor(R.color.white));

        holder.itemView.setOnClickListener(v -> {
            listener.onTrackClick(trackStep.getContent_url());
            notifyItemChanged(selectedPosition);
            selectedPosition = position;
            notifyItemChanged(selectedPosition);
        });

        holder.playButton.setOnClickListener(v -> {
            listener.onTrackClick(trackStep.getContent_url());
            notifyItemChanged(selectedPosition);
            selectedPosition = position;
            notifyItemChanged(selectedPosition);
        });
    }

    @Override
    public int getItemCount() {
        return trackStepList.size();
    }

    public static class TrackViewHolder extends RecyclerView.ViewHolder {

        TextView trackTitle;
        TextView videoDuration;
        ImageView playButton;
        CardView cardView;

        public TrackViewHolder(@NonNull View itemView) {
            super(itemView);
            trackTitle = itemView.findViewById(R.id.trackTitle);
            videoDuration = itemView.findViewById(R.id.videoDuration);
            playButton = itemView.findViewById(R.id.playButton);
            cardView = (CardView) itemView;
        }
    }
}
