package com.example.imhieu;

public class SubGoal {
    private int id; // ID của sub-goal
    private int parentGoalId; // ID của goal cha
    private String title; // Tiêu đề của sub-goal
    private boolean isCompleted; // Trạng thái hoàn thành

    // Constructor
    public SubGoal(int id, int parentGoalId, String title) {
        this.id = id;
        this.parentGoalId = parentGoalId;
        this.title = title;
        this.isCompleted = false; // Mặc định chưa hoàn thành
    }

    // Getter và Setter cho id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter và Setter cho parentGoalId
    public int getParentGoalId() {
        return parentGoalId;
    }

    public void setParentGoalId(int parentGoalId) {
        this.parentGoalId = parentGoalId;
    }

    // Getter và Setter cho title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter và Setter cho isCompleted
    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    // Phương thức đánh dấu sub-goal hoàn thành
    public void markAsCompleted() {
        this.isCompleted = true;
    }

    // Phương thức hủy trạng thái hoàn thành
    public void markAsIncomplete() {
        this.isCompleted = false;
    }

    // Phương thức hiển thị thông tin của sub-goal
    @Override
    public String toString() {
        return "SubGoal{" +
                "id=" + id +
                ", parentGoalId=" + parentGoalId +
                ", title='" + title + '\'' +
                ", isCompleted=" + isCompleted +
                '}';
    }
}
