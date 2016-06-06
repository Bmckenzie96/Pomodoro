package com.a1996.ben.pomodoro.View;

import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.a1996.ben.pomodoro.R;

import java.util.ArrayList;

import Model.Task;
import Model.TaskArray;

/**
 * Created by Ben on 6/5/2016.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    ArrayList<Task> mTasks;
    TaskListFragment.TaskAdapterInterface mTaskAdapterInterface;

    public TaskAdapter(TaskListFragment.TaskAdapterInterface taskAdapterInterface) {
        mTasks = TaskArray.taskArrayList;
        mTaskAdapterInterface = taskAdapterInterface;
    }


    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_list_item, parent, false);
        TaskViewHolder taskViewHolder = new TaskViewHolder(view);
        return taskViewHolder;
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        holder.bindTask(mTasks.get(position));
        holder.mIndex = position;
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView mTaskTitle;
        int mIndex;

        public void bindTask(Task task) {
            mTaskTitle.setText(task.getTitle());
        }

        public TaskViewHolder(View view) {
            super(view);
            mTaskTitle = (TextView) view.findViewById(R.id.taskTitle);
            ImageButton viewTask = (ImageButton) view.findViewById(R.id.viewTask);
            ImageButton editTask = (ImageButton) view.findViewById(R.id.taskEdit);
            ImageButton deleteTask =(ImageButton) view.findViewById(R.id.taskDelete);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mTaskAdapterInterface.longItemClick(mIndex);
                    return true;
                }
            });
            viewTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mTaskAdapterInterface.goToContent(mIndex);
                }
            });
        }
    }

}
