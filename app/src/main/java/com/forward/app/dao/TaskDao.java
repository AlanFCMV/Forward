package com.forward.app.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.forward.app.models.Task;

@Dao
public interface TaskDao {
    @Insert
    void insertTasks(Task... tasks);

    @Update
    void updateTasks(Task... tasks);

    @Delete
    void deleteTasks(Task... tasks);
}
