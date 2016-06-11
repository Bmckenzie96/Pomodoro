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

import Model.TaskArray;
import Utils.CountdownTimer;

public class Home extends AppCompatActivity implements TimerFragment.FragmentCreated {

    TimerFragment mTimerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        TaskArray.initialPopulation(this);
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
}
