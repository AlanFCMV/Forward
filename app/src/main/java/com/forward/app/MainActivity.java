package com.forward.app;

import androidx.appcompat.app.AppCompatActivity;

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
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    private static final int GREEN = 2127140;
    private static final int WHITE = 16777215;
    private static final int GRAY = 5659735;

    private List<Integer> goalIds;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

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
        List<Button> buttons = new ArrayList<>();
        LinearLayout linearLayout = findViewById(R.id.scroll_box_layout);

        if (linearLayout == null) {
            Log.d("HELP", "HELP!!!!!!!");
            return;
        }

        // Read goals from database
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        GoalDao goalDao = db.getGoalDao();
        List<Goal> goals = goalDao.getAllGoals();

        for (int i = 0; i < 15; i++)
        {
            buttons.add((Button) linearLayout.getChildAt(i));
        }

        fillGoalButtons(goals, buttons);
    }

    public void fillGoalButtons(List<Goal> goals, List<Button> buttons)
    {
        List<Goal> incompleteGoals = new ArrayList<Goal>();
        List<Goal> completeGoals = new ArrayList<Goal>();
        Goal currentGoal;

        for (int i = 0; i < goals.size() && i < 15; i++)
        {
            currentGoal = goals.get(i);

            if (currentGoal.complete)
            {
                completeGoals.add(currentGoal);
            }
            else incompleteGoals.add(currentGoal);
        }

        Log.d("HELP", "Made it past Goal list splitting");

        goalIds = new ArrayList<Integer>();
        int i;
        for (i = 0; i < incompleteGoals.size(); i++)
        {
            currentGoal = incompleteGoals.get(i);
            buttons.get(i).setText(currentGoal.name);
            buttons.get(i).setBackgroundColor(0xFF207524);
            goalIds.add(currentGoal.id);
        }

        Log.d("HELP", "Made it past incomplete goal list");

        for (; i < goals.size(); i++)
        {
            currentGoal = completeGoals.get(i - incompleteGoals.size());
            Log.d("HELP", currentGoal.name);
            buttons.get(i).setText(currentGoal.name);
            goalIds.add(currentGoal.id);
        }

        Log.d("HELP", "Made it past complete goal list");

        for (; i < 15; i++)
        {
            buttons.get(i).setVisibility(View.INVISIBLE);
        }

        Log.d("HELP", "Made it past goal list");

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
}