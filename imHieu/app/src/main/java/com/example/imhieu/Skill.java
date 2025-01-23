package com.example.imhieu;

public class Skill {
    private int id;
    private String name;
    private int points;
    private int maxPoints;
    private String description; // Thêm thuộc tính description

    // Constructor
    public Skill(int id, String name, int points, int maxPoints, String description) {
        this.id = id;
        this.name = name;
        this.points = points;
        this.maxPoints = maxPoints;
        this.description = description;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
