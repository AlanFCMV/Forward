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

public class AddGoalActivity extends AppCompatActivity {

    public static final int NUMTASKS = 10;
    // public static final int GREEN = 0xFF207524;
    // public static final int GREY = 0xFF606060;

    private GoalWithTasks goal;
    private GoalDao goalDao;
    private TaskDao taskDao;
    // private int completeTasks = 0;
    // private int numValidTasks;

    // Map textView ids to tasks
    private HashMap<Integer, Task> idMap;
    private List<TextView> taskTexts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);
        Intent intent = getIntent();
        int goalId = intent.getIntExtra("GoalId", -1);
        Log.d("Help", ((Integer) goalId).toString());
        if (goalId == -1)
        {
            // No goal to view
            Log.d("ForwardError", "No goal to edit here");
            Intent intent2 = new Intent(this, MainActivity.class);
            startActivity(intent2);
        }

        // Read goal from database
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        goalDao = db.getGoalDao();
        taskDao = db.getTaskDao();
        Log.d("Help", ((Integer) goalId).toString());
        goal = goalDao.getGoalWithTasks(goalId);

        Log.d("Help", (goal == null) ? "goal is null" : "goal is not null");

        // Get Views
        TextView name = findViewById(R.id.editGoalName);
        TextView description = findViewById(R.id.editGoalDescription);
        taskTexts = new ArrayList<>();
        LinearLayout linearLayout = findViewById(R.id.editTasksLayout);
        for (int i = 0; i < NUMTASKS; i++) {
            taskTexts.add((TextView) linearLayout.getChildAt(i));
        }

        idMap = new HashMap<>();

        // Assign values to Views
        name.setText(goal.goal.name);
        description.setText(goal.goal.description);
        int numValidTasks = goal.tasks.size();
        for (int i = 0; i < NUMTASKS; i++)
        {
            Log.d("Help", ((Integer) i).toString());
            TextView textView = taskTexts.get(i);

            if (i < numValidTasks)
            {
                Task task = goal.tasks.get(i);
                textView.setText(task.name);
                idMap.put(textView.getId(), task);
            }
        }
    }

    private String charSeqToString(CharSequence seq)
    {
        StringBuilder builder = new StringBuilder(seq.length());
        builder.append(seq);
        return builder.toString();
    }

    public void delete(View v)
    {
        goalDao.deleteGoals(goal.goal);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void saveGoalData(View v)
    {
        TextView name = findViewById(R.id.editGoalName);
        goal.goal.name = charSeqToString(((TextView) name).getText());

        TextView description = findViewById(R.id.editGoalDescription);
        goal.goal.description = charSeqToString(((TextView) description).getText());

        for (int i = 0; i < NUMTASKS; i++)
        {
            TextView textView = taskTexts.get(i);

            if (textView.getText().equals(""))
            {
                Task task = idMap.get(textView.getId());

                if (task != null)
                {
                    taskDao.deleteTasks(task);
                }
            }

            else
            {
                Task task = idMap.get(textView.getId());

                if (task != null)
                {
                    task.name = charSeqToString(textView.getText());
                    taskDao.updateTasks(task);
                }
                else
                {
                    Task newTask = new Task(goal.goal.id, charSeqToString(textView.getText()), false);
                    taskDao.insertTasks(newTask);
                }
            }
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}