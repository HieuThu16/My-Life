package com.example.imhieu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CategoryDetailFragment extends Fragment {

    private static final String ARG_CATEGORY = "category";
    private Category category;
    private RecyclerView skillsRecyclerView;
    private SkillAdapter skillAdapter;
    private FloatingActionButton addSkillFab;
    private DatabaseHelper databaseHelper;

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

        // Setup the RecyclerView and Add Skill button
        setupRecyclerView();
        setupAddSkillButton();

        return view;
    }

    private void setupRecyclerView() {
        List<Skill> skills = databaseHelper.getSkillsForCategory(category.getId());  // Fetch skills from database
        skillAdapter = new SkillAdapter(skills);
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
                    int skillMaxPoints = 100;  // You can adjust this if needed

                    if (!skillName.isEmpty()) {
                        // Create new skill object
                        Skill newSkill = new Skill(
                                databaseHelper.generateSkillId(),  // Generate skill ID
                                skillName,
                                skillScore,
                                skillMaxPoints,
                                skillDescription,
                                category  // Set the category object (linking skill to category)
                        );

                        // Add skill to the category object
                        category.getSkills().add(newSkill);

                        // Add skill to the database (linking it to the category by categoryId)
                        databaseHelper.addSkillToCategory(category.getId(), newSkill);

                        // Update the RecyclerView UI
                        skillAdapter.notifyItemInserted(category.getSkills().size() - 1);
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }

}
