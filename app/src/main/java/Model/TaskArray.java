package Model;

import java.util.ArrayList;

/**
 * Created by Ben on 6/5/2016.
 */
public class TaskArray {
    public static ArrayList<Task> taskArrayList = new ArrayList<Task>();
    public static void addTask(Task task) {
        taskArrayList.add(task);
    }
}
