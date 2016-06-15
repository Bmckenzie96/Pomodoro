package Model;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import Utils.TaskDataSource;
import Utils.TaskSQLHelper;

/**
 * Created by Ben on 6/5/2016.
 */
public class TaskArray {
    public static ArrayList<Task> taskArrayList = new ArrayList<Task>();
    public static void removeTask(int index) {taskArrayList.remove(index);}
    public static void addTask(Task task, Context context) {

        taskArrayList.add(task);
        TaskDataSource taskDataSource = new TaskDataSource(context.getApplicationContext());
        taskDataSource.open();
        task.setRowId(taskDataSource.insertTask(task));
        taskDataSource.close();
        Log.i("task_row_id", task.getRowId() + "");
    }
    public static void initialPopulation(Context context) {
        context = context.getApplicationContext();
        TaskDataSource taskDataSource = new TaskDataSource(context);
        taskDataSource.open();
        Cursor cursor = taskDataSource.selectAllTasks();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int i = cursor.getColumnIndex(TaskSQLHelper.COLUMN_TITLE);
            String title = cursor.getString(i);
            i = cursor.getColumnIndex(TaskSQLHelper.COLUMN_CONTENT);
            String content = cursor.getString(i);
            i =  cursor.getColumnIndex(TaskSQLHelper.COLUMN_USERID);
            String ownerId = cursor.getString(i);
            Task task = new Task(title, content, ownerId);
            i = cursor.getColumnIndex(TaskSQLHelper.COLUMN_ID);
            task.setRowId(cursor.getInt(i));
            taskArrayList.add(task);
            cursor.moveToNext();
        }
        taskDataSource.close();
    }
}
