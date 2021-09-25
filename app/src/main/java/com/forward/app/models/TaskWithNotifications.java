package com.forward.app.models;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class TaskWithNotifications {
    @Embedded public Task task;
    @Relation(
            parentColumn = "id",
            entityColumn = "taskId"
    )
    public List<Notification> notifications;
}
