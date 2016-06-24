package Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ben on 6/8/2016.
 */
public class TaskSQLHelper extends SQLiteOpenHelper{

    public static String BackendlessUserId = "";
    public static final String TABLE_NAME = "TASKS";
    public static final String COLUMN_TITLE = "TITLE";
    public static final String COLUMN_CONTENT = "CONTENT";
    public static final String COLUMN_ID = "_ID";
    public static final String COLUMN_DIRTY = "DIRTY";
    public static final String COLUMN_USERID = "USERID";
    public static final String COLUMN_OBJECTID = "OBJECTID";
    private static final String DB_ALTER_OBJID = "ALTER TABLE " + TABLE_NAME
            + " ADD COLUMN " + COLUMN_OBJECTID + " TEXT";

    private static final String DB_NAME = "tasks.db";
    private static final int DB_VERSION = 3;
    private static final String DB_CREATE = "CREATE TABLE "
            + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TITLE + " TEXT, "
            + COLUMN_CONTENT + " TEXT, "
            + COLUMN_USERID + " TEXT, "
            + COLUMN_DIRTY + " INTEGER, "
            + COLUMN_OBJECTID + " TEXT)";

    public TaskSQLHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DB_ALTER_OBJID);
    }
}
