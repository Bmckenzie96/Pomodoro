package com.a1996.ben.pomodoro.View;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.a1996.ben.pomodoro.R;
import com.backendless.Backendless;
import com.backendless.persistence.local.UserIdStorageFactory;

import java.util.ArrayList;

import Model.Task;
import Model.TaskArray;
import Utils.BackendlessHelper;
import Utils.CountdownTimer;
import Utils.TaskSQLHelper;

public class Home extends AppCompatActivity implements TimerFragment.FragmentCreated {

    TimerFragment mTimerFragment;
    private static boolean firstRun = true;
    public static boolean isRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        TaskSQLHelper.BackendlessUserId = UserIdStorageFactory.instance().getStorage().get();
        if (firstRun) {
            TaskArray.initialPopulation(this);
            firstRun = false;
        }
        Log.i("user_id", TaskSQLHelper.BackendlessUserId);



        ArrayList<Task> insertTasksHere = new ArrayList<>();
        BackendlessHelper backendlessHelper = new BackendlessHelper(this);
        backendlessHelper.backendSelectAllUserTasks(TaskSQLHelper.BackendlessUserId, insertTasksHere);



        mTimerFragment = new TimerFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.placeHolder, mTimerFragment);
        fragmentTransaction.commit();
    }


    @Override
    public void onFragmentCreated(View v) {
        //Toast.makeText(this, "made Invisible",Toast.LENGTH_SHORT).show();
        v.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onStartTimer(View v) {
        v.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTaskListClick() {
        Intent i = new Intent(Home.this, Tasks.class);
        startActivity(i);
    }

    @Override
    public void handleLogout(String fault) {
        Toast.makeText(Home.this, fault, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goToSignIn() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isRunning = false;
    }
}
