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
    public List<Goal> getAllGoals();

    @Transaction
    @Query("SELECT * FROM goals WHERE id = :goalId")
    public List<GoalWithTasks> getGoalWithTasks(int goalId);

    @Insert
    public void insertAll(Goal... goals);

    @Update
    public void updateGoals(Goal... goals);

    @Delete
    public void deleteGoals(Goal... goals);
}
