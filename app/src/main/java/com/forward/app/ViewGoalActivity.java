package com.forward.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.forward.app.dao.GoalDao;
import com.forward.app.dao.TaskDao;
import com.forward.app.models.GoalWithTasks;
import com.forward.app.models.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewGoalActivity extends AppCompatActivity {

    public static final int NUMTASKS = 10;
    public static final int GREEN = 0xFF207524;
    public static final int GREY = 0xFF606060;

    private GoalWithTasks goal;
    private GoalDao goalDao;
    private TaskDao taskDao;
    private int completeTasks = 0;
    private int numValidTasks;
    private Button completeGoalButton;

    // Map button ids to tasks
    private HashMap<Integer, Task> idMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_goal);
        Intent intent = getIntent();
        int goalId = intent.getIntExtra("GoalId", -1);

        if (goalId == -1)
        {
            // No goal to view
            Log.d("ForwardError", "No goal to view here");
            Intent intent2 = new Intent(this, MainActivity.class);
            startActivity(intent2);
        }

        // Read goal from database
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        goalDao = db.getGoalDao();
        taskDao = db.getTaskDao();
        goal = goalDao.getGoalWithTasks(goalId);

        // Get Views
        completeGoalButton = findViewById(R.id.complete_goal);
        TextView name = findViewById(R.id.viewGoalName);
        TextView description = findViewById(R.id.viewGoalDescription);
        List<Button> taskButtons = new ArrayList<>();
        LinearLayout linearLayout = findViewById(R.id.viewTasksLayout);
        for (int i = 0; i < NUMTASKS; i++) {
            taskButtons.add((Button) linearLayout.getChildAt(i));
        }

        idMap = new HashMap<>();

        // Assign values to Views
        name.setText(goal.goal.name);
        description.setText(goal.goal.description);
        numValidTasks = goal.tasks.size();
        for (int i = 0; i < NUMTASKS; i++)
        {
            Button button = taskButtons.get(i);

            if (i < numValidTasks)
            {
                Task task = goal.tasks.get(i);
                button.setText(task.name);
                idMap.put(button.getId(), task);
                if (task.complete)
                {
                    button.setBackgroundColor(GREEN);
                    completeTasks++;
                }
                else
                {
                    button.setBackgroundColor(GREY);
                }
            }
            else
            {
                button.setVisibility(View.GONE);
            }
        }
    }

    public void toggleTask(View v)
    {
        Button button = (Button) v;
        Task task = idMap.get(button.getId());

        if (task.complete)
        {
            task.complete = false;
            taskDao.updateTasks(task);
            button.setBackgroundColor(GREY);
            completeTasks--;
            completeGoalButton.setBackgroundColor(GREY);
            completeGoalButton.setEnabled(false);
            goal.goal.complete = false;
            goalDao.updateGoals(goal.goal);

        }
        else
        {
            task.complete = true;
            taskDao.updateTasks(task);
            button.setBackgroundColor(GREEN);
            completeTasks++;
            if (completeTasks == numValidTasks)
            {
                completeGoalButton.setEnabled(true);
            }
        }
    }

    public void toggleGoal(View v)
    {
        Button button = (Button) v;

        if (goal.goal.complete)
        {
            goal.goal.complete = false;
            goalDao.updateGoals(goal.goal);
            button.setBackgroundColor(GREY);
        }
        else
        {
            goal.goal.complete = true;
            goalDao.updateGoals(goal.goal);
            button.setBackgroundColor(GREEN);
        }
    }

    public void editGoal(View v)
    {
        Intent i = new Intent(this, AddGoalActivity.class);
        i.putExtra("GoalId", goal.goal.id);
        startActivity(i);
    }

}