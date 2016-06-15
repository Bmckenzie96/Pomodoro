package com.a1996.ben.pomodoro.View;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.a1996.ben.pomodoro.R;
import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import Model.BackendlessTask;
import Model.Task;
import Model.TaskArray;
import Utils.TaskDataSource;
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
        final Task task = new Task(title.getText().toString(), content.getText().toString(), TaskSQLHelper.BackendlessUserId);
        TaskArray.addTask(task, this);
        final BackendlessTask backendlessTask = new BackendlessTask(task.getTitle(), task.getContent(), task.getOwnersId());
        Backendless.Persistence.save( backendlessTask, new AsyncCallback<BackendlessTask>() {
            public void handleResponse( BackendlessTask response )
            {
                // new Contact instance has been saved
                Log.i("Task_save", "saved successfully" + backendlessTask.getOwner());
            }

            public void handleFault( BackendlessFault fault )
            {
                // an error has occurred, the error code can be retrieved with fault.getCode()
                Toast.makeText(AddTask.this, "Something went wrong, check your internet.", Toast.LENGTH_LONG).show();
                task.setIsDirty(1);
                TaskDataSource taskDataSource = new TaskDataSource(AddTask.this);
                taskDataSource.open();
                taskDataSource.updateTask(task);
                taskDataSource.close();
            }
        });
        finish();
    }

    @Override
    public void cancel() {
        finish();
    }
}
