package com.a1996.ben.pomodoro.View;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.a1996.ben.pomodoro.R;

import Utils.CountdownTimer;

/**
 * Created by Ben on 6/4/2016.
 */
public class TimerFragment extends Fragment implements View.OnClickListener {
    public interface FragmentCreated {
        void onFragmentCreated(View v);
        void onStartTimer(View v);
    }
    Button mFocus;
    Button mBreak;
    ImageButton mPlayPause;
    TextView mTimeView;
    FragmentCreated mListener;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.timer_fragment, container, false);
        mListener = (FragmentCreated) getActivity();
        mFocus = (Button) view.findViewById(R.id.focusButton);
        mFocus.setOnClickListener(this);
        mBreak = (Button) view.findViewById(R.id.breakButton);
        mBreak.setOnClickListener(this);
        mTimeView = (TextView) view.findViewById(R.id.timer);
        mPlayPause = (ImageButton) view.findViewById(R.id.playPause);
        mPlayPause.setOnClickListener(this);
        mListener.onFragmentCreated(mPlayPause);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.focusButton:
                CountdownTimer.getInstance().startTime(1500, 1000, mTimeView);
                mPlayPause.setBackgroundResource(android.R.drawable.ic_media_pause);
                mListener.onStartTimer(mPlayPause);
                break;
            case R.id.breakButton:
                CountdownTimer.getInstance().startTime(300, 1000, mTimeView);
                mPlayPause.setBackgroundResource(android.R.drawable.ic_media_pause);
                mListener.onStartTimer(mPlayPause);
                break;
            case R.id.playPause:
                if (CountdownTimer.getInstance().isCounting) {
                    CountdownTimer.getInstance().pause();
                    mPlayPause.setBackgroundResource(android.R.drawable.ic_media_play);
                }
                else {
                    CountdownTimer.getInstance().play(mTimeView);
                    mPlayPause.setBackgroundResource(android.R.drawable.ic_media_pause);
                }
                break;
        }
    }

}
