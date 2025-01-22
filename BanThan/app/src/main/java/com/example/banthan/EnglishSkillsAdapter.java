package com.example.banthan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class EnglishSkillsAdapter extends RecyclerView.Adapter<EnglishSkillsAdapter.EnglishSkillViewHolder> {
    private List<EnglishSkill> skills;
    private OnItemClickListener listener;

    // Constructor
    public EnglishSkillsAdapter(List<EnglishSkill> skills) {
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
    public EnglishSkillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_english_skill, parent, false); // Ensure `item_english_skill.xml` exists
        return new EnglishSkillViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EnglishSkillViewHolder holder, int position) {
        EnglishSkill skill = skills.get(position);
        holder.titleText.setText(skill.getTitle());
        holder.descriptionText.setText(skill.getDescription());
        holder.lastActivityText.setText(skill.getLastActivity());
        holder.progressBar.setProgress(skill.getProgress());
        holder.progressText.setText(skill.getProgress() + "%");

        // Handle Add button click if needed
        holder.addButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position);
            }
        });

        // Handle item click
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return skills.size();
    }

    public static class EnglishSkillViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView cardView;
        TextView titleText;
        TextView descriptionText;
        TextView lastActivityText;
        ProgressBar progressBar;
        TextView progressText;
        ImageButton addButton;

        public EnglishSkillViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            titleText = itemView.findViewById(R.id.title_text);
            descriptionText = itemView.findViewById(R.id.description_text);
            lastActivityText = itemView.findViewById(R.id.last_activity_text);
            progressBar = itemView.findViewById(R.id.progress_bar);
            progressText = itemView.findViewById(R.id.progress_text);
            addButton = itemView.findViewById(R.id.add_button);
        }
    }
}
