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

import java.util.ArrayList;
import java.util.List;

public class SkillsFragment extends Fragment {

    private RecyclerView categoriesRecyclerView; // RecyclerView for displaying categories
    private CategoryAdapter categoryAdapter; // Adapter for categories
    private FloatingActionButton addCategoryFab; // FAB for adding a category
    private List<Category> categoryList; // List to store categories
    private DatabaseHelper databaseHelper; // Helper to handle database operations

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the fragment layout
        View view = inflater.inflate(R.layout.fragment_skills, container, false);

        // Initialize the database helper
        databaseHelper = new DatabaseHelper(getContext());

        // Initialize UI elements
        categoriesRecyclerView = view.findViewById(R.id.categoriesRecyclerView);
        addCategoryFab = view.findViewById(R.id.addCategoryFab);

        // Load categories from the database
        categoryList = databaseHelper.getAllCategories();

        // Set up RecyclerView and FAB button
        setupRecyclerView();
        setupAddButton();

        return view;
    }

    /**
     * Set up the RecyclerView with its adapter and layout manager
     */
    private void setupRecyclerView() {
        categoryAdapter = new CategoryAdapter(categoryList, this::onCategoryClicked);
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        categoriesRecyclerView.setAdapter(categoryAdapter);
    }

    /**
     * Handle what happens when a category is clicked.
     * Navigate to CategoryDetailFragment with the selected category.
     */
    private void onCategoryClicked(Category category) {
        CategoryDetailFragment fragment = CategoryDetailFragment.newInstance(category);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment) // Replace with the fragment container ID
                .addToBackStack(null) // Add this transaction to the back stack
                .commit();
    }

    /**
     * Set up the Floating Action Button to show a dialog for adding a new category.
     */
    private void setupAddButton() {
        addCategoryFab.setOnClickListener(v -> showAddCategoryDialog());
    }

    /**
     * Show a dialog to add a new category. Save it to the database and update the UI.
     */
    private void showAddCategoryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_category, null);
        builder.setView(dialogView);

        EditText categoryNameInput = dialogView.findViewById(R.id.categoryNameInput);

        builder.setTitle("Add New Category")
                .setPositiveButton("Save", (dialog, which) -> {
                    String categoryName = categoryNameInput.getText().toString().trim();
                    if (!categoryName.isEmpty()) {
                        // Create a new category object
                        Category newCategory = new Category(
                                databaseHelper.generateCategoryId(), // Generate unique ID from the database
                                categoryName,
                                new ArrayList<>() // Initialize with an empty skill list
                        );

                        // Save the new category to the database
                        databaseHelper.addCategory(newCategory);

                        // Update the RecyclerView with the new category
                        categoryList.add(newCategory);
                        categoryAdapter.notifyItemInserted(categoryList.size() - 1);
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }
}
