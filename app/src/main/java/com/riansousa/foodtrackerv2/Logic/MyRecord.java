package com.riansousa.foodtrackerv2.Logic;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import com.riansousa.foodtrackerv2.Database.MyRecordDBHelper;
import com.riansousa.foodtrackerv2.Model.Record;

/**
 *
 */
public class MyRecord {
    private static final String TAG = "FoodTracker";
    private FoodProfile _foodProfile = new FoodProfile();

    /*
     * input: Context, String
     * output: void
     * This function adds new items to the FoodProfile repository
     */
    public void AddNewItem(android.content.Context context, String item) {
        try {
            /** declare local variables */
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Date date = new Date();

            /* get the item profile */
            Hashtable<String, String> foodProfle = _foodProfile.GetFoodProfileByName(context, item);

            /* instantiate helper class */
            MyRecordDBHelper helper = new MyRecordDBHelper(context);
            Log.i(TAG, "MyRecord.AddNewItem() - FoodProfileDBHelper created");

            /** Data repository db is in write mode */
            SQLiteDatabase db = helper.getWritableDatabase();
            Log.i(TAG, "MyRecord.AddNewItem() - SQLiteDatabase created");

            /** record fruit calories */
            if (foodProfle.get("fruitCalories") != null) {
                ContentValues values = new ContentValues();
                values.put(MyRecordDBHelper.TIME, dateFormat.format(date));
                values.put(MyRecordDBHelper.GROUP, "Fruit");
                values.put(MyRecordDBHelper.ITEM, item);
                values.put(MyRecordDBHelper.PORTION, foodProfle.get("fruitPortion"));
                values.put(MyRecordDBHelper.CALORIES, foodProfle.get("fruitCalories"));
                db.insert(MyRecordDBHelper.TABLE_NAME, null, values);
            }

            /** record grain calories */
            if (foodProfle.get("grainCalories") != null) {
                ContentValues values = new ContentValues();
                values.put(MyRecordDBHelper.TIME, dateFormat.format(date));
                values.put(MyRecordDBHelper.GROUP, "Grain");
                values.put(MyRecordDBHelper.ITEM, item);
                values.put(MyRecordDBHelper.PORTION, foodProfle.get("grainPortion"));
                values.put(MyRecordDBHelper.CALORIES, foodProfle.get("grainCalories"));
                db.insert(MyRecordDBHelper.TABLE_NAME, null, values);;
            }

            /** record protein calories */
            if (foodProfle.get("proteinCalories") != null) {
                ContentValues values = new ContentValues();
                values.put(MyRecordDBHelper.TIME, dateFormat.format(date));
                values.put(MyRecordDBHelper.GROUP, "Protein");
                values.put(MyRecordDBHelper.ITEM, item);
                values.put(MyRecordDBHelper.PORTION, foodProfle.get("proteinPortion"));
                values.put(MyRecordDBHelper.CALORIES, foodProfle.get("proteinCalories"));
                db.insert(MyRecordDBHelper.TABLE_NAME, null, values);;
            }

            /** record vegetable calories */
            if (foodProfle.get("vegetableCalories") != null) {
                ContentValues values = new ContentValues();
                values.put(MyRecordDBHelper.TIME, dateFormat.format(date));
                values.put(MyRecordDBHelper.GROUP, "Vegetable");
                values.put(MyRecordDBHelper.ITEM, item);
                values.put(MyRecordDBHelper.PORTION, foodProfle.get("vegetablePortion"));
                values.put(MyRecordDBHelper.CALORIES, foodProfle.get("vegetableCalories"));
                db.insert(MyRecordDBHelper.TABLE_NAME, null, values);;
            }

            /** record dairy calories */
            if (foodProfle.get("dairyCalories") != null) {
                ContentValues values = new ContentValues();
                values.put(MyRecordDBHelper.TIME, dateFormat.format(date));
                values.put(MyRecordDBHelper.GROUP, "Dairy");
                values.put(MyRecordDBHelper.ITEM, item);
                values.put(MyRecordDBHelper.PORTION, foodProfle.get("dairyPortion"));
                values.put(MyRecordDBHelper.CALORIES, foodProfle.get("dairyCalories"));
                db.insert(MyRecordDBHelper.TABLE_NAME, null, values);;
            }

            /** Log item saved to repository */
            Log.i(TAG, "MyRecord.AddNewItem() - The consumption of " + item + " has been saved to the repository.");
        } catch (Exception e) {
            Log.i(TAG, "MyRecord.AddNewItem() - ERROR:" + e.getMessage());
        }
    }

    /*
     * input: Context
     * output: ArrayList<Record>
     * This method will return all records for a specific day
     */
    public ArrayList<Record> getByDate(android.content.Context context, String date) {
        /* declare return array */
        ArrayList<Record> records = new ArrayList<Record>();
        try {
            /* instantiate helper class */
            MyRecordDBHelper helper = new MyRecordDBHelper(context);
            SQLiteDatabase database = helper.getReadableDatabase();

            /* get data from repository */
            String query = "SELECT * FROM " + helper.TABLE_NAME + " WHERE time LIKE '%" + date + "%'";
            Cursor c = database.rawQuery(query, null);
            if (c != null) {
                while (c.moveToNext()) {
                    /** Create a new record object with row data */
                    Record record = new Record();
                    record.setId(c.getInt(c.getColumnIndex(helper.PRIMARY_KEY_NAME)));
                    record.setTime(c.getString(c.getColumnIndex(helper.TIME)));
                    record.setGroup(c.getString(c.getColumnIndex(helper.GROUP)));
                    record.setItem(c.getString(c.getColumnIndex(helper.ITEM)));
                    record.setPortion(c.getString(c.getColumnIndex(helper.PORTION)));
                    record.setCalories(c.getString(c.getColumnIndex(helper.CALORIES)));

                    /** add the record to the array */
                    records.add(record);
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "MyRecord.getByDate() - ERROR:" + e.getMessage());
        }
        return records;
    }

    /**
     * Method to count the total number of calories from an arraylist of record objects
     * @param records
     * @return
     */
    public int getTotalCalories(ArrayList<Record> records) {
        int totalCalories = 0;
        try {
            /** loop through array and total calories*/
            for (int i = 0; i < records.size(); i++) {
                Record record = records.get(i);
                totalCalories = totalCalories + Integer.parseInt(record.getCalories());
            }

        } catch (Exception e) {
            Log.i(TAG, "MyRecord.getTotalCalories() - ERROR:" + e.getMessage());
        }
        return totalCalories;
    }
}
