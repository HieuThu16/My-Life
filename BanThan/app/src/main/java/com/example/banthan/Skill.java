package com.example.banthan;

public class Skill {
    private String title;
    private String description;
    private int progress;

    // Constructor
    public Skill(String title, String description, int progress) {
        this.title = title;
        this.description = description;
        this.progress = progress;
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

    // Optional: Override toString() for better debugging
    @Override
    public String toString() {
        return "Skill{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", progress=" + progress +
                '}';
    }
}
