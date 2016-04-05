package com.riansousa.foodtrackerv2.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This is the object model for the MyAlerts table.
 * You can use it for insert, update, delete & select
 * interactions with the table.
 */
public class MyAlertsDBHelper extends SQLiteOpenHelper {

    /** declare constants */
    private static final String TAG = "FoodTracker";
    public static final String TABLE_NAME = "myAlerts";
    public static final String PRIMARY_KEY_NAME = "id";
    public static final String NAME = "name";
    public static final String MAXDAILY = "maxDaily";
    public static final String FRUITMIN = "fruitMin";
    public static final String FRUITMAX = "fruitMax";
    public static final String GRAINMIN = "grainMin";
    public static final String GRAINMAX = "grainMax";
    public static final String PROTEINMIN = "proteinMin";
    public static final String PROTEINMAX = "proteinMax";
    public static final String VEGETABLEMIN = "vegetableMin";
    public static final String VEGETABLEMAX = "vegetableMax";
    public static final String DAIRYMIN = "dairyMin";
    public static final String DAIRYMAX = "dairyMax";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FoodTrackerV2.db";

    /** create sql strings for table */
    private static final String TABLE_SPECIFICATIONS =
            "CREATE TABLE " +
                    TABLE_NAME + "(" +
                    PRIMARY_KEY_NAME + " INTEGER PRIMARY KEY, " +
                    NAME + " TEXT," +
                    MAXDAILY + " INTEGER," +
                    FRUITMIN + " INTEGER," +
                    FRUITMAX + " INTEGER," +
                    GRAINMIN + " INTEGER," +
                    GRAINMAX + " INTEGER," +
                    PROTEINMIN + " INTEGER," +
                    PROTEINMAX + " INTEGER," +
                    VEGETABLEMIN + " INTEGER," +
                    VEGETABLEMAX + " INTEGER," +
                    DAIRYMIN + " INTEGER," +
                    DAIRYMAX + " INTEGER" +
                    ")";

    private static final String DEFAULT_ROW =
            "INSERT INTO " + TABLE_NAME +
                    "("+NAME+", "+MAXDAILY+", "+FRUITMIN+", "+FRUITMAX+", "+GRAINMIN+", "+GRAINMAX+", "+PROTEINMIN+", "+PROTEINMAX+", "+VEGETABLEMIN+", "+VEGETABLEMAX+", "+DAIRYMIN+", "+DAIRYMAX+")" +
                    " values " +
                    "('me', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)";
    private static final String TEST_SQL = "SELECT " + NAME + " FROM " + TABLE_NAME;

    public MyAlertsDBHelper(Context context) {
        /** Constructor to create DB object */
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Cursor c = db.rawQuery(TEST_SQL, null);
            if (c.getCount() == 0) {
                db.execSQL(TABLE_SPECIFICATIONS);
                db.execSQL(DEFAULT_ROW);
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
            db.execSQL(DEFAULT_ROW);
        } catch (Exception e) {
            Log.i(TAG, "MyAlertsDBHelper.onReCreate() - ERROR:" + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // not implemented
    }
}
