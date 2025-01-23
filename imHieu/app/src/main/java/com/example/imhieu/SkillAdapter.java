package com.example.imhieu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SkillAdapter extends RecyclerView.Adapter<SkillAdapter.SkillViewHolder> {

    private final List<Skill> skillList;
    private final DatabaseHelper databaseHelper;
    private final OnSkillScoreChangeListener onSkillScoreChangeListener;

    // Constructor to initialize the skill list, DatabaseHelper, and listener
    public SkillAdapter(List<Skill> skillList, DatabaseHelper databaseHelper, OnSkillScoreChangeListener onSkillScoreChangeListener) {
        this.skillList = skillList;
        this.databaseHelper = databaseHelper;
        this.onSkillScoreChangeListener = onSkillScoreChangeListener;
    }

    public List<Skill> getSkills() {
        return skillList;
    }

    @NonNull
    @Override
    public SkillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for individual skill items
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_skill, parent, false);
        return new SkillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SkillViewHolder holder, int position) {
        // Get the current skill object
        Skill skill = skillList.get(position);

        // Bind skill data to the view holder
        holder.skillNameTextView.setText(skill.getName());
        holder.skillDescriptionTextView.setText(skill.getDescription());
        holder.skillScoreProgressBar.setMax(skill.getMaxPoints());
        holder.skillScoreProgressBar.setProgress(skill.getPoints());

        // Increase score button functionality
        holder.increaseScoreButton.setOnClickListener(v -> {
            int currentPoints = skill.getPoints();
            if (currentPoints < skill.getMaxPoints()) {
                skill.setPoints(currentPoints + 1); // Increment skill points
                holder.skillScoreProgressBar.setProgress(skill.getPoints()); // Update progress bar

                // Cập nhật database
                databaseHelper.updateSkillPoints(skill.getId(), skill.getPoints());

                // Gọi callback để thông báo thay đổi
                if (onSkillScoreChangeListener != null) {
                    onSkillScoreChangeListener.onSkillScoreChanged(skill);
                }

                notifyItemChanged(position); // Notify adapter of item change
            }
        });

        // Decrease score button functionality
        holder.decreaseScoreButton.setOnClickListener(v -> {
            int currentPoints = skill.getPoints();
            if (currentPoints > 0) {
                skill.setPoints(currentPoints - 1); // Decrement skill points
                holder.skillScoreProgressBar.setProgress(skill.getPoints()); // Update progress bar

                // Cập nhật database
                databaseHelper.updateSkillPoints(skill.getId(), skill.getPoints());

                // Gọi callback để thông báo thay đổi
                if (onSkillScoreChangeListener != null) {
                    onSkillScoreChangeListener.onSkillScoreChanged(skill);
                }

                notifyItemChanged(position); // Notify adapter of item change
            }
        });
    }

    @Override
    public int getItemCount() {
        // Return the total number of skills
        return skillList.size();
    }

    // ViewHolder class for the RecyclerView
    public static class SkillViewHolder extends RecyclerView.ViewHolder {

        TextView skillNameTextView;
        TextView skillDescriptionTextView;
        ProgressBar skillScoreProgressBar;
        ImageButton decreaseScoreButton;
        ImageButton increaseScoreButton;

        public SkillViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize views from the layout
            skillNameTextView = itemView.findViewById(R.id.skillNameTextView);
            skillScoreProgressBar = itemView.findViewById(R.id.skillScoreProgressBar);
            skillDescriptionTextView = itemView.findViewById(R.id.skillDescriptionTextView);
            decreaseScoreButton = itemView.findViewById(R.id.decreaseScoreButton);
            increaseScoreButton = itemView.findViewById(R.id.increaseScoreButton);
        }
    }
}
