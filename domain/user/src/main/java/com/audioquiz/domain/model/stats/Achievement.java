package com.adrian.model.stats;


/**
 * Represents an achievement.
 * [icon] The icon of the achievement.
 * [name] The name of the achievement.
 * [description] The description of the achievement.
 */
public class Achievement {
    private int icon;
    private String name;
    private String description;

    public Achievement(int icon, String name, String description) {
        this.icon = icon;
        this.name = name;
        this.description = description;
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

    @Override
    public String toString() {
        return "com.example.model.domain.Achievement{" +
                "icon=" + icon +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}