package com.forward.app.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "goals")
public class Milestone {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "goal_id")
    public int goalId;

    public String name;
    public boolean complete;
}
