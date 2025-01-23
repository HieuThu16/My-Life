package com.example.imhieu;

public class Target {
    private String title;
    private String description;
    private int progress;
    private String deadline;
    private boolean isLongTerm;

    // Constructor
    public Target(String title, String description, int progress, String deadline, boolean isLongTerm) {
        this.title = title;
        this.description = description;
        this.progress = progress;
        this.deadline = deadline;
        this.isLongTerm = isLongTerm;
    }

    // Getter for title
    public String getTitle() {
        return title;
    }

    // Setter for title
    public void setTitle(String title) {
        this.title = title;
    }

    // Getter for description
    public String getDescription() {
        return description;
    }

    // Setter for description
    public void setDescription(String description) {
        this.description = description;
    }

    // Getter for progress
    public int getProgress() {
        return progress;
    }

    // Setter for progress
    public void setProgress(int progress) {
        this.progress = progress;
    }

    // Getter for deadline
    public String getDeadline() {
        return deadline;
    }

    // Setter for deadline
    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    // Getter for isLongTerm
    public boolean isLongTerm() {
        return isLongTerm;
    }

    // Setter for isLongTerm
    public void setLongTerm(boolean longTerm) {
        isLongTerm = longTerm;
    }
}
