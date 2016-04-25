package com.riansousa.foodtrackerv2.Logic;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.riansousa.foodtrackerv2.Database.FoodProfileDBHelper;
import com.riansousa.foodtrackerv2.Database.MyAlertsDBHelper;
import com.riansousa.foodtrackerv2.Database.MyRecordDBHelper;
import com.riansousa.foodtrackerv2.AlarmReceiver;

import java.util.Calendar;

public class SeedData {

    private static final String TAG = "FoodTracker";
    private static final String KEY = "pref_SeedCompleteV18";
    private static final int HOUR_OF_DAY = 17; // 17 for 5:00pm
    private static final int MINUTE = 00; // 00 for on the hour

    /**
     * Method to create the table structure, preferences and seed data to run the app
     * @param context
     */
    public void Run(android.content.Context context) {
        try {
            /** get system preference for seed data */
            final SharedPreferences pref_SeedComplete = context.getSharedPreferences(KEY, 0);
            final CharSequence value_SeedComplete = pref_SeedComplete.getString(KEY, "");

            /** if this isn't set then run config */
            if (value_SeedComplete == "") {

                /** set pre-configuration complete flag */
                SharedPreferences.Editor edit_SeedComplete = pref_SeedComplete.edit();
                edit_SeedComplete.putString(KEY, "complete");
                edit_SeedComplete.apply();

                /**
                 * create tables
                 */
                /** re-create the my alerts table */
                MyRecordDBHelper myRecordHelper = new MyRecordDBHelper(context);
                SQLiteDatabase record = myRecordHelper.getWritableDatabase();
                myRecordHelper.onReCreate(record);

                /** re-create the my alerts table */
                MyAlertsDBHelper myAlertsHelper = new MyAlertsDBHelper(context);
                SQLiteDatabase alert = myAlertsHelper.getWritableDatabase();
                myAlertsHelper.onReCreate(alert);

                /** re-create food profile table */
                FoodProfileDBHelper foodHelper = new FoodProfileDBHelper(context);
                SQLiteDatabase food = foodHelper.getWritableDatabase();
                foodHelper.onReCreate(food);

                /**
                 * add food items
                 */
                /** add "coffee with milk" */
                ContentValues values = new ContentValues();
                values.put(FoodProfileDBHelper.NAME, "coffee with milk");
                values.put(FoodProfileDBHelper.FRUITCALORIES, "0");
                values.put(FoodProfileDBHelper.FRUITPORTION, "0");
                values.put(FoodProfileDBHelper.GRAINCALORIES, "0");
                values.put(FoodProfileDBHelper.GRAINPORTION, "0");
                values.put(FoodProfileDBHelper.PROTEINCALORIES, "0");
                values.put(FoodProfileDBHelper.PROTEINPORTION, "0");
                values.put(FoodProfileDBHelper.VEGETABLECALORIES, "0");
                values.put(FoodProfileDBHelper.VEGETABLEPORTION, "0");
                values.put(FoodProfileDBHelper.DAIRYCALORIES, "25");
                values.put(FoodProfileDBHelper.DAIRYPORION, "1");
                values.put(FoodProfileDBHelper.ISNEW, 2);
                food.insert(FoodProfileDBHelper.TABLE_NAME, null, values);

                /** add "toast with jam" */
                values = new ContentValues();
                values.put(FoodProfileDBHelper.NAME, "toast with jam");
                values.put(FoodProfileDBHelper.FRUITCALORIES, "100");
                values.put(FoodProfileDBHelper.FRUITPORTION, "1");
                values.put(FoodProfileDBHelper.GRAINCALORIES, "35");
                values.put(FoodProfileDBHelper.GRAINPORTION, "1");
                values.put(FoodProfileDBHelper.PROTEINCALORIES, "0");
                values.put(FoodProfileDBHelper.PROTEINPORTION, "0");
                values.put(FoodProfileDBHelper.VEGETABLECALORIES, "0");
                values.put(FoodProfileDBHelper.VEGETABLEPORTION, "0");
                values.put(FoodProfileDBHelper.DAIRYCALORIES, "0");
                values.put(FoodProfileDBHelper.DAIRYPORION, "0");
                values.put(FoodProfileDBHelper.ISNEW, 2);
                food.insert(FoodProfileDBHelper.TABLE_NAME, null, values);

                /** add "cereal with milk" */
                values = new ContentValues();
                values.put(FoodProfileDBHelper.NAME, "cereal with milk");
                values.put(FoodProfileDBHelper.FRUITCALORIES, "50");
                values.put(FoodProfileDBHelper.FRUITPORTION, "1");
                values.put(FoodProfileDBHelper.GRAINCALORIES, "100");
                values.put(FoodProfileDBHelper.GRAINPORTION, "1");
                values.put(FoodProfileDBHelper.PROTEINCALORIES, "0");
                values.put(FoodProfileDBHelper.PROTEINPORTION, "0");
                values.put(FoodProfileDBHelper.VEGETABLECALORIES, "0");
                values.put(FoodProfileDBHelper.VEGETABLEPORTION, "0");
                values.put(FoodProfileDBHelper.DAIRYCALORIES, "50");
                values.put(FoodProfileDBHelper.DAIRYPORION, "2");
                values.put(FoodProfileDBHelper.ISNEW, 2);
                food.insert(FoodProfileDBHelper.TABLE_NAME, null, values);

                /** add "hot dogs" */
                values = new ContentValues();
                values.put(FoodProfileDBHelper.NAME, "hot dog w sauce");
                values.put(FoodProfileDBHelper.FRUITCALORIES, "0");
                values.put(FoodProfileDBHelper.FRUITPORTION, "0");
                values.put(FoodProfileDBHelper.GRAINCALORIES, "100");
                values.put(FoodProfileDBHelper.GRAINPORTION, "1");
                values.put(FoodProfileDBHelper.PROTEINCALORIES, "200");
                values.put(FoodProfileDBHelper.PROTEINPORTION, "1");
                values.put(FoodProfileDBHelper.VEGETABLECALORIES, "0");
                values.put(FoodProfileDBHelper.VEGETABLEPORTION, "0");
                values.put(FoodProfileDBHelper.DAIRYCALORIES, "0");
                values.put(FoodProfileDBHelper.DAIRYPORION, "0");
                values.put(FoodProfileDBHelper.ISNEW, 2);
                food.insert(FoodProfileDBHelper.TABLE_NAME, null, values);

                /** add "berry cup w cashews" */
                values = new ContentValues();
                values.put(FoodProfileDBHelper.NAME, "berry cup w cashews");
                values.put(FoodProfileDBHelper.FRUITCALORIES, "200");
                values.put(FoodProfileDBHelper.FRUITPORTION, "2");
                values.put(FoodProfileDBHelper.GRAINCALORIES, "0");
                values.put(FoodProfileDBHelper.GRAINPORTION, "0");
                values.put(FoodProfileDBHelper.PROTEINCALORIES, "150");
                values.put(FoodProfileDBHelper.PROTEINPORTION, "1");
                values.put(FoodProfileDBHelper.VEGETABLECALORIES, "0");
                values.put(FoodProfileDBHelper.VEGETABLEPORTION, "0");
                values.put(FoodProfileDBHelper.DAIRYCALORIES, "0");
                values.put(FoodProfileDBHelper.DAIRYPORION, "0");
                values.put(FoodProfileDBHelper.ISNEW, 2);
                food.insert(FoodProfileDBHelper.TABLE_NAME, null, values);

                /** add "chocolate milk" */
                values = new ContentValues();
                values.put(FoodProfileDBHelper.NAME, "chocolate milk");
                values.put(FoodProfileDBHelper.FRUITCALORIES, "0");
                values.put(FoodProfileDBHelper.FRUITPORTION, "0");
                values.put(FoodProfileDBHelper.GRAINCALORIES, "0");
                values.put(FoodProfileDBHelper.GRAINPORTION, "0");
                values.put(FoodProfileDBHelper.PROTEINCALORIES, "0");
                values.put(FoodProfileDBHelper.PROTEINPORTION, "0");
                values.put(FoodProfileDBHelper.VEGETABLECALORIES, "0");
                values.put(FoodProfileDBHelper.VEGETABLEPORTION, "0");
                values.put(FoodProfileDBHelper.DAIRYCALORIES, "160");
                values.put(FoodProfileDBHelper.DAIRYPORION, "6");
                values.put(FoodProfileDBHelper.ISNEW, 2);
                food.insert(FoodProfileDBHelper.TABLE_NAME, null, values);

                /** add "banana" */
                values = new ContentValues();
                values.put(FoodProfileDBHelper.NAME, "banana");
                values.put(FoodProfileDBHelper.FRUITCALORIES, "120");
                values.put(FoodProfileDBHelper.FRUITPORTION, "1");
                values.put(FoodProfileDBHelper.GRAINCALORIES, "0");
                values.put(FoodProfileDBHelper.GRAINPORTION, "0");
                values.put(FoodProfileDBHelper.PROTEINCALORIES, "0");
                values.put(FoodProfileDBHelper.PROTEINPORTION, "0");
                values.put(FoodProfileDBHelper.VEGETABLECALORIES, "0");
                values.put(FoodProfileDBHelper.VEGETABLEPORTION, "0");
                values.put(FoodProfileDBHelper.DAIRYCALORIES, "0");
                values.put(FoodProfileDBHelper.DAIRYPORION, "0");
                values.put(FoodProfileDBHelper.ISNEW, 2);
                food.insert(FoodProfileDBHelper.TABLE_NAME, null, values);

                /** add "cookie" */
                values = new ContentValues();
                values.put(FoodProfileDBHelper.NAME, "cookie");
                values.put(FoodProfileDBHelper.FRUITCALORIES, "0");
                values.put(FoodProfileDBHelper.FRUITPORTION, "0");
                values.put(FoodProfileDBHelper.GRAINCALORIES, "70");
                values.put(FoodProfileDBHelper.GRAINPORTION, "1");
                values.put(FoodProfileDBHelper.PROTEINCALORIES, "0");
                values.put(FoodProfileDBHelper.PROTEINPORTION, "0");
                values.put(FoodProfileDBHelper.VEGETABLECALORIES, "0");
                values.put(FoodProfileDBHelper.VEGETABLEPORTION, "0");
                values.put(FoodProfileDBHelper.DAIRYCALORIES, "0");
                values.put(FoodProfileDBHelper.DAIRYPORION, "0");
                values.put(FoodProfileDBHelper.ISNEW, 2);
                food.insert(FoodProfileDBHelper.TABLE_NAME, null, values);

                /** add "steak & cheese" */
                values = new ContentValues();
                values.put(FoodProfileDBHelper.NAME, "steak & cheese");
                values.put(FoodProfileDBHelper.FRUITCALORIES, "0");
                values.put(FoodProfileDBHelper.FRUITPORTION, "0");
                values.put(FoodProfileDBHelper.GRAINCALORIES, "100");
                values.put(FoodProfileDBHelper.GRAINPORTION, "1");
                values.put(FoodProfileDBHelper.PROTEINCALORIES, "300");
                values.put(FoodProfileDBHelper.PROTEINPORTION, "2");
                values.put(FoodProfileDBHelper.VEGETABLECALORIES, "0");
                values.put(FoodProfileDBHelper.VEGETABLEPORTION, "0");
                values.put(FoodProfileDBHelper.DAIRYCALORIES, "75");
                values.put(FoodProfileDBHelper.DAIRYPORION, "3");
                values.put(FoodProfileDBHelper.ISNEW, 2);
                food.insert(FoodProfileDBHelper.TABLE_NAME, null, values);

                /** add "pizza" */
                values = new ContentValues();
                values.put(FoodProfileDBHelper.NAME, "pizza");
                values.put(FoodProfileDBHelper.FRUITCALORIES, "0");
                values.put(FoodProfileDBHelper.FRUITPORTION, "0");
                values.put(FoodProfileDBHelper.GRAINCALORIES, "150");
                values.put(FoodProfileDBHelper.GRAINPORTION, "1");
                values.put(FoodProfileDBHelper.PROTEINCALORIES, "50");
                values.put(FoodProfileDBHelper.PROTEINPORTION, "1");
                values.put(FoodProfileDBHelper.VEGETABLECALORIES, "100");
                values.put(FoodProfileDBHelper.VEGETABLEPORTION, "2");
                values.put(FoodProfileDBHelper.DAIRYCALORIES, "75");
                values.put(FoodProfileDBHelper.DAIRYPORION, "3");
                values.put(FoodProfileDBHelper.ISNEW, 2);
                food.insert(FoodProfileDBHelper.TABLE_NAME, null, values);

                /** add "creamsicle" */
                values = new ContentValues();
                values.put(FoodProfileDBHelper.NAME, "creamsicle");
                values.put(FoodProfileDBHelper.FRUITCALORIES, "50");
                values.put(FoodProfileDBHelper.FRUITPORTION, "1");
                values.put(FoodProfileDBHelper.GRAINCALORIES, "00");
                values.put(FoodProfileDBHelper.GRAINPORTION, "0");
                values.put(FoodProfileDBHelper.PROTEINCALORIES, "0");
                values.put(FoodProfileDBHelper.PROTEINPORTION, "0");
                values.put(FoodProfileDBHelper.VEGETABLECALORIES, "0");
                values.put(FoodProfileDBHelper.VEGETABLEPORTION, "0");
                values.put(FoodProfileDBHelper.DAIRYCALORIES, "0");
                values.put(FoodProfileDBHelper.DAIRYPORION, "0");
                values.put(FoodProfileDBHelper.ISNEW, 2);
                food.insert(FoodProfileDBHelper.TABLE_NAME, null, values);

                /**
                 * create preferences
                 */
                /** instantiate shared preference objects */
                final SharedPreferences pref_email = context.getSharedPreferences("pref_email", 0);
                final SharedPreferences pref_phone = context.getSharedPreferences("pref_phone", 0);
                final SharedPreferences pref_sms = context.getSharedPreferences("pref_sms", 0);
                final SharedPreferences pref_maxDailyCalories = context.getSharedPreferences("pref_maxDailyCalories", 0);
                final SharedPreferences pref_fruitMin = context.getSharedPreferences("pref_fruitMin", 0);
                final SharedPreferences pref_fruitMax = context.getSharedPreferences("pref_fruitMax", 0);
                final SharedPreferences pref_grainMin = context.getSharedPreferences("pref_grainMin", 0);
                final SharedPreferences pref_grainMax = context.getSharedPreferences("pref_grainMax", 0);
                final SharedPreferences pref_proteinMin = context.getSharedPreferences("pref_proteinMin", 0);
                final SharedPreferences pref_proteinMax = context.getSharedPreferences("pref_proteinMax", 0);
                final SharedPreferences pref_vegetableMin = context.getSharedPreferences("pref_vegetableMin", 0);
                final SharedPreferences pref_vegetableMax = context.getSharedPreferences("pref_vegetableMax", 0);
                final SharedPreferences pref_dairyMin = context.getSharedPreferences("pref_dairyMin", 0);
                final SharedPreferences pref_dairyMax = context.getSharedPreferences("pref_dairyMax", 0);

                /** get editors */
                SharedPreferences.Editor edit_email = pref_email.edit();
                SharedPreferences.Editor edit_phone = pref_phone.edit();
                SharedPreferences.Editor edit_sms = pref_sms.edit();
                SharedPreferences.Editor edit_maxDailyCalories = pref_maxDailyCalories.edit();
                SharedPreferences.Editor edit_fruitMin = pref_fruitMin.edit();
                SharedPreferences.Editor edit_fruitMax = pref_fruitMax.edit();
                SharedPreferences.Editor edit_grainMin = pref_grainMin.edit();
                SharedPreferences.Editor edit_grainMax = pref_grainMax.edit();
                SharedPreferences.Editor edit_proteinMin = pref_proteinMin.edit();
                SharedPreferences.Editor edit_proteinMax = pref_proteinMax.edit();
                SharedPreferences.Editor edit_vegetableMin = pref_vegetableMin.edit();
                SharedPreferences.Editor edit_vegetableMax = pref_vegetableMax.edit();
                SharedPreferences.Editor edit_dairyMin = pref_dairyMin.edit();
                SharedPreferences.Editor edit_dairyMax = pref_dairyMax.edit();

                /** set values */
                edit_email.putString("pref_email", "nehaabrol87@gmail.com");
                edit_phone.putString("pref_phone", "8005551212");
                edit_sms.putString("pref_sms", "What would be a good time to speak?");
                edit_maxDailyCalories.putString("pref_maxDailyCalories", "You have gone over your calorie limit for the day.");
                edit_fruitMin.putString("pref_fruitMin", "Eat some berries or a banana.");
                edit_fruitMax.putString("pref_fruitMax", "Pick something other than fruit.");
                edit_grainMin.putString("pref_grainMin", "Eat some toast of a muffin.");
                edit_grainMax.putString("pref_grainMax", "Pick something other than a grain.");
                edit_proteinMin.putString("pref_proteinMin", "Eat some fish or meat.");
                edit_proteinMax.putString("pref_proteinMax", "Pick something other than protein.");
                edit_vegetableMin.putString("pref_vegetableMin", "Eat some peppers or a salad.");
                edit_vegetableMax.putString("pref_vegetableMax", "Pick something other than vegetables.");
                edit_dairyMin.putString("pref_dairyMin", "Drink some milk or have a yogurt.");
                edit_dairyMax.putString("pref_dairyMax", "Pick something other than dairy.");

                /** commit changes */
                edit_email.apply();
                edit_phone.apply();
                edit_sms.apply();
                edit_maxDailyCalories.apply();
                edit_fruitMin.apply();
                edit_fruitMax.apply();
                edit_grainMin.apply();
                edit_grainMax.apply();
                edit_proteinMin.apply();
                edit_proteinMax.apply();
                edit_vegetableMin.apply();
                edit_vegetableMax.apply();
                edit_dairyMin.apply();
                edit_dairyMax.apply();

                /** instantiate alarm manager */
                AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

                /** create an intent using the AlarmReceiver as the catcher */
                Intent intent = new Intent(context, AlarmReceiver.class);

                /** wrap the intent in a pending intent */
                PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

                /** Set the alarm start time */
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY, HOUR_OF_DAY);
                calendar.set(Calendar.MINUTE, MINUTE);

                /** set Inexact repeating on a daily basis with wake up enabled */
                alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, alarmIntent);
            }

            Log.i(TAG, "SeedData.Run() - executed");
        } catch (Exception e) {
            Log.i(TAG, "SeedData.Run() - ERROR " + e.getMessage());
        }
    }
}
