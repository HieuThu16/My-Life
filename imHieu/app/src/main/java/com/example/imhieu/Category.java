package com.example.imhieu;

import com.example.imhieu.Skill;

import java.io.Serializable;
import java.util.List;

public class Category implements Serializable {
    private int id;
    private String name;
    private List<Skill> skills;
    private float averageScore;

    // Constructor that initializes the fields
    public Category(int id, String name, List<Skill> skills) {
        this.id = id;
        this.name = name;
        this.skills = skills;
        this.averageScore = calculateAverageScore(skills);
    }

    // Method to calculate average score based on skills
    private float calculateAverageScore(List<Skill> skills) {
        if (skills == null || skills.isEmpty()) {
            return 0;
        }

        float totalScore = 0;
        for (Skill skill : skills) {
            totalScore += skill.getPoints(); // Assuming Skill has a getScore() method
        }

        return totalScore / skills.size();
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public float getAverageScore() {
        return averageScore;
    }
    public void setAverageScore(float averageScore) { this.averageScore = averageScore; }
}
