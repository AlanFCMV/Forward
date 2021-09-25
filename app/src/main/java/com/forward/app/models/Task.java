package com.forward.app.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks")
public class Task {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "goal_id")
    public int goalId;

    public String name;
    public boolean complete;

    public Task(int goalId, String name, boolean complete) {
        this.goalId = goalId;
        this.name = name;
        this.complete = complete;
    }
}
