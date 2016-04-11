package com.riansousa.foodtrackerv2.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This is the object model for the MyRecord table.
 * You can use it for insert, update, delete & select
 * interactions with the table.
 */
public class MyRecordDBHelper extends SQLiteOpenHelper {

    /** declare constants */
    private static final String TAG = "FoodTracker";
    public static final String TABLE_NAME = "myRecord";
    public static final String PRIMARY_KEY_NAME = "id";
    public static final String TIME = "time";
    public static final String GROUP = "foodgroup";
    public static final String ITEM = "item";
    public static final String PORTION = "portion";
    public static final String CALORIES = "calories";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FoodTrackerV2.db";

    /** create sql test string */
    private static final String TEST_SQL = "SELECT * FROM " + TABLE_NAME;

    /** create sql string to create the table */
    private static final String TABLE_SPECIFICATIONS =
            "CREATE TABLE " +
                    TABLE_NAME + "(" +
                    PRIMARY_KEY_NAME + " INTEGER PRIMARY KEY, " +
                    TIME + " TEXT," +
                    GROUP + " TEXT," +
                    ITEM + " TEXT," +
                    PORTION + " INTEGER," +
                    CALORIES + " INTEGER" +
                    ")";

    /**
     * constructor
     * @param context
     */
    public MyRecordDBHelper(Context context) {
        /** Constructor to create DB object */
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /** not implemented */
    }

    /**
     * Public method to re-create the DB structure
     * @param db
     */
    public void onReCreate(SQLiteDatabase db) {
        try {
            /** re-initialize the table */
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            db.execSQL(TABLE_SPECIFICATIONS);
        } catch (Exception e) {
            Log.i(TAG, "MyRecordDBHelper.onReCreate() - ERROR:" + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // not implemented
    }
}
