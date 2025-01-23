package com.example.imhieu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.List;

public class SkillsFragment extends Fragment {
    private RecyclerView categoriesRecyclerView;
    private CategoryAdapter categoryAdapter;
    private FloatingActionButton addCategoryFab;
    private LinearLayout summaryContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate fragment layout
        View view = inflater.inflate(R.layout.fragment_skills, container, false);

        // Initialize views
        categoriesRecyclerView = view.findViewById(R.id.categoriesRecyclerView);
        addCategoryFab = view.findViewById(R.id.addCategoryFab);
        summaryContainer = view.findViewById(R.id.summaryContainer);

        // Set up RecyclerView and other features
        setupRecyclerView();
        setupAddButton();
        updateSummary();

        return view;
    }

    // Set up RecyclerView with data and adapter
    private void setupRecyclerView() {
        categoryAdapter = new CategoryAdapter(getCategories());
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        categoriesRecyclerView.setAdapter(categoryAdapter);
    }

    // Mock data for categories and skills
    private List<Category> getCategories() {
        // Tạo danh sách các kỹ năng với đầy đủ 5 thuộc tính
        List<Skill> programmingSkills = Arrays.asList(
                new Skill(1, "Java", 85, 100, "Lập trình hướng đối tượng mạnh mẽ"),
                new Skill(2, "Kotlin", 90, 100, "Ngôn ngữ hiện đại cho Android"),
                new Skill(3, "Python", 80, 100, "Ngôn ngữ linh hoạt, phổ biến")
        );

        List<Skill> frontendSkills = Arrays.asList(
                new Skill(4, "React", 88, 100, "Thư viện JS mạnh mẽ cho giao diện"),
                new Skill(5, "Angular", 75, 100, "Framework JS mạnh mẽ cho dự án lớn"),
                new Skill(6, "Vue.js", 82, 100, "Framework linh hoạt cho giao diện")
        );

        List<Skill> designSkills = Arrays.asList(
                new Skill(7, "Photoshop", 78, 100, "Công cụ thiết kế hình ảnh chuyên nghiệp"),
                new Skill(8, "Illustrator", 85, 100, "Công cụ vẽ vector mạnh mẽ"),
                new Skill(9, "Figma", 90, 100, "Công cụ thiết kế giao diện hiện đại")
        );

        // Trả về danh sách các Category với danh sách kỹ năng
        return Arrays.asList(
                new Category(1, "Programming", programmingSkills),
                new Category(2, "Frontend Frameworks", frontendSkills),
                new Category(3, "Design Tools", designSkills)
        );
    }

    // Tính điểm trung bình (cập nhật để tính % hoàn thành thay vì điểm tuyệt đối)
    private float calculateAverageScore(List<Skill> skills) {
        if (skills == null || skills.isEmpty()) return 0;
        float totalPercentage = 0;
        for (Skill skill : skills) {
            totalPercentage += skill.getPoints()/skill.getMaxPoints();
        }
        return totalPercentage / skills.size();
    }


    // Set up floating action button to handle adding new categories
    private void setupAddButton() {
        addCategoryFab.setOnClickListener(v -> showAddCategoryDialog());
    }

    // Show a dialog to add a new category (implementation can be extended)
    private void showAddCategoryDialog() {
        // Logic to display dialog to add a new category
        // You can use an AlertDialog or a custom dialog layout
    }

    // Update summary of all categories and skills
    private void updateSummary() {
        // Logic to update summary of skills or overall performance
        // Example: calculate total average score, number of skills, etc.
    }
}
