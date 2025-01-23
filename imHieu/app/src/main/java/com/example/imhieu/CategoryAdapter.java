package com.example.imhieu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<Category> categories;
    private OnCategoryClickListener onCategoryClickListener;

    public CategoryAdapter(List<Category> categories, OnCategoryClickListener onCategoryClickListener) {
        this.categories = categories;
        this.onCategoryClickListener = onCategoryClickListener;
    }


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override

    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);

        // Hiển thị tên thể loại
        holder.nameTextView.setText(category.getName());

        // Hiển thị điểm trung bình
        double averageScore = category.getAverageScore();
        holder.averageScoreTextView.setText(String.format("Average Score: %.2f", averageScore));

        // Xử lý khi người dùng bấm vào item
        holder.itemView.setOnClickListener(v -> {
            if (onCategoryClickListener != null) {
                onCategoryClickListener.onCategoryClick(category);
            }
        });
    }


    @Override
    public int getItemCount() {
        return categories != null ? categories.size() : 0;
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView averageScoreTextView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.categoryName);
            averageScoreTextView = itemView.findViewById(R.id.categoryScore);
        }
    }
}
