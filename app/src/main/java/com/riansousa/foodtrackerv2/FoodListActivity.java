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


/**
 * Class that handles all the code processing to support the activity_food_list view.
 */
public class FoodListActivity extends AppCompatActivity {

    /**
     * private global variables
     */
    private static final String TAG = "FoodTracker";

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
        Log.i(TAG, "FoodListActivity - Menu Options Loaded");
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
                Log.i(TAG, "FoodListActivity - The food list icon was clicked");
                // load food list screen
                Intent foodListScreen = new Intent(getApplicationContext(), FoodListActivity.class);
                startActivity(foodListScreen);
                break;
            case R.id.menu_recorder:
                Log.i(TAG, "FoodListActivity - The recorder icon was clicked");
                // load food list screen
                Intent recorder = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(recorder);
                break;
            case R.id.menu_alert:
                Log.i(TAG, "FoodListActivity - The alert icon was clicked");
                // load food list screen
                Intent alert = new Intent(getApplicationContext(), AlertActivity.class);
                startActivity(alert);
                break;
            case R.id.menu_reporting:
                Log.i(TAG, "FoodListActivity - The reporting icon was clicked");
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
        Log.i(TAG, "FoodListActivity - onStart");
    }

    /**
     * System method used for logging activity life cycle onResume()
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "FoodListActivity - onResume");
    }

    /**
     * System method used for logging activity life cycle onPause()
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "FoodListActivity - onPause");
    }

    /**
     * System method used for logging activity life cycle onStop()
     */
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "FoodListActivity - onStop");
    }

    /**
     * System method used for logging activity life cycle onRestart()
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "FoodListActivity - onRestart");
    }

    /**
     * System method used for logging activity life cycle onDestroy()
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "FoodListActivity - onDestroy");
    }

    /**
     * System method used for saving state instance
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "FoodListActivity - onSaveInstanceState");
    }

    /**
     * System method used for restoring state instance
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "FoodListActivity - onRestoreInstanceState");
    }

    /**
     * Private method to handle the initial load of the activity
     * used for setting default values and event listeners
     * research on listview from http://www.tutorialspoint.com/android/android_list_view.htm
     */
    private void initMain() {
        try {
            // Instantiate listview objects
            ListView listNew = (ListView) findViewById(R.id.listNewItems);
            ListView listExisting = (ListView) findViewById(R.id.listExistingItems);

            // Create a string array for seed data
            // todo: replace with data pull from repository
            String[] valuesNew = new String[]{"Orange Juice"};
            String[] valuesExisting = new String[]{"Raisin Bran", "Fruit & Nuts Cup", "Coffee",
                    "Banana", "Hot Dogs", "Chili", "Meatball Sub", "Pizza",
                    "Steak & Cheese", "Apple Juice", "Chicken Stir Fry", "Hamburger with Cheese", "Sloppy Joe", "Sword Fish",
                    "Sushi Regular"};

            // Create an adapter as the source for the list view. Note: MUST create TEXTVIEW layout for rendering
            ArrayAdapter<String> adapterNew = new ArrayAdapter<String>(this, R.layout.food_item, valuesNew);
            ArrayAdapter<String> adapterExisting = new ArrayAdapter<String>(this, R.layout.food_item, valuesExisting);

            // Set the adapter as the list source
            listNew.setAdapter(adapterNew);
            listExisting.setAdapter(adapterExisting);

            // Log action
            Log.i(TAG, "FoodListActivity - Food Profile load complete");

            // Create 1st listener to capture item click on NEW list
            listNew.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                // Implement click handle
                @Override
                public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                    // get item from position
                    String item = (String) parent.getItemAtPosition(position);

                    // Log item click
                    Log.i(TAG, "FoodListActivity - You clicked " + item + " from food list.");

                    // create intent
                    // research on intents and passing data between screens from
                    // http://stackoverflow.com/questions/2091465/how-do-i-pass-data-between-activities-on-android
                    Intent foodListDetailScreen = new Intent(getApplicationContext(), FoodListDetailActivity.class);

                    // set key/value data pair to pass selected item
                    foodListDetailScreen.putExtra("itemSelected", item);

                    // load food list detail screen
                    startActivity(foodListDetailScreen);
                }
            });

            // Create 2nd listener to capture item click on EXISTING list
            listExisting.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                // Implement click handle
                @Override
                public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                    // get item from position
                    String item = (String) parent.getItemAtPosition(position);

                    // Log item click
                    Log.i(TAG, "FoodListActivity - You clicked " + item + " from food list.");

                    // create intent
                    // research on intents and passing data between screens from
                    // http://stackoverflow.com/questions/17743094/how-to-switch-between-activities-screens-in-android
                    Intent foodListDetailScreen = new Intent(getApplicationContext(), FoodListDetailActivity.class);

                    // set key/value data pair to pass selected item
                    foodListDetailScreen.putExtra("itemSelected", item);

                    // load food list detail screen
                    startActivity(foodListDetailScreen);
                }
            });
        } catch (Exception e) {
            Log.i(TAG, "FoodListActivity ERROR - " + e.getMessage());
        }
    }
}
