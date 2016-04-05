package com.riansousa.foodtrackerv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.riansousa.foodtrackerv2.Logic.ErrorLog;
import com.riansousa.foodtrackerv2.Logic.FoodProfile;


/**
 * Class that handles all the code processing to support the activity_food_list view.
 */
public class FoodProfileListActivity extends AppCompatActivity {

    /**
     * private global variables
     */
    private static final String TAG = "FoodTracker";
    private FoodProfile _foodProfile = new FoodProfile();
    private ErrorLog _log = new ErrorLog();

    /**
     * Default method to load the activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        initMain();
    }

    /**
     * Method to create the menu.
     * Adapted from: Lecture Notes Module 1: Example illustrating state changes
     *
     * @param menu  Takes a Menu object
     * @return      boolean value from super
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mif = getMenuInflater();
        mif.inflate(R.menu.menu_main, menu);
        Log.i(TAG, "FoodProfileListActivity - Menu Options Loaded");
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Method to handle option selection event.
     * Launches a new Activity Screen based on selection.
     * Adapted from: Lecture Notes Module 1: Example illustrating state changes
     * research on switching between activity screens from
     * http://stackoverflow.com/questions/17743094/how-to-switch-between-activities-screens-in-android
     *
     * @param item  The menu item selected by the user
     * @return      boolean value from super
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // research on switching between activity screens from
        // http://stackoverflow.com/questions/17743094/how-to-switch-between-activities-screens-in-android
        switch(item.getItemId())
        {
            case R.id.menu_food_profile:
                Log.i(TAG, "FoodProfileListActivity - The food list icon was clicked");
                // load food list screen
                Intent foodListScreen = new Intent(getApplicationContext(), FoodProfileListActivity.class);
                startActivity(foodListScreen);
                break;
            case R.id.menu_recorder:
                Log.i(TAG, "FoodProfileListActivity - The main icon was clicked");
                // load main screen
                Intent recorder = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(recorder);
                break;
            case R.id.menu_my_alert:
                Log.i(TAG, "FoodProfileListActivity - The alert icon was clicked");
                // load alert screen
                Intent alert = new Intent(getApplicationContext(), MyAlertActivity.class);
                startActivity(alert);
                break;
            case R.id.menu_my_preferences:
                Log.i(TAG, "FoodProfileListActivity - The preferences icon was clicked");
                // load my preferences screen
                Intent preference = new Intent(getApplicationContext(), MyPreferencesActivity.class);
                startActivity(preference);
                break;
            case R.id.menu_my_reports:
                Log.i(TAG, "FoodProfileListActivity - The reporting icon was clicked");
                // load reporting screen
                Intent report = new Intent(getApplicationContext(), MyReportingActivity.class);
                startActivity(report);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * System method used for logging activity life cycle onStart()
     */
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "FoodProfileListActivity - onStart");
    }

    /**
     * System method used for logging activity life cycle onResume()
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "FoodProfileListActivity - onResume");
    }

    /**
     * System method used for logging activity life cycle onPause()
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "FoodProfileListActivity - onPause");
    }

    /**
     * System method used for logging activity life cycle onStop()
     */
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "FoodProfileListActivity - onStop");
    }

    /**
     * System method used for logging activity life cycle onRestart()
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "FoodProfileListActivity - onRestart");
    }

    /**
     * System method used for logging activity life cycle onDestroy()
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "FoodProfileListActivity - onDestroy");
    }

    /**
     * System method used for saving state instance
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "FoodProfileListActivity - onSaveInstanceState");
    }

    /**
     * System method used for restoring state instance
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "FoodProfileListActivity - onRestoreInstanceState");
    }

    /**
     * Private method to handle the initial load of the activity
     * used for setting default values and event listeners
     * research on listview from http://www.tutorialspoint.com/android/android_list_view.htm
     */
    private void initMain() {
        try {
            /** Instantiate listview objects */
            ListView list = (ListView) findViewById(R.id.listItems);

            /*  data pull from repository, use 0 for state param to pull all food items */
            String[] valuesNew = _foodProfile.GetFoodProfileList(getApplicationContext(), 1);       // 1=new
            String[] valuesExisting = _foodProfile.GetFoodProfileList(getApplicationContext(), 2);  // 2=existing

            /* create a combined new string array to show new items at the top with a NEW marker */
            int count = 0;
            int totalRows = valuesNew.length + valuesExisting.length;
            String[] combined = new String[totalRows];
            for (int i=0; i<valuesNew.length; i++) {
                combined[count] = valuesNew[i] + " - NEW ";
                count++;
            }
            for (int i=0; i<valuesExisting.length; i++) {
                combined[count] = valuesExisting[i];
                count++;
            }

            /** Create an adapter as the source for the list view. Note: MUST create TEXTVIEW layout for rendering */
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.food_item, combined);

            /** Set the adapter as the list source */
            list.setAdapter(adapter);

            /** Log action */
            Log.i(TAG, "FoodProfileListActivity - Food Profile load complete");

            /** Create 1st listener to capture item click on NEW list */
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                /** Implement click handle */
                @Override
                public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                    /** get item from position */
                    String item = (String) parent.getItemAtPosition(position);

                    /** Log item click */
                    Log.i(TAG, "FoodProfileListActivity - You clicked " + item + " from food list.");

                    /** create intent
                     * research on intents and passing data between screens from
                     * http://stackoverflow.com/questions/2091465/how-do-i-pass-data-between-activities-on-android
                     */
                    Intent foodListDetailScreen = new Intent(getApplicationContext(), FoodProfileDetailActivity.class);

                    /** set key/value data pair to pass selected item */
                    foodListDetailScreen.putExtra("itemSelected", item);

                    /** load food list detail screen */
                    startActivity(foodListDetailScreen);
                }
            });

        } catch (Exception e) {
            /** log to file */
            _log.WriteError(getApplicationContext(), "FoodProfileListActivity.initMain() - ERROR: " + e.getMessage());
            /** log to console */
            Log.i(TAG, "FoodProfileListActivity.initMain() - ERROR: " + e.getMessage());
        }
    }
}
