package com.riansousa.foodtrackerv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Class that handles all the code processing to support the activity_alert view.
 */
public class AlertActivity extends AppCompatActivity {

    /**
     * private global variables
     */
    private static final String TAG = "FoodTracker";
    private EditText _maxDailyCalories;
    private EditText _fruitMin;
    private EditText _fruitMax;

    /**
     * Default method to load the activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);
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
        Log.i(TAG, "AlertActivity - Menu Options Loaded");
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
            case R.id.menu_food_list:
                Log.i(TAG, "AlertActivity - The food list icon was clicked");
                // load food list screen
                Intent foodListScreen = new Intent(getApplicationContext(), FoodListActivity.class);
                startActivity(foodListScreen);
                break;
            case R.id.menu_recorder:
                Log.i(TAG, "AlertActivity - The recorder icon was clicked");
                // load food list screen
                Intent recorder = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(recorder);
                break;
            case R.id.menu_alert:
                Log.i(TAG, "AlertActivity - The alert icon was clicked");
                // load food list screen
                Intent alert = new Intent(getApplicationContext(), AlertActivity.class);
                startActivity(alert);
                break;
            case R.id.menu_reporting:
                Log.i(TAG, "AlertActivity - The reporting icon was clicked");
                // load food list screen
                Intent report = new Intent(getApplicationContext(), ReportingActivity.class);
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
        Log.i(TAG, "AlertActivity - onStart");
    }

    /**
     * System method used for logging activity life cycle onResume()
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "AlertActivity - onResume");
    }

    /**
     * System method used for logging activity life cycle onPause()
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "AlertActivity - onPause");
    }

    /**
     * System method used for logging activity life cycle onStop()
     */
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "AlertActivity - onStop");
    }

    /**
     * System method used for logging activity life cycle onRestart()
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "AlertActivity - onRestart");
    }

    /**
     * System method used for logging activity life cycle onDestroy()
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "AlertActivity - onDestroy");
    }

    /**
     * System method used for saving state instance
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "AlertActivity - onSaveInstanceState");
    }

    /**
     * System method used for restoring state instance
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "MainActivity - onRestoreInstanceState");
    }

    /**
     * Private method to handle the initial load of the activity
     * used for setting default values and event listeners
     */
    private void initMain() {
        // instantiate button object
        Button button = (Button) findViewById(R.id.btnUpdateMyAlerts);

        // assign values to global variables
        // research on "getting" UI data from
        // http://stackoverflow.com/questions/4531396/get-value-of-a-edit-text-field
        _maxDailyCalories = (EditText)findViewById(R.id.txtMaxDailyCalories);
        _fruitMin = (EditText)findViewById(R.id.txtFruitMin);
        _fruitMax = (EditText)findViewById(R.id.txtFruitMax);

        // set listener for onClick event
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // handle click
                Log.i(TAG, "FoodListDetailActivity - Update Food Item Button Clicked");

                try {
                    // log form details
                    Log.i(TAG, "FoodListDetailActivity - Max Daily Calories:" + _maxDailyCalories.getText());
                    Log.i(TAG, "FoodListDetailActivity - Max Fruit Calories:" + _fruitMax.getText());
                    Log.i(TAG, "FoodListDetailActivity - Min Fruit Calories:" + _fruitMin.getText());

                    // send Toast confirm
                    Toast.makeText(getApplicationContext(), "My Alerts have been updated!", Toast.LENGTH_SHORT).show();

                    // todo: record to repository

                } catch (Exception e) {
                    Log.i(TAG, "FoodListDetailActivity ERROR - " + e.getMessage());
                }

                // redirect back to main
                Intent main = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(main);
            }
        });
    }
}
