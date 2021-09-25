package com.forward.app.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "goals")
public class Goal {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public String description;
    public boolean complete;

    public Goal(String name, String description, boolean complete) {
        this.name = name;
        this.description = description;
        this.complete = complete;
    }
}
