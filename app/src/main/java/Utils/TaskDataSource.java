package Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import Model.Task;

/**
 * Created by Ben on 6/8/2016.
 */
public class TaskDataSource {
    private SQLiteDatabase mDatabase;
    private TaskSQLHelper mTaskSQLHelper;
    private Context mContext;

    public TaskDataSource(Context context) {
        mContext = context;
        mTaskSQLHelper = new TaskSQLHelper(context);
    }

    //open
    public void open() throws SQLException {
        mDatabase = mTaskSQLHelper.getWritableDatabase();
    }

    //close
    public void close() {
        mDatabase.close();
    }

    //insert
    public long insertTask(Task task) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TaskSQLHelper.COLUMN_TITLE, task.getTitle());
        contentValues.put(TaskSQLHelper.COLUMN_CONTENT, task.getContent());
        return mDatabase.insert(TaskSQLHelper.TABLE_NAME, null, contentValues);
    }

    //select
    public Cursor selectAllTasks() {
        Cursor cursor = mDatabase.query(
                TaskSQLHelper.TABLE_NAME,
                new String[] {TaskSQLHelper.COLUMN_ID, TaskSQLHelper.COLUMN_TITLE, TaskSQLHelper.COLUMN_CONTENT},
                null, null, null, null, null
        );
        return cursor;
    }

    //select from id
    public Cursor selectFromId(long id) {
        String whereClause = "WHERE " + TaskSQLHelper.COLUMN_ID + " = ?";
        Cursor cursor = mDatabase.query(
                TaskSQLHelper.TABLE_NAME,
                new String[] {TaskSQLHelper.COLUMN_TITLE, TaskSQLHelper.COLUMN_CONTENT},
                whereClause,
                new String[] {id + ""},
                null, null, null
        );
        return cursor;
    }

    //update
    public int updateTask(Task task){
        ContentValues contentValues = new ContentValues();
        String whereClause = TaskSQLHelper.COLUMN_ID + " = ?";
        contentValues.put(TaskSQLHelper.COLUMN_TITLE, task.getTitle());
        contentValues.put(TaskSQLHelper.COLUMN_CONTENT, task.getContent());
        int rowsUpdated = mDatabase.update(
                TaskSQLHelper.TABLE_NAME,
                contentValues,
                whereClause,
                new String[] {task.getRowId()+""}
        );
        return rowsUpdated;
    }

    //delete
    public void deleteTask(Task task) {
        String whereClause = TaskSQLHelper.COLUMN_ID + " = ?";
        mDatabase.delete(
                TaskSQLHelper.TABLE_NAME,
                whereClause,
                new String[] {task.getRowId() + ""}
        );
    }
}
