package com.example.imhieu;

import java.util.ArrayList;
import java.util.List;

public class Goal {
    private int id; // ID của mục tiêu
    private String title; // Tiêu đề của mục tiêu
    private String description; // Mô tả chi tiết
    private int progress; // Tiến độ (0-100)
    private boolean isLongTerm; // Có phải mục tiêu dài hạn không
    private List<SubGoal> subGoals; // Danh sách các sub-goals

    // Constructor
    public Goal(int id, String title, String description, boolean isLongTerm) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isLongTerm = isLongTerm;
        this.progress = 0; // Mặc định tiến độ là 0%
        this.subGoals = new ArrayList<>();
    }

    // Getter và Setter cho id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter và Setter cho title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter và Setter cho description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter và Setter cho progress
    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        if (progress < 0 || progress > 100) {
            throw new IllegalArgumentException("Progress must be between 0 and 100.");
        }
        this.progress = progress;
    }

    // Getter và Setter cho isLongTerm
    public boolean isLongTerm() {
        return isLongTerm;
    }

    public void setLongTerm(boolean longTerm) {
        isLongTerm = longTerm;
    }

    // Getter cho danh sách subGoals
    public List<SubGoal> getSubGoals() {
        return subGoals;
    }

    // Thêm một sub-goal
    public void addSubGoal(SubGoal subGoal) {
        subGoals.add(subGoal);
    }

    // Xóa một sub-goal theo id
    public void removeSubGoal(int subGoalId) {
        subGoals.removeIf(subGoal -> subGoal.getId() == subGoalId);
    }

    // Đánh dấu sub-goal hoàn thành
    public void markSubGoalAsCompleted(int subGoalId) {
        for (SubGoal subGoal : subGoals) {
            if (subGoal.getId() == subGoalId) {
                subGoal.markAsCompleted();
                break;
            }
        }
        updateProgress();
    }

    // Tính toán và cập nhật tiến độ dựa trên sub-goals
    public void updateProgress() {
        if (subGoals.isEmpty()) {
            this.progress = 0;
            return;
        }
        int completedSubGoals = 0;
        for (SubGoal subGoal : subGoals) {
            if (subGoal.isCompleted()) {
                completedSubGoals++;
            }
        }
        this.progress = (completedSubGoals * 100) / subGoals.size();
    }

    // Hiển thị thông tin của mục tiêu
    @Override
    public String toString() {
        return "Goal{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", progress=" + progress +
                "%, isLongTerm=" + isLongTerm +
                ", subGoals=" + subGoals +
                '}';
    }
}
