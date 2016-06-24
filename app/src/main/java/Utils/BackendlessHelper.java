package Utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.ArrayList;

import Model.Task;
import Model.TaskArray;

/**
 * Created by Ben on 6/16/2016.
 */
public class BackendlessHelper {

    Context mContext;
    public BackendlessHelper(Context context) {
        mContext = context;
    }

    //insert
    public void backendInsert(final Task task) {
        final TaskDataSource taskDataSource = new TaskDataSource(mContext);
        taskDataSource.open();
        Backendless.Persistence.save( task, new AsyncCallback<Task>() {
            public void handleResponse( Task response )
            {
                // new Task instance has been saved
                Log.i("backendInsertobjid:", response.getObjectId());
                Log.i("backendInsertownId", response.getOwnerId());
                task.setObjectId(response.getObjectId());
                task.setOwnerId(response.getOwnerId());
                taskDataSource.updateTask(task);
                taskDataSource.close();
            }

            public void handleFault( BackendlessFault fault )
            {
                Log.i("backendInsert:", fault.getMessage());
                task.setIsDirty(1);
                taskDataSource.updateTask(task);
                taskDataSource.close();
                Toast.makeText(mContext, fault.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    //select
    public void backendSelectAllUserTasks(String userId, final ArrayList<Task> insertTasksHere) {

        insertTasksHere.clear();

        Backendless.Persistence.of( Task.class).find( new AsyncCallback<BackendlessCollection<Task>>(){
            @Override
            public void handleResponse( BackendlessCollection<Task> response )
            {
                int size = response.getCurrentPage().size();
                for (Task task : response.getCurrentPage()) {
                    insertTasksHere.add(task);
                    Log.i("task gotten", task.getContent());
                }
                if (size > 0) {
                    response.nextPage(this);
                }
                else {
                    TaskArray.taskArrayList.clear();
                    for (Task task : insertTasksHere) {
                        TaskArray.addTask(task, mContext);
                    }
                }
            }
            @Override
            public void handleFault( BackendlessFault fault )
            {
                Toast.makeText(mContext, "Unable to update task list."
                + fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //update
    public void backendUpdate(final Task task) {
        if (task.getObjectId() == "" || task.getObjectId() == null) {
            Log.i("backendUpdate", "task not already in database, cannot update");
            return;
        }
        final TaskDataSource taskDataSource = new TaskDataSource(mContext);
        taskDataSource.open();
        Backendless.Persistence.save( task, new AsyncCallback<Task>() {
            public void handleResponse( Task response )
            {
                // new Task instance has been saved
                Log.i("backendUpdate", "task updated");
            }

            public void handleFault( BackendlessFault fault )
            {
                Log.i("backendUpdate", fault.getMessage());
                task.setIsDirty(1);
                taskDataSource.updateTask(task);
                taskDataSource.close();
                Toast.makeText(mContext, fault.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    //delete
    public void backendlessDelete(final Task task) {
        Backendless.Persistence.of( Task.class ).remove( task, new AsyncCallback<Long>()
        {
            public void handleResponse( Long response )
            {
                // task has been deleted. The response is a time in milliseconds when the object was deleted
                Log.i("backendlessDelete", "task deleted");
            }
            public void handleFault( BackendlessFault fault )
            {
                // an error has occurred, the error code can be retrieved with fault.getCode()
                Log.i("backendlessDelete", fault.getMessage());
                TaskDataSource taskDataSource = new TaskDataSource(mContext);
                taskDataSource.open();
                task.setIsDirty(2);
                taskDataSource.insertTask(task); //not updating so when task removed on main ui can still have the dirty version.
                taskDataSource.close();
            }
        } );
    }

}
