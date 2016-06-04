package com.a1996.ben.pomodoro.View;

import android.app.Fragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.a1996.ben.pomodoro.R;

/**
 * Created by Ben on 6/4/2016.
 */
public class TimerFragment extends Fragment implements View.OnClickListener {
    CountDownTimer mCountDownTimer;
    Button mFocus;
    TextView mTimeView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.timer_fragment, container, false);
        mFocus = (Button) view.findViewById(R.id.focusButton);
        mFocus.setOnClickListener(this);
        mTimeView = (TextView) view.findViewById(R.id.timer);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.focusButton:
                startTime(1500, 1000);
                break;
        }
    }

    public void startTime(long time, long delay) {
        if(mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        mCountDownTimer = new CountDownTimer(time * 1000 + 100, delay) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeView.setText(millisUntilFinished/1000 + "");
            }

            @Override
            public void onFinish() {
                mTimeView.setText("0");
            }
        }.start();
    }
}
