package com.forward.app.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.forward.app.DateConverter;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "notifications",
        foreignKeys = @ForeignKey(
                entity = Task.class,
                parentColumns = "id",
                childColumns = "taskId",
                onDelete = CASCADE,
                onUpdate = CASCADE
        )
)
@TypeConverters(DateConverter.class)
public class Notification {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int taskId;

    public boolean recurring;
    public boolean[] daysOfWeek;
    public Date date;

    public Notification(int taskId, boolean recurring, boolean[] daysOfWeek, Date date) {
        this.taskId = taskId;
        this.recurring = recurring;
        this.daysOfWeek = daysOfWeek;
        this.date = date;
    }
}
