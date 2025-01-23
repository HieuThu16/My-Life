package com.example.imhieu;

import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TargetAdapter extends RecyclerView.Adapter<TargetAdapter.TargetViewHolder> {
    private List<Target> targets;
    private int progressBarColor;

    public TargetAdapter(List<Target> targets, int progressBarColor) {
        this.targets = targets;
        this.progressBarColor = progressBarColor;
    }

    @Override
    public TargetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_target, parent, false);
        return new TargetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TargetViewHolder holder, int position) {
        Target target = targets.get(position);
        holder.titleText.setText(target.getTitle());
        holder.descriptionText.setText(target.getDescription());
        holder.dateText.setText(target.getDeadline());
        holder.percentageText.setText(target.getProgress() + "%");

        holder.progressBar.setProgress(target.getProgress());
        holder.progressBar.getProgressDrawable().setColorFilter(
                progressBarColor, PorterDuff.Mode.SRC_IN);
    }

    @Override
    public int getItemCount() {
        return targets.size();
    }

    static class TargetViewHolder extends RecyclerView.ViewHolder {
        TextView titleText, descriptionText, dateText, percentageText;
        ProgressBar progressBar;

        TargetViewHolder(View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.targetTitle);
            descriptionText = itemView.findViewById(R.id.targetDescription);
            dateText = itemView.findViewById(R.id.targetDate);
            percentageText = itemView.findViewById(R.id.targetPercentage);
            progressBar = itemView.findViewById(R.id.targetProgress);
        }
    }
    public void submitList(List<Target> newTargets) {
        targets.clear();
        targets.addAll(newTargets);
        notifyDataSetChanged();
    }
}
