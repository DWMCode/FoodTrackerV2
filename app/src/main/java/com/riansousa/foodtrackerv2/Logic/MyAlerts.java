package com.riansousa.foodtrackerv2.Logic;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.riansousa.foodtrackerv2.Database.MyAlertsDBHelper;
import com.riansousa.foodtrackerv2.Model.Record;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
            Log.i(TAG, "MyAlerts.GetMyAlerts() - ERROR:" + e.getMessage());
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
            Log.i(TAG, "MyAlerts.UpdateAlerts() - MyAlertsDBHelper created");

            /** Data repository db is in write mode */
            SQLiteDatabase db = helper.getWritableDatabase();
            Log.i(TAG, "MyAlerts.UpdateAlerts() - SQLiteDatabase created");

            /** construct the where clause using placeholders and add values to where Args array */
            String whereClause = helper.NAME + " = ?";
            String[] whereArgs = new String[] {
                    "me"
            };

            /**  update table  */
            db.update(MyAlertsDBHelper.TABLE_NAME, profile, whereClause, whereArgs);

        } catch (Exception e) {
            Log.i(TAG, "MyAlerts.UpdateAlerts() - ERROR:" + e.getMessage());
        }
    }

    /**
     * Method to return a message is the user has eaten too much of a certain food group
     * @param context
     * @return
     */
    public String CheckNotification(android.content.Context context) {
        String message = "";
        try {
            /** declare local variables */
            int fruitMax = 0;
            int grainMax = 0;
            int proteinMax = 0;
            int vegetableMax = 0;
            int dairyMax = 0;
            int totalCalories = 0;
            String groupMsg = "";
            String totalMsg = "";

            /** prepare date */
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date date = new Date();

            /** get todays records */
            MyRecord myRecord = new MyRecord();
            ArrayList<Record> records = myRecord.getByDate(context, dateFormat.format(date));

            /** loop through records */
            for (int i = 0; i < records.size(); i++) {
                Record record = records.get(i);

                /** sort into food groups */
                switch (record.getGroup()) {
                    case "Fruit":
                        fruitMax = fruitMax + Integer.parseInt(record.getCalories());
                        break;
                    case "Grain":
                        grainMax = grainMax + Integer.parseInt(record.getCalories());
                        break;
                    case "Protein":
                        proteinMax = proteinMax + Integer.parseInt(record.getCalories());
                        break;
                    case "Vegetable":
                        vegetableMax = vegetableMax + Integer.parseInt(record.getCalories());
                        break;
                    case "Dairy":
                        dairyMax = dairyMax + Integer.parseInt(record.getCalories());
                        break;
                }

                /** update total */
                totalCalories = totalCalories + Integer.parseInt(record.getCalories());
            }

            /** pull from db and set UI values */
            Hashtable<String, String> record = GetMyAlerts(context);
            int maxDailyCalories = Integer.parseInt(record.get("maxDaily"));
            int dailyFruitMax = Integer.parseInt(record.get("fruitMax"));
            int dailyGrainMax = Integer.parseInt(record.get("grainMax"));
            int dailyProteinMax = Integer.parseInt(record.get("proteinMax"));
            int dailyVegetablesMax = Integer.parseInt(record.get("vegetableMax"));
            int dailyDairyMax = Integer.parseInt(record.get("dairyMax"));

            /** compare with daily totals and message */
            if (totalCalories > maxDailyCalories) {
                totalMsg = totalMsg + "You have exceeded your total daily calories. ";
            }
            if (dairyMax > dailyDairyMax) {
                groupMsg = groupMsg + "dairy, ";
            }
            if (fruitMax > dailyFruitMax) {
                groupMsg = groupMsg + "fruits, ";
            }
            if (grainMax > dailyGrainMax) {
                groupMsg = groupMsg + "grains, ";
            }
            if (proteinMax > dailyProteinMax) {
                groupMsg = groupMsg + "protein, ";
            }
            if (vegetableMax > dailyVegetablesMax) {
                groupMsg = groupMsg + "vegetable, ";
            }

            /** create result message */
            if (totalMsg.length() > 0) {
                message = message + totalMsg;
            }
            if (groupMsg.length() > 0) {
                groupMsg = groupMsg.substring(0, groupMsg.length()-2);
                message = message + "You have exceeded your daily allowance for these food groups: " + groupMsg;
            }

        } catch (Exception e) {
            Log.i(TAG, "MyAlerts.CheckNotification() - ERROR:" + e.getMessage());
        }
        return message;
    }

    /**
     * Method to return a message if the user has not enough of each food group
     * @param context
     * @return
     */
    public String CheckAlarm(android.content.Context context) {
        String message = "";
        try {
            /** declare local variables */
            int fruitMin = 0;
            int grainMin = 0;
            int proteinMin = 0;
            int vegetableMin = 0;
            int dairyMin = 0;
            int totalCalories = 0;
            String groupMsg = "";
            String totalMsg = "";

            /** prepare date */
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date date = new Date();

            /** get todays records */
            MyRecord myRecord = new MyRecord();
            ArrayList<Record> records = myRecord.getByDate(context, dateFormat.format(date));

            /** loop through records */
            for (int i = 0; i < records.size(); i++) {
                Record record = records.get(i);

                /** sort into food groups */
                switch (record.getGroup()) {
                    case "Fruit":
                        fruitMin = fruitMin + Integer.parseInt(record.getCalories());
                        break;
                    case "Grain":
                        grainMin = grainMin + Integer.parseInt(record.getCalories());
                        break;
                    case "Protein":
                        proteinMin = proteinMin + Integer.parseInt(record.getCalories());
                        break;
                    case "Vegetable":
                        vegetableMin = vegetableMin + Integer.parseInt(record.getCalories());
                        break;
                    case "Dairy":
                        dairyMin = dairyMin + Integer.parseInt(record.getCalories());
                        break;
                }

                /** update total */
                totalCalories = totalCalories + Integer.parseInt(record.getCalories());
            }

            /** pull from db and set UI values */
            Hashtable<String, String> record = GetMyAlerts(context);
            int dailyFruitMin = Integer.parseInt(record.get("fruitMin"));
            int dailyGrainMin = Integer.parseInt(record.get("grainMin"));
            int dailyProteinMin = Integer.parseInt(record.get("proteinMin"));
            int dailyVegetablesMin = Integer.parseInt(record.get("vegetableMin"));
            int dailyDairyMin = Integer.parseInt(record.get("dairyMin"));

            /** compare with daily totals and message */
            if (dairyMin < dailyDairyMin) {
                groupMsg = groupMsg + "dairy, ";
            }
            if (fruitMin < dailyFruitMin) {
                groupMsg = groupMsg + "fruits, ";
            }
            if (grainMin < dailyGrainMin) {
                groupMsg = groupMsg + "grains, ";
            }
            if (proteinMin < dailyProteinMin) {
                groupMsg = groupMsg + "protein, ";
            }
            if (vegetableMin < dailyVegetablesMin) {
                groupMsg = groupMsg + "vegetable, ";
            }

            /** create result message */
            if (groupMsg.length() > 0) {
                groupMsg = groupMsg.substring(0, groupMsg.length()-2);
                message = message + "You need to eat more of these food groups: " + groupMsg;
            }

        } catch (Exception e) {
            Log.i(TAG, "MyAlerts.CheckAlarm() - ERROR:" + e.getMessage());
        }
        return message;
    }
}
