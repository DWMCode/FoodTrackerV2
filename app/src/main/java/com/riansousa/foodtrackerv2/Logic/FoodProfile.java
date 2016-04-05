package com.riansousa.foodtrackerv2.Logic;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.riansousa.foodtrackerv2.Database.FoodProfileDBHelper;

import java.util.Hashtable;

public class FoodProfile {

    private static final String TAG = "FoodTracker";

    /**
     * input: string
     * output: void
     * This function records items consumed to the repository
     */
    public void RecordConsumption(String item) {
        /** Log item saved to repository */
        Log.i(TAG, "FoodProfile.RecordConsumption() - The consumption of " + item + " has been saved to the repository.");
    }

    /*
     * input: android.text.Editable
     * output: void
     * This function adds new items to the FoodProfile repository
     */
    public void AddNewItem(android.content.Context context, android.text.Editable item) {
        try {
            /* instantiate helper class */
            FoodProfileDBHelper helper = new FoodProfileDBHelper(context);
            Log.i(TAG, "FoodProfile.AddNewItem() - FoodProfileDBHelper created");

            /** Data repository db is in write mode */
            SQLiteDatabase db = helper.getWritableDatabase();
            Log.i(TAG, "FoodProfile.AddNewItem() - SQLiteDatabase created");

            /** Map of values created, where column names are the keys */
            ContentValues values = new ContentValues();
            values.put(FoodProfileDBHelper.NAME, item.toString());
            values.put(FoodProfileDBHelper.FRUITCALORIES, "0");
            values.put(FoodProfileDBHelper.FRUITPORTION, "0");
            values.put(FoodProfileDBHelper.GRAINCALORIES, "0");
            values.put(FoodProfileDBHelper.GRAINPORTION, "0");
            values.put(FoodProfileDBHelper.PROTEINCALORIES, "0");
            values.put(FoodProfileDBHelper.PROTEINPORTION, "0");
            values.put(FoodProfileDBHelper.VEGETABLECALORIES, "0");
            values.put(FoodProfileDBHelper.VEGETABLEPORTION, "0");
            values.put(FoodProfileDBHelper.DAIRYCALORIES, "0");
            values.put(FoodProfileDBHelper.DAIRYPORION, "0");
            values.put(FoodProfileDBHelper.ISNEW, 1);
            Log.i(TAG, "FoodProfile.AddNewItem() - values created!");

            /**  values inserted in row; insertion = primary key value of the new row */
            long insertion = db.insert(FoodProfileDBHelper.TABLE_NAME, null, values);
            Log.i(TAG, "FoodProfile.AddNewItem() - values Inserted; 'insertion=" + insertion);
        } catch (Exception e) {
            Log.i(TAG, "FoodProfile.AddNewItem() - ERROR:" + e.getMessage());
        }
    }

    /*
    * input: context, String, ContentValues
    * output: void
    * This function updates items in the repository
    */
    public void UpdateItem(android.content.Context context, String name, ContentValues profile) {
        try {
            /* instantiate helper class */
            FoodProfileDBHelper helper = new FoodProfileDBHelper(context);
            Log.i(TAG, "FoodProfile.UpdateItem() - FoodProfileDBHelper created");

            /** Data repository db is in write mode */
            SQLiteDatabase db = helper.getWritableDatabase();
            Log.i(TAG, "FoodProfile.UpdateItem() - SQLiteDatabase created");

            /** construct the where clause using placeholders and add values to where Args array */
            String whereClause = helper.NAME + " = ?";
            String[] whereArgs = new String[] {
                    name
            };

            /**  update table  */
            db.update(FoodProfileDBHelper.TABLE_NAME, profile, whereClause, whereArgs);

        } catch (Exception e) {
            Log.i(TAG, "FoodProfile.UpdateItem() - ERROR:" + e.getMessage());
        }
    }

    /*
    * input: context, String
    * output: void
    * This function deletes items in the repository
    */
    public void DeleteItem(android.content.Context context, String name) {
        try {
            /* instantiate helper class */
            FoodProfileDBHelper helper = new FoodProfileDBHelper(context);
            Log.i(TAG, "FoodProfile.DeleteItem() - FoodProfileDBHelper created");

            /** Data repository db is in write mode */
            SQLiteDatabase db = helper.getWritableDatabase();
            Log.i(TAG, "FoodProfile.DeleteItem() - SQLiteDatabase created");

            /** construct the where clause using placeholders and add values to where Args array */
            String whereClause = helper.NAME + " = ?";
            String[] whereArgs = new String[] {
                    name
            };

            /**  delete item from repository  */
            db.delete(FoodProfileDBHelper.TABLE_NAME, whereClause, whereArgs);

        } catch (Exception e) {
            Log.i(TAG, "FoodProfile.DeleteItem() - ERROR:" + e.getMessage());
        }
    }


    /**
     * Method to get a list of FoodProfile items
     * input: context
     * output: string array
     */
    public String[] GetFoodProfileList(android.content.Context context, int state) {
        /** declare local variables */
        String[] values = null;
        SQLiteDatabase db = null;

        /** wrap with try/catch in case of error. close db in finally block */
        try {
            /* instantiate helper class */
            FoodProfileDBHelper helper = new FoodProfileDBHelper(context);
            Log.i(TAG, "FoodProfile.GetFoodProfileList() - FoodProfileDBHelper created");

            /** db is accessible for reading */
            db = helper.getReadableDatabase();

            /** create table if it doesn't exist */
            helper.onCreate(db);

            /*
             * System code to re-create DB during DEBUG cycle
             */
            //helper.RecreateTable(db);

            /** columns to return from query */
            String[] returnCols = {"name"};

            /** declare Where clause and Args array */
            String whereClause = null;
            String[] whereArgs = null;

            /** set up conditional to drive results of query */
            if (state > 0) {
                whereClause = helper.ISNEW + " = ?";
                whereArgs = new String[] {
                        Integer.toString(state)
                };                
            }

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
             * Initialize the string array with the array size
             */
            values = new String[c.getCount()];

            /**
             * While loop for cursor iteration obtained from:
             * http://stackoverflow.com/questions/10723770/whats-the-best-way-to-iterate-an-android-cursor
             */
            int i = 0;
            while (c.moveToNext()) {
                /** add items to the array by index */
                values[i] = c.getString(c.getColumnIndexOrThrow(helper.NAME));
                i++;
            }

        } catch (Exception e) {
            Log.i(TAG, "FoodProfile.GetFoodProfileList() - ERROR:" + e.getMessage());
        } finally {
            db.close();
        }
        return values;
    }

    /**
     * Method to get a Food Profile record by name
     * input: context, name of the item
     * output: Hashtable<String, String>
     */
    public Hashtable<String, String> GetFoodProfileByName(android.content.Context context, String name) {
        /** declare local variables */
        SQLiteDatabase db = null;
        Hashtable<String, String> record = new Hashtable<String, String>();

        /** wrap with try/catch in case of error. close db in finally block */
        try {
            /* instantiate helper class */
            FoodProfileDBHelper helper = new FoodProfileDBHelper(context);
            Log.i(TAG, "FoodProfile.GetFoodProfileByName() - FoodProfileDBHelper created");

            /** db is accessible for reading */
            db = helper.getReadableDatabase();

            /** columns to return from query */
            String[] returnCols = {
                    "name",
                    "fruitCalories",
                    "fruitPortion",
                    "grainCalories",
                    "grainPortion",
                    "proteinCalories",
                    "proteinPortion",
                    "vegetableCalories",
                    "vegetablePortion",
                    "dairyCalories",
                    "dairyPortion",
                    "isNew"
            };

            /** construct the where clause using placeholders and add values to where Args array */
            String whereClause = helper.NAME + " = ?";
            String[] whereArgs = new String[] {
                    name
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
                if (Integer.parseInt(c.getString(c.getColumnIndexOrThrow(helper.FRUITCALORIES))) > 0) record.put("fruitCalories", c.getString(c.getColumnIndexOrThrow(helper.FRUITCALORIES)));
                if (Integer.parseInt(c.getString(c.getColumnIndexOrThrow(helper.FRUITPORTION))) > 0) record.put("fruitPortion", c.getString(c.getColumnIndexOrThrow(helper.FRUITPORTION)));
                if (Integer.parseInt(c.getString(c.getColumnIndexOrThrow(helper.GRAINCALORIES))) > 0) record.put("grainCalories", c.getString(c.getColumnIndexOrThrow(helper.GRAINCALORIES)));
                if (Integer.parseInt(c.getString(c.getColumnIndexOrThrow(helper.GRAINPORTION))) > 0) record.put("grainPortion", c.getString(c.getColumnIndexOrThrow(helper.GRAINPORTION)));
                if (Integer.parseInt(c.getString(c.getColumnIndexOrThrow(helper.PROTEINCALORIES))) > 0) record.put("proteinCalories", c.getString(c.getColumnIndexOrThrow(helper.PROTEINCALORIES)));
                if (Integer.parseInt(c.getString(c.getColumnIndexOrThrow(helper.PROTEINPORTION))) > 0) record.put("proteinPortion", c.getString(c.getColumnIndexOrThrow(helper.PROTEINPORTION)));
                if (Integer.parseInt(c.getString(c.getColumnIndexOrThrow(helper.VEGETABLECALORIES))) > 0) record.put("vegetableCalories", c.getString(c.getColumnIndexOrThrow(helper.VEGETABLECALORIES)));
                if (Integer.parseInt(c.getString(c.getColumnIndexOrThrow(helper.VEGETABLEPORTION))) > 0) record.put("vegetablePortion", c.getString(c.getColumnIndexOrThrow(helper.VEGETABLEPORTION)));
                if (Integer.parseInt(c.getString(c.getColumnIndexOrThrow(helper.DAIRYCALORIES))) > 0) record.put("dairyCalories", c.getString(c.getColumnIndexOrThrow(helper.DAIRYCALORIES)));
                if (Integer.parseInt(c.getString(c.getColumnIndexOrThrow(helper.DAIRYPORION))) > 0) record.put("dairyPortion", c.getString(c.getColumnIndexOrThrow(helper.DAIRYPORION)));
            }

        } catch (Exception e) {
            Log.i(TAG, "FoodProfile.GetFoodProfileByName() - ERROR:" + e.getMessage());
        } finally {
            db.close();
        }
        /* pass data back up to the UI */
        return record;
    }

}
