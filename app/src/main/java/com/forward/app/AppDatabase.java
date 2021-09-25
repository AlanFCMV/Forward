package com.forward.app;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.forward.app.dao.GoalDao;
import com.forward.app.dao.TaskDao;
import com.forward.app.models.Goal;
import com.forward.app.models.Task;

@Database(entities = {Goal.class, Task.class}, version = 1)
abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "appDatabase.db";
    private static volatile AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static AppDatabase create(final Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, DB_NAME).build();
    }

    abstract public GoalDao getGoalDao();
    abstract public TaskDao getTaskDao();
}
