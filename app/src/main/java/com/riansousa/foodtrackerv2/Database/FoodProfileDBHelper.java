package com.riansousa.foodtrackerv2.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This is the object model for the FoodProfile table.
 * You can use it for insert, update, delete & select
 * interactions with the table.
 */
public class FoodProfileDBHelper extends SQLiteOpenHelper {

    /** declare constants */
    private static final String TAG = "FoodTracker";
    public static final String TABLE_NAME = "foodProfile";
    public static final String PRIMARY_KEY_NAME = "id";
    public static final String NAME = "name";
    public static final String FRUITCALORIES = "fruitCalories";
    public static final String FRUITPORTION = "fruitPortion";
    public static final String GRAINCALORIES = "grainCalories";
    public static final String GRAINPORTION = "grainPortion";
    public static final String PROTEINCALORIES = "proteinCalories";
    public static final String PROTEINPORTION = "proteinPortion";
    public static final String VEGETABLECALORIES = "vegetableCalories";
    public static final String VEGETABLEPORTION = "vegetablePortion";
    public static final String DAIRYCALORIES = "dairyCalories";
    public static final String DAIRYPORION = "dairyPortion";
    public static final String ISNEW = "isNew";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FoodTrackerV2.db";

    /** create sql test string */
    private static final String TEST_SQL = "SELECT " + NAME + " FROM " + TABLE_NAME;

    /** create sql string to create the table */
    private static final String TABLE_SPECIFICATIONS =
            "CREATE TABLE " +
                    TABLE_NAME + "(" +
                    PRIMARY_KEY_NAME + " INTEGER PRIMARY KEY, " +
                    NAME + " TEXT," +
                    FRUITCALORIES + " INTEGER," +
                    FRUITPORTION + " INTEGER," +
                    GRAINCALORIES + " INTEGER," +
                    GRAINPORTION + " INTEGER," +
                    PROTEINCALORIES + " INTEGER," +
                    PROTEINPORTION + " INTEGER," +
                    VEGETABLECALORIES + " INTEGER," +
                    VEGETABLEPORTION + " INTEGER," +
                    DAIRYCALORIES + " INTEGER," +
                    DAIRYPORION + " INTEGER," +
                    ISNEW + " INTEGER" +
                    ")";

    public FoodProfileDBHelper(Context context) {
        /** Constructor to create DB object */
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            try {
                Cursor c = db.rawQuery(TEST_SQL, null);
            } catch (Exception e) {
                db.execSQL(TABLE_SPECIFICATIONS);
            }
        } catch (Exception e) {
            Log.i(TAG, "MyAlertsDBHelper.onCreate() - ERROR:" + e.getMessage());
        }
    }

    public void onReCreate(SQLiteDatabase db) {
        try {
            /** re-initialize the table */
            db.execSQL("DROP TABLE " + TABLE_NAME);
            db.execSQL(TABLE_SPECIFICATIONS);
        } catch (Exception e) {
            Log.i(TAG, "MyAlertsDBHelper.onReCreate() - ERROR:" + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // not implemented
    }
}
