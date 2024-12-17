package com.audioquiz.designsystem.model;


import androidx.annotation.NonNull;

public class AchievementUi {
    private int id;
    private int icon;
    private String name;
    private String description;

    public AchievementUi(int icon, String name, String description) {
        this.icon = icon;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NonNull
    @Override
    public String toString() {
        return "com.example.model.domain.AchievementUi{" +
                "icon=" + icon +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}