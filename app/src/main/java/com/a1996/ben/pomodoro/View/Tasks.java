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
import android.widget.TextView;
import android.widget.Toast;

import com.a1996.ben.pomodoro.R;

import Model.Task;
import Model.TaskArray;
import Utils.TaskDataSource;

public class Tasks extends AppCompatActivity implements TaskListFragment.TaskAdapterInterface, EditTaskFragment.EditTaskInterface{

    TaskListFragment mTaskListFragment;
    EditTaskFragment mEditTaskFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        mTaskListFragment = new TaskListFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.tasksPlaceHolder, mTaskListFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void goToContent(int position) {
        Bundle args = new Bundle();
        args.putString("TITLE", TaskArray.taskArrayList.get(position).getTitle());
        args.putString("CONTENT", TaskArray.taskArrayList.get(position).getContent());
        args.putInt("INDEX", position);
        TaskContentFragment taskContentFragment = new TaskContentFragment();
        taskContentFragment.setArguments(args);
        getFragmentManager().beginTransaction()
                .replace(R.id.tasksPlaceHolder, taskContentFragment)
                .addToBackStack("Task View").commit();
    }

    @Override
    public void longItemClick(int position) {
    }

    @Override
    public void goToEdit(int position) {
        Bundle args = new Bundle();
        args.putString("TITLE", TaskArray.taskArrayList.get(position).getTitle());
        args.putString("CONTENT", TaskArray.taskArrayList.get(position).getContent());
        mEditTaskFragment = new EditTaskFragment();
        mEditTaskFragment.setArguments(args);
        getFragmentManager().beginTransaction()
                .replace(R.id.tasksPlaceHolder, mEditTaskFragment)
                .addToBackStack("Task Edit View").commit();
    }

    @Override
    public void delete(int position, TaskAdapter taskAdapter) {
        TaskDataSource taskDataSource = new TaskDataSource(this.getApplicationContext());
        taskDataSource.open();
        taskDataSource.deleteTask(TaskArray.taskArrayList.get(position));
        taskDataSource.close();
        TaskArray.removeTask(position);
        taskAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmpty(TextView textView) {
        textView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmpty(TextView textView) {
        textView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void addTask() {
        Intent intent = new Intent(this, AddTask.class);
        this.startActivity(intent);
    }

    @Override
    public void doneEdit(String title, String content, int index) {
        TaskArray.taskArrayList.get(index).setTitle(title);
        TaskArray.taskArrayList.get(index).setContent(content);
        TaskArray.taskArrayList.get(index);
        TaskDataSource taskDataSource = new TaskDataSource(Tasks.this.getApplicationContext());
        taskDataSource.open();
        taskDataSource.updateTask(TaskArray.taskArrayList.get(index));
        taskDataSource.close();
        getFragmentManager().popBackStack();
    }

    @Override
    public void cancelEdit() {
        getFragmentManager().popBackStack();
    }
}
