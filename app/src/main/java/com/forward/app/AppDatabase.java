package com.forward.app;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.forward.app.dao.GoalDao;
import com.forward.app.dao.TaskDao;
import com.forward.app.models.Goal;
import com.forward.app.models.Task;

@Database(entities = {Goal.class, Task.class}, version = 1)
abstract class AppDatabase extends RoomDatabase {
    abstract public GoalDao getGoalDao();
    abstract public TaskDao getTaskDao();
}
