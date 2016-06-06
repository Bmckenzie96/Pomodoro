package com.a1996.ben.pomodoro.View;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.a1996.ben.pomodoro.R;

import Model.Task;
import Model.TaskArray;

public class Tasks extends AppCompatActivity implements TaskListFragment.TaskAdapterInterface{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tasks.this, AddTask.class);
                startActivity(intent);
            }
        });
        TaskListFragment taskListFragment = new TaskListFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.tasksPlaceHolder, taskListFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void goToContent(int position) {
        Toast.makeText(Tasks.this, "item " + position  + " clicked",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void longItemClick(int position) {
        Toast.makeText(Tasks.this, "item " + position  + "long clicked",Toast.LENGTH_SHORT).show();
    }
}
