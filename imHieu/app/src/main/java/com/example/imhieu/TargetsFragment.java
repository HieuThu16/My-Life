package com.example.imhieu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imhieu.TargetAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TargetsFragment extends Fragment {
    private RecyclerView longTermRecyclerView;
    private RecyclerView shortTermRecyclerView;
    private TargetAdapter longTermAdapter;
    private TargetAdapter shortTermAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_targets, container, false);

        longTermRecyclerView = view.findViewById(R.id.longTermTargetsRecyclerView);
        shortTermRecyclerView = view.findViewById(R.id.shortTermTargetsRecyclerView);

        setupRecyclerViews();
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

        longTermRecyclerView.setAdapter(longTermAdapter);
        shortTermRecyclerView.setAdapter(shortTermAdapter);
    }

    private void loadTargets() {
        // Dữ liệu ví dụ cho mục tiêu dài hạn
        List<Target> longTermTargets = Arrays.asList(
                new Target("Full-stack Developer",
                        "Thành thạo front-end và back-end",
                        45, "", true),
                new Target("Machine Learning Basics",
                        "Học các thuật toán cơ bản của machine learning",
                        75, "", true)
        );

        // Dữ liệu ví dụ cho mục tiêu ngắn hạn
        List<Target> shortTermTargets = Arrays.asList(
                new Target("React Project",
                        "Hoàn thành component cơ bản",
                        30, "", false),
                new Target("Bug Fixing",
                        "Sửa các lỗi quan trọng trong dự án",
                        20, "", false)
        );

        // Cập nhật danh sách mục tiêu cho các adapter
        longTermAdapter.submitList(longTermTargets);
        shortTermAdapter.submitList(shortTermTargets);
    }
}