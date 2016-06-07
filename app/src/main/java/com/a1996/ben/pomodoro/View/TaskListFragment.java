package com.a1996.ben.pomodoro.View;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a1996.ben.pomodoro.R;

import Model.Task;
import Model.TaskArray;

/**
 * Created by Ben on 6/5/2016.
 */
public class TaskListFragment extends Fragment {

    TaskAdapter mTaskAdapter;
    TaskAdapterInterface mTaskAdapterInterface;
    TextView mTextView;

    public interface TaskAdapterInterface {
        public void goToContent(int position);
        public void longItemClick(int position);
        public void delete(int position, TaskAdapter taskAdapter);
        public void showEmpty(TextView textView);
        public void hideEmpty(TextView textView);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_list_fragment, container, false);
        mTextView = (TextView) view.findViewById(R.id.taskListEmpty);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerTaskView);
        mTaskAdapterInterface = (TaskAdapterInterface) getActivity();
        mTaskAdapter = new TaskAdapter(mTaskAdapterInterface, mTextView);
        recyclerView.setAdapter(mTaskAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mTaskAdapter.notifyDataSetChanged();
        showHideEmpty();
    }

    public void showHideEmpty() {
        if (TaskArray.taskArrayList.isEmpty()) {
            mTaskAdapterInterface.showEmpty(mTextView);
        }
        else {
            mTaskAdapterInterface.hideEmpty(mTextView);
        }
    }
}
