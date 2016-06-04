package com.a1996.ben.pomodoro.View;

import android.app.Fragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.a1996.ben.pomodoro.R;

import Utils.CountdownTimer;

/**
 * Created by Ben on 6/4/2016.
 */
public class TimerFragment extends Fragment implements View.OnClickListener {
    Button mFocus;
    Button mBreak;
    TextView mTimeView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.timer_fragment, container, false);
        mFocus = (Button) view.findViewById(R.id.focusButton);
        mFocus.setOnClickListener(this);
        mBreak = (Button) view.findViewById(R.id.breakButton);
        mBreak.setOnClickListener(this);
        mTimeView = (TextView) view.findViewById(R.id.timer);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.focusButton:
                CountdownTimer.getInstance().startTime(1500, 1000, mTimeView);
                break;
            case R.id.breakButton:
                CountdownTimer.getInstance().startTime(300, 1000, mTimeView);
                break;
        }
    }

}
