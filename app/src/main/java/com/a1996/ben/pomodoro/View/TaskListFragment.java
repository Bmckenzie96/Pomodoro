package com.a1996.ben.pomodoro.View;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a1996.ben.pomodoro.R;

import Model.Task;

/**
 * Created by Ben on 6/5/2016.
 */
public class TaskListFragment extends Fragment {

    TaskAdapter mTaskAdapter;

    public interface TaskAdapterInterface {
        public void goToContent(int position);
        public void longItemClick(int position);
        public void delete(int position, TaskAdapter taskAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_list_fragment, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerTaskView);
        TaskAdapterInterface taskAdapterInterface = (TaskAdapterInterface) getActivity();
        mTaskAdapter = new TaskAdapter(taskAdapterInterface);
        recyclerView.setAdapter(mTaskAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mTaskAdapter.notifyDataSetChanged();
    }
}
