package com.a1996.ben.pomodoro.View;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.a1996.ben.pomodoro.R;

import Utils.HideKeyboard;

/**
 * Created by Ben on 6/7/2016.
 */
public class EditTaskFragment extends Fragment {
    public interface EditTaskInterface{
        public void doneEdit(String title, String content, int index);
        public void cancelEdit();
    }
    EditText mTitle;
    EditText mContent;
    Button mDoneButton;
    Button mCancelButton;
    int mIndex;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_edit_fragment, container, false);
        HideKeyboard.setupUI(view, getActivity());
        mTitle = (EditText) view.findViewById(R.id.taskViewTitleEdit);
        mContent = (EditText) view.findViewById(R.id.taskViewContentEdit);
        mDoneButton = (Button) view.findViewById(R.id.doneEdit);
        mCancelButton = (Button) view.findViewById(R.id.cancelEdit);
        mTitle.setText(getArguments().getString("TITLE"));
        mContent.setText(getArguments().getString("CONTENT"));
        mIndex = getArguments().getInt("INDEX");
        final EditTaskInterface editTaskInterface = (EditTaskInterface) getActivity();
        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTaskInterface.doneEdit(mTitle.getText().toString(), mContent.getText().toString(), mIndex);
            }
        });
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTaskInterface.cancelEdit();
            }
        });
        return view;
    }
}
