package com.a1996.ben.pomodoro.View;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a1996.ben.pomodoro.R;

/**
 * Created by Ben on 6/6/2016.
 */
public class TaskContentFragment extends Fragment{
    int mIndex;
    TextView taskTitle;
    TextView taskContent;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.task_content_fragment, container, false);
        taskContent = (TextView) view.findViewById(R.id.taskViewContent);
        taskTitle = (TextView) view.findViewById(R.id.taskViewTitle);
        taskTitle.setText(getArguments().getString("TITLE"));
        taskContent.setText(getArguments().getString("CONTENT"));
        return view;
    }
}
