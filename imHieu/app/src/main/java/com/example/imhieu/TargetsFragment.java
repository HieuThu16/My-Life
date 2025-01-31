package com.example.imhieu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import android.app.AlertDialog;
import android.widget.EditText;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

public class TargetsFragment extends Fragment {
    private RecyclerView longTermRecyclerView;
    private RecyclerView shortTermRecyclerView;
    private TargetAdapter longTermAdapter;
    private TargetAdapter shortTermAdapter;
    private DatabaseHelper dbHelper;
    private FloatingActionButton fabAddTarget;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_targets, container, false);

        // Initialize database helper
        dbHelper = new DatabaseHelper(requireContext());

        // Initialize views
        longTermRecyclerView = view.findViewById(R.id.longTermTargetsRecyclerView);
        shortTermRecyclerView = view.findViewById(R.id.shortTermTargetsRecyclerView);
        fabAddTarget = view.findViewById(R.id.fabAddTarget);

        setupRecyclerViews();
        setupFab();
        loadTargets();

        return view;
    }

    private void setupRecyclerViews() {
        longTermRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        shortTermRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        int redColor = ContextCompat.getColor(requireContext(), android.R.color.holo_red_light);
        int greenColor = ContextCompat.getColor(requireContext(), android.R.color.holo_green_light);

        longTermAdapter = new TargetAdapter(new ArrayList<>(), redColor);
        shortTermAdapter = new TargetAdapter(new ArrayList<>(), greenColor);

        // Set click listeners for the adapters
        longTermAdapter.setOnItemClickListener(goal -> showSubGoalsDialog(goal));
        shortTermAdapter.setOnItemClickListener(goal -> showSubGoalsDialog(goal));

        longTermRecyclerView.setAdapter(longTermAdapter);
        shortTermRecyclerView.setAdapter(shortTermAdapter);
    }

    private void setupFab() {
        fabAddTarget.setOnClickListener(v -> showAddTargetDialog());
    }

    private void loadTargets() {
        List<Goal> allGoals = dbHelper.getAllGoals();
        List<Goal> longTermGoals = new ArrayList<>();
        List<Goal> shortTermGoals = new ArrayList<>();

        // Separate goals into long-term and short-term
        for (Goal goal : allGoals) {
            if (goal.isLongTerm()) {
                longTermGoals.add(goal);
            } else {
                shortTermGoals.add(goal);
            }
        }

        // Convert Goals to Targets for the adapters
        List<Target> longTermTargets = convertGoalsToTargets(longTermGoals);
        List<Target> shortTermTargets = convertGoalsToTargets(shortTermGoals);

        // Update the adapters
        longTermAdapter.submitList(longTermTargets);
        shortTermAdapter.submitList(shortTermTargets);
    }

    private List<Target> convertGoalsToTargets(List<Goal> goals) {
        List<Target> targets = new ArrayList<>();
        for (Goal goal : goals) {
            Target target = new Target(
                    goal.getTitle(),
                    goal.getDescription(),
                    goal.getProgress(),
                    "", // Additional info if needed
                    goal.isLongTerm()
            );
            target.setId(goal.getId()); // Assuming you add an ID field to Target class
            targets.add(target);
        }
        return targets;
    }

    private void showAddTargetDialog() {
        View dialogView = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_add_target, null);

        EditText etTitle = dialogView.findViewById(R.id.etTitle);
        EditText etDescription = dialogView.findViewById(R.id.etDescription);
        Switch switchLongTerm = dialogView.findViewById(R.id.switchLongTerm);

        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Add New Target")
                .setView(dialogView)
                .setPositiveButton("Add", (dialog, which) -> {
                    String title = etTitle.getText().toString();
                    String description = etDescription.getText().toString();
                    boolean isLongTerm = switchLongTerm.isChecked();

                    // Create and save the goal
                    Goal newGoal = new Goal(0, title, description, isLongTerm);
                    long goalId = dbHelper.addGoal(newGoal);
                    newGoal.setId((int) goalId);

                    // Refresh the lists
                    loadTargets();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showSubGoalsDialog(Target target) {
        View dialogView = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_subgoals, null);
        RecyclerView subGoalsRecyclerView = dialogView.findViewById(R.id.subGoalsRecyclerView);
        EditText etNewSubGoal = dialogView.findViewById(R.id.etNewSubGoal);
        Button btnAddSubGoal = dialogView.findViewById(R.id.btnAddSubGoal);

        // Setup SubGoals RecyclerView and load existing subgoals
        SubGoalsAdapter subGoalsAdapter = new SubGoalsAdapter();
        subGoalsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        subGoalsRecyclerView.setAdapter(subGoalsAdapter);

        List<SubGoal> subGoals = dbHelper.getSubGoalsForGoal(target.getId());
        subGoalsAdapter.submitList(subGoals);

        // Setup add subgoal button
        btnAddSubGoal.setOnClickListener(v -> {
            String subGoalTitle = etNewSubGoal.getText().toString();
            if (!subGoalTitle.isEmpty()) {
                SubGoal newSubGoal = new SubGoal(0, target.getId(), subGoalTitle);
                dbHelper.addSubGoal(newSubGoal);
                etNewSubGoal.setText("");

                // Refresh subgoals list
                List<SubGoal> updatedSubGoals = dbHelper.getSubGoalsForGoal(target.getId());
                subGoalsAdapter.submitList(updatedSubGoals);

                // Update main target progress
                updateTargetProgress(target);
            }
        });

        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(target.getTitle())
                .setView(dialogView)
                .setPositiveButton("Close", null)
                .show();
    }

    private void updateTargetProgress(Target target) {
        List<SubGoal> subGoals = dbHelper.getSubGoalsForGoal(target.getId());
        if (!subGoals.isEmpty()) {
            int completedCount = 0;
            for (SubGoal subGoal : subGoals) {
                if (subGoal.isCompleted()) {
                    completedCount++;
                }
            }
            int progress = (completedCount * 100) / subGoals.size();
            dbHelper.updateGoalProgress(target.getId(), progress);
            loadTargets(); // Refresh the main lists
        }
    }
}