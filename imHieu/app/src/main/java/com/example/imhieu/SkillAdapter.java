package com.example.imhieu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SkillAdapter extends RecyclerView.Adapter<SkillAdapter.SkillViewHolder> {
    private final List<Skill> skillList;

    public SkillAdapter(List<Skill> skillList) {
        this.skillList = skillList;
    }

    @NonNull
    @Override
    public SkillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_skill, parent, false);
        return new SkillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SkillViewHolder holder, int position) {
        Skill skill = skillList.get(position);
        holder.skillNameTextView.setText(skill.getName());
        holder.skillScoreTextView.setText(String.format("Score: %d/%d", skill.getPoints(), skill.getMaxPoints()));
        holder.skillDescriptionTextView.setText(skill.getDescription());
    }

    @Override
    public int getItemCount() {
        return skillList.size();
    }

    public static class SkillViewHolder extends RecyclerView.ViewHolder {
        TextView skillNameTextView;
        TextView skillScoreTextView;
        TextView skillDescriptionTextView;

        public SkillViewHolder(@NonNull View itemView) {
            super(itemView);
            skillNameTextView = itemView.findViewById(R.id.skillNameTextView);
            skillScoreTextView = itemView.findViewById(R.id.skillScoreTextView);
            skillDescriptionTextView = itemView.findViewById(R.id.skillDescriptionTextView);
        }
    }
}
