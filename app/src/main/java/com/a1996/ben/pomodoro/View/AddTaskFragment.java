package com.a1996.ben.pomodoro.View;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.a1996.ben.pomodoro.R;

import Utils.HideKeyboard;

/**
 * Created by Ben on 6/6/2016.
 */
public class AddTaskFragment extends Fragment {

    public interface DoneAddingTask{
        public void doneAdding(EditText title, EditText content);
        public void cancel();
    }

    Button mDoneButton;
    EditText mTitle;
    EditText mContent;
    Button mCancel;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_task_fragment, container, false);
        HideKeyboard.setupUI(view, getActivity());
        mDoneButton = (Button) view.findViewById(R.id.doneButton);
        mCancel = (Button) view.findViewById(R.id.cancelButton);
        mTitle = (EditText) view.findViewById(R.id.taskTitleEntry);
        mContent = (EditText) view.findViewById(R.id.taskContentEntry);
        final DoneAddingTask doneAddingTask = (DoneAddingTask) getActivity();
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doneAddingTask.cancel();
            }
        });
        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doneAddingTask.doneAdding(mTitle, mContent);
            }
        });

        return view;
    }
}
