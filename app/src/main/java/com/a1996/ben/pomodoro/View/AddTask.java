package com.a1996.ben.pomodoro.View;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.a1996.ben.pomodoro.R;

import Model.Task;
import Model.TaskArray;
import Utils.TaskSQLHelper;

public class AddTask extends AppCompatActivity implements AddTaskFragment.DoneAddingTask {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        AddTaskFragment addTaskFragment = new AddTaskFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction  fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.addTaskPlaceholder, addTaskFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void doneAdding(EditText title, EditText content) {
        Task task = new Task(title.getText().toString(), content.getText().toString(), TaskSQLHelper.BackendlessUserId);
        TaskArray.addTask(task, this);
        finish();
    }

    @Override
    public void cancel() {
        finish();
    }
}
