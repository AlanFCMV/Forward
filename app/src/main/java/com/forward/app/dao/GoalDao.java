package com.forward.app.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.forward.app.models.Goal;
import com.forward.app.models.GoalWithTasks;

import java.util.List;

@Dao
public interface GoalDao {
    @Query("SELECT * FROM goals")
    List<Goal> getAllGoals();

    @Transaction
    @Query("SELECT * FROM goals WHERE id = :goalId")
    List<GoalWithTasks> getGoalWithTasks(int goalId);

    @Insert
    void insertGoals(Goal... goals);

    @Update
    void updateGoals(Goal... goals);

    @Delete
    void deleteGoals(Goal... goals);
}
