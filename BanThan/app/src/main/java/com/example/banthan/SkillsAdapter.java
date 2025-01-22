package com.example.banthan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class SkillsAdapter extends RecyclerView.Adapter<SkillsAdapter.SkillViewHolder> {
    private List<Skill> skills;
    private OnItemClickListener listener;

    // Constructor
    public SkillsAdapter(List<Skill> skills) {
        this.skills = skills;
    }

    // Interface for item click listener
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    // Setter for listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public SkillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_english_skill, parent, false); // Ensure `item_skill.xml` layout exists
        return new SkillViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull SkillViewHolder holder, int position) {
        Skill skill = skills.get(position);
        holder.titleText.setText(skill.getTitle());
        holder.descriptionText.setText(skill.getDescription());
        holder.progressBar.setProgress(skill.getProgress());
        holder.progressText.setText(skill.getProgress() + "%");
    }

    @Override
    public int getItemCount() {
        return skills.size();
    }

    public static class SkillViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView cardView;
        TextView titleText;
        TextView descriptionText;
        ProgressBar progressBar;
        TextView progressText;

        public SkillViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            titleText = itemView.findViewById(R.id.title_text);
            descriptionText = itemView.findViewById(R.id.description_text);
            progressBar = itemView.findViewById(R.id.progress_bar);
            progressText = itemView.findViewById(R.id.progress_text);

            // Set item click listener
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }
}
