package com.forward.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.forward.app.dao.GoalDao;
import com.forward.app.models.Goal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static final int NUMGOALS = 15;
    public static final int GREEN = 0xFF207524;
    public static final int GREY = 0xFF606060;

    // Map button ids to the goal ids
    private HashMap<Integer, Integer> idMap;

    private GoalDao goalDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        List<Button> buttons = new ArrayList<>();
        LinearLayout linearLayout = findViewById(R.id.scroll_box_layout);

        if (linearLayout == null) {
            Log.d("ForwardError", "The linear layout is null");
            return;
        }

        // Read goals from database
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        goalDao = db.getGoalDao();
        List<Goal> goals = goalDao.getAllGoals();

        for (int i = 0; i < 15; i++) {
            buttons.add((Button) linearLayout.getChildAt(i));
        }

        fillGoalButtons(goals, buttons);
    }

    public void goalClicked(View v)
    {
        // Get goal id from the button id and pass it to new activity;
        Button button = (Button) v;
        int goalId = idMap.get(button.getId());
        Intent i = new Intent(this, ViewGoalActivity.class);
        i.putExtra("GoalId", goalId);
        startActivity(i);
    }

    public void addGoal(View v)
    {
        Goal newGoal = new Goal("New Goal",  "Add A Description Of Your Goal Here", false);
        goalDao.insertGoals(newGoal);

        List<Goal> goals = new ArrayList<>();
        goals = goalDao.getAllGoals();

        Goal newGoalWithId = goals.get(goals.size()-1);


        Intent i = new Intent(this, AddGoalActivity.class);
        i.putExtra("GoalId", newGoalWithId.id);
        Log.d("Help", "ID OF NEW GOAL" + ((Integer) newGoalWithId.id).toString());
        startActivity(i);
    }

    public void fillGoalButtons(List<Goal> goals, List<Button> buttons) {
        List<Goal> incompleteGoals = new ArrayList<Goal>();
        List<Goal> completeGoals = new ArrayList<Goal>();
        Goal currentGoal;

        for (int i = 0; i < goals.size() && i < NUMGOALS; i++) {
            currentGoal = goals.get(i);

            if (currentGoal.complete) {
                completeGoals.add(currentGoal);
            } else incompleteGoals.add(currentGoal);
        }

        Log.d("HELP", "Made it past Goal list splitting");

        idMap = new HashMap<>();
        int i;
        for (i = 0; i < incompleteGoals.size(); i++) {
            currentGoal = incompleteGoals.get(i);
            buttons.get(i).setText(currentGoal.name);
            buttons.get(i).setBackgroundColor(GREEN);
            idMap.put(buttons.get(i).getId(), currentGoal.id);
        }

        Log.d("HELP", "Made it past incomplete goal list");

        for (; i < goals.size(); i++) {
            currentGoal = completeGoals.get(i - incompleteGoals.size());
            Log.d("HELP", currentGoal.name);
            buttons.get(i).setText(currentGoal.name);
            buttons.get(i).setBackgroundColor(GREY);
            idMap.put(buttons.get(i).getId(), currentGoal.id);
        }

        Log.d("HELP", "Made it past complete goal list");

        for (; i < NUMGOALS; i++) {
            buttons.get(i).setVisibility(View.GONE);
        }

        Log.d("HELP", "Made it past goal list");

    }
}

/*
public void generateGoalButtons(List<Goal> goals)
{
    List<Goal> incompleteGoals = new ArrayList<Goal>();
    List<Goal> completeGoals = new ArrayList<Goal>();
    Goal currentGoal;

    for (int i = 0; i < goals.size(); i++)
    {
        currentGoal = goals.get(i);

        if (currentGoal.complete)
        {
            completeGoals.add(currentGoal);
        }
        else incompleteGoals.add(currentGoal);
    }

    for (int i = 0; i < incompleteGoals.size(); i++)
    {
        currentGoal = incompleteGoals.get(i);

        Button button = new Button(MainActivity.this);
        LinearLayout myLayout = (LinearLayout)(findViewById(R.id.scroll_box_layout));
        button.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
        ));

        button.setId(currentGoal.id);
        button.setText(currentGoal.name);
        button.setBackgroundColor(GREEN);
        button.setTextColor(WHITE);

        myLayout.addView(button);
    }

    for (int i = 0; i < completeGoals.size(); i++)
    {
        currentGoal = completeGoals.get(i);

        Button button = new Button(MainActivity.this);
        LinearLayout myLayout = (LinearLayout)(findViewById(R.id.scroll_box_layout));
        button.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
        ));

        button.setId(currentGoal.id);
        button.setText(currentGoal.name);
        button.setBackgroundColor(GRAY);
        button.setTextColor(WHITE);

        myLayout.addView(button);
    }
}*/

// Generate 20 random strings as names for goals
        /*Random rand = new Random();
        List<Goal> goals = new ArrayList<Goal>();
        for (int i = 0; i < 14; i++)
        {
            List<Character> chars = new ArrayList<Character>();
            int randLength = rand.nextInt(20) + 1;
            for (int j = 0; j < randLength; j++)
            {
                chars.add((char)('A' + rand.nextInt(26)));
            }

            StringBuilder builder = new StringBuilder(chars.size());
            for (Character ch : chars) {
                builder.append(ch);
            }
            String str = builder.toString();
            // boolean complete = rand.nextInt(2) == 0;
            boolean complete;
            if (i < 7)
                complete = true;
            else
                complete = false;
            Goal goal = new Goal(str, "description", complete);
            Log.d("LookingForStr", str);
            goals.add(goal);
        }

        // generateGoalButtons(goals);

        Log.d("LookingForStr", "THE STRINGS ARE DONE");

        */