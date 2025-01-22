package com.example.banthan;

public class EnglishSkill {
    private String title;
    private String description;
    private int progress;
    private String lastActivity;

    // Constructor
    public EnglishSkill(String title, String description, int progress, String lastActivity) {
        this.title = title;
        this.description = description;
        this.progress = progress;
        this.lastActivity = lastActivity;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getProgress() {
        return progress;
    }

    public String getLastActivity() {
        return lastActivity;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void setLastActivity(String lastActivity) {
        this.lastActivity = lastActivity;
    }

    // Optional: Override toString() for better debugging
    @Override
    public String toString() {
        return "EnglishSkill{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", progress=" + progress +
                ", lastActivity='" + lastActivity + '\'' +
                '}';
    }
}
