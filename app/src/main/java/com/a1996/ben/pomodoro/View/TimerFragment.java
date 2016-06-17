package com.a1996.ben.pomodoro.View;

import android.app.Fragment;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.a1996.ben.pomodoro.R;
import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import Utils.CountdownTimer;

/**
 * Created by Ben on 6/4/2016.
 */
public class TimerFragment extends Fragment implements View.OnClickListener {
    public interface FragmentCreated {
        void onFragmentCreated(View v);
        void onStartTimer(View v);
        void onTaskListClick();
        void handleLogout(String fault);
        void goToSignIn();
    }
    Button mLogout;
    Button mFocus;
    Button mBreak;
    Button mTaskList;
    TextView mTimeView;
    ImageButton mPlayPause;
    FragmentCreated mListener;
    public static boolean fromNotification = false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.timer_fragment, container, false);
        mListener = (FragmentCreated) getActivity();
        mTaskList = (Button) view.findViewById(R.id.goToTasks);
        mTaskList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onTaskListClick();
            }
        });
        mFocus = (Button) view.findViewById(R.id.focusButton);
        mFocus.setOnClickListener(this);
        mBreak = (Button) view.findViewById(R.id.breakButton);
        mBreak.setOnClickListener(this);
        mLogout = (Button) view.findViewById(R.id.logoutButton);
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Backendless.UserService.logout(new AsyncCallback<Void>()
                {
                    public void handleResponse( Void response )
                    {
                        // user has been logged out.
                        mListener.goToSignIn();
                    }

                    public void handleFault( BackendlessFault fault )
                    {
                        // something went wrong and logout failed, to get the error code call fault.getCode()
                        mListener.handleLogout(fault.getMessage());
                    }
                });
            }
        });
        mTimeView = (TextView) view.findViewById(R.id.timer);
        mPlayPause = (ImageButton) view.findViewById(R.id.playPause);
        mPlayPause.setOnClickListener(this);
        mListener.onFragmentCreated(mPlayPause);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (fromNotification) {
            CountdownTimer.getInstance(mTimeView, getActivity()).play(mTimeView);
            fromNotification = false;
            mPlayPause.setVisibility(View.VISIBLE);
            mPlayPause.setBackgroundResource(android.R.drawable.ic_media_pause);
        }
        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(5);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.focusButton:
                CountdownTimer.getInstance(mTimeView, getActivity()).startTime(5, 1000, mTimeView);
                mPlayPause.setBackgroundResource(android.R.drawable.ic_media_pause);
                mListener.onStartTimer(mPlayPause);
                CountdownTimer.getInstance(mTimeView, getActivity()).isFocus = true;
                break;
            case R.id.breakButton:
                CountdownTimer.getInstance(mTimeView, getActivity()).startTime(300, 1000, mTimeView);
                mPlayPause.setBackgroundResource(android.R.drawable.ic_media_pause);
                mListener.onStartTimer(mPlayPause);
                CountdownTimer.getInstance(mTimeView, getActivity()).isFocus = false;
                break;
            case R.id.playPause:
                if (CountdownTimer.getInstance(mTimeView, getActivity()).isCounting) {
                    CountdownTimer.getInstance(mTimeView, getActivity()).pause();
                    mPlayPause.setBackgroundResource(android.R.drawable.ic_media_play);
                }
                else {
                    CountdownTimer.getInstance(mTimeView, getActivity()).play(mTimeView);
                    mPlayPause.setBackgroundResource(android.R.drawable.ic_media_pause);
                }
                break;
        }
    }

}
