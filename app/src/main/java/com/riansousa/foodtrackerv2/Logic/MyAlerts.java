package com.riansousa.foodtrackerv2.Logic;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.riansousa.foodtrackerv2.Database.MyAlertsDBHelper;
import java.util.Hashtable;

public class MyAlerts {

    private static final String TAG = "FoodTracker";

    /**
     * Method to get a Food Profile record by name
     * input: context, name of the item
     * output: Hashtable<String, String>
     */
    public Hashtable<String, String> GetMyAlerts(android.content.Context context) {
        /** declare local variables */
        SQLiteDatabase db = null;
        Hashtable<String, String> record = new Hashtable<String, String>();

        /** wrap with try/catch in case of error. close db in finally block */
        try {
            /** instantiate helper class */
            MyAlertsDBHelper helper = new MyAlertsDBHelper(context);
            Log.i(TAG, "MyAlerts.GetMyAlerts() - MyAlertsDBHelper created");

            /** db is accessible for reading */
            db = helper.getReadableDatabase();

            /** columns to return from query */
            String[] returnCols = {
                    "name",
                    "maxDaily",
                    "fruitMin",
                    "fruitMax",
                    "grainMin",
                    "grainMax",
                    "proteinMin",
                    "proteinMax",
                    "vegetableMin",
                    "vegetableMax",
                    "dairyMin",
                    "dairyMax"
            };

            /** construct the where clause using placeholders and add values to where Args array */
            String whereClause = helper.NAME + " = ?";
            String[] whereArgs = new String[] {
                    "me"
            };

            /** Pass params into query() method letting method merge WHERE clause.
             * The query results will match output columns specified above
             */
            Cursor c = db.query(
                    helper.TABLE_NAME,                  // table to query
                    returnCols,                         // columns to return
                    whereClause,                        // columns for WHERE clause
                    whereArgs,                          // values for WHERE clause
                    null,                               // don't group rows
                    null,                               // don't filter by row groups
                    null                                // sort order
            );

            /**
             * While loop for cursor iteration obtained from:
             * http://stackoverflow.com/questions/10723770/whats-the-best-way-to-iterate-an-android-cursor
             */
            while (c.moveToNext()) {
                /** create a hash table of key/value pairs for UI element value setting */
                record.put("name", c.getString(c.getColumnIndexOrThrow(helper.NAME)));
                if (Integer.parseInt(c.getString(c.getColumnIndexOrThrow(helper.MAXDAILY))) > 0) record.put("maxDaily", c.getString(c.getColumnIndexOrThrow(helper.MAXDAILY)));
                if (Integer.parseInt(c.getString(c.getColumnIndexOrThrow(helper.FRUITMIN))) > 0) record.put("fruitMin", c.getString(c.getColumnIndexOrThrow(helper.FRUITMIN)));
                if (Integer.parseInt(c.getString(c.getColumnIndexOrThrow(helper.FRUITMAX))) > 0) record.put("fruitMax", c.getString(c.getColumnIndexOrThrow(helper.FRUITMAX)));
                if (Integer.parseInt(c.getString(c.getColumnIndexOrThrow(helper.GRAINMIN))) > 0) record.put("grainMin", c.getString(c.getColumnIndexOrThrow(helper.GRAINMIN)));
                if (Integer.parseInt(c.getString(c.getColumnIndexOrThrow(helper.GRAINMAX))) > 0) record.put("grainMax", c.getString(c.getColumnIndexOrThrow(helper.GRAINMAX)));
                if (Integer.parseInt(c.getString(c.getColumnIndexOrThrow(helper.PROTEINMIN))) > 0) record.put("proteinMin", c.getString(c.getColumnIndexOrThrow(helper.PROTEINMIN)));
                if (Integer.parseInt(c.getString(c.getColumnIndexOrThrow(helper.PROTEINMAX))) > 0) record.put("proteinMax", c.getString(c.getColumnIndexOrThrow(helper.PROTEINMAX)));
                if (Integer.parseInt(c.getString(c.getColumnIndexOrThrow(helper.VEGETABLEMIN))) > 0) record.put("vegetableMin", c.getString(c.getColumnIndexOrThrow(helper.VEGETABLEMIN)));
                if (Integer.parseInt(c.getString(c.getColumnIndexOrThrow(helper.VEGETABLEMAX))) > 0) record.put("vegetableMax", c.getString(c.getColumnIndexOrThrow(helper.VEGETABLEMAX)));
                if (Integer.parseInt(c.getString(c.getColumnIndexOrThrow(helper.DAIRYMIN))) > 0) record.put("dairyMin", c.getString(c.getColumnIndexOrThrow(helper.DAIRYMIN)));
                if (Integer.parseInt(c.getString(c.getColumnIndexOrThrow(helper.DAIRYMAX))) > 0) record.put("dairyMax", c.getString(c.getColumnIndexOrThrow(helper.DAIRYMAX)));
            }

        } catch (Exception e) {
            Log.i(TAG, "FoodProfile.GetFoodProfileByName() - ERROR:" + e.getMessage());
        } finally {
            db.close();
        }
        /* pass data back up to the UI */
        return record;
    }

    /*
     * input: context, String, ContentValues
     * output: void
     * This function updates items in the My Alerts repository
     */
    public void UpdateAlerts(android.content.Context context, ContentValues profile) {
        try {
            /* instantiate helper class */
            MyAlertsDBHelper helper = new MyAlertsDBHelper(context);
            Log.i(TAG, "MyAlerts.UpdateItem() - MyAlertsDBHelper created");

            /** Data repository db is in write mode */
            SQLiteDatabase db = helper.getWritableDatabase();
            Log.i(TAG, "MyAlerts.UpdateItem() - SQLiteDatabase created");

            /** construct the where clause using placeholders and add values to where Args array */
            String whereClause = helper.NAME + " = ?";
            String[] whereArgs = new String[] {
                    "me"
            };

            /**  update table  */
            db.update(MyAlertsDBHelper.TABLE_NAME, profile, whereClause, whereArgs);

        } catch (Exception e) {
            Log.i(TAG, "MyAlerts.UpdateItem() - ERROR:" + e.getMessage());
        }
    }
}
