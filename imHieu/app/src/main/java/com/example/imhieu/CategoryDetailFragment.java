package com.example.imhieu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CategoryDetailFragment extends Fragment {

    private static final String ARG_CATEGORY = "category";
    private Category category;
    private RecyclerView skillsRecyclerView;
    private SkillAdapter skillAdapter;
    private FloatingActionButton addSkillFab;
    private DatabaseHelper databaseHelper;
    private TextView categoryAverageScoreTextView; // TextView for displaying the average score

    // Create a new instance of the fragment with the selected category
    public static CategoryDetailFragment newInstance(Category category) {
        CategoryDetailFragment fragment = new CategoryDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = (Category) getArguments().getSerializable(ARG_CATEGORY);
        }
        databaseHelper = new DatabaseHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_detail, container, false);

        skillsRecyclerView = view.findViewById(R.id.skillsRecyclerView);
        addSkillFab = view.findViewById(R.id.addSkillFab);
        categoryAverageScoreTextView = view.findViewById(R.id.categoryAverageScoreTextView); // Initialize TextView

        // Setup RecyclerView and Add Skill button
        setupRecyclerView();
        setupAddSkillButton();

        // Update the average score when the view is created
        updateCategoryAverageScore();

        return view;
    }

    private void setupRecyclerView() {
        List<Skill> skills = databaseHelper.getSkillsForCategory(category.getId());
        skillAdapter = new SkillAdapter(skills, databaseHelper, updatedSkill -> {
            // Update the interface or data when the skill score changes
            updateCategoryAverageScore();
        });
        skillsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        skillsRecyclerView.setAdapter(skillAdapter);
    }

    private void setupAddSkillButton() {
        addSkillFab.setOnClickListener(v -> showAddSkillDialog());
    }

    private void showAddSkillDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_skill, null);
        builder.setView(dialogView);

        EditText skillNameInput = dialogView.findViewById(R.id.skillNameInput);
        EditText skillScoreInput = dialogView.findViewById(R.id.skillPointsInput);
        EditText skillDescriptionInput = dialogView.findViewById(R.id.skillDescriptionInput);

        builder.setTitle("Add New Skill")
                .setPositiveButton("Save", (dialog, which) -> {
                    String skillName = skillNameInput.getText().toString().trim();
                    String skillDescription = skillDescriptionInput.getText().toString().trim();
                    int skillScore = Integer.parseInt(skillScoreInput.getText().toString().trim());
                    int skillMaxPoints = 100;

                    if (!skillName.isEmpty()) {
                        // Create a new skill
                        Skill newSkill = new Skill(
                                databaseHelper.generateSkillId(),
                                skillName,
                                skillScore,
                                skillMaxPoints,
                                skillDescription,
                                category
                        );

                        // Ensure skillAdapter is initialized
                        if (skillAdapter == null) {
                            List<Skill> skills = databaseHelper.getSkillsForCategory(category.getId());
                            skillAdapter = new SkillAdapter(skills, databaseHelper, updatedSkill -> {
                                updateCategoryAverageScore();
                            });
                            skillsRecyclerView.setAdapter(skillAdapter);
                        }

                        // Add the skill to the adapter and database
                        skillAdapter.getSkills().add(newSkill);
                        databaseHelper.addSkillToCategory(category.getId(), newSkill);

                        // Notify the adapter and update the average score
                        skillAdapter.notifyItemInserted(skillAdapter.getSkills().size() - 1);
                        updateCategoryAverageScore();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }

    private void updateCategoryAverageScore() {
        // Calculate the new average score
        List<Skill> skills = skillAdapter.getSkills();
        double totalScore = 0;

        for (Skill skill : skills) {
            totalScore += skill.getPoints();
        }

        double newAverageScore = skills.isEmpty() ? 0 : totalScore / skills.size();

        // Update the category's average score field
        category.setAverageScore((float) newAverageScore);

        // Update the UI
        categoryAverageScoreTextView.setText(String.format("Average Score: %.2f", category.getAverageScore()));
    }
}
