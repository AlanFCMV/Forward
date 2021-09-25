package com.forward.app.models;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class GoalWithTasks {
    @Embedded public Goal goal;
    @Relation(
            parentColumn = "id",
            entityColumn = "goalId"
    )
    public List<Task> tasks;
}
