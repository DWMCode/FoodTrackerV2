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
import android.widget.TextView;
import android.widget.Toast;

/**
 * Class that handles all the code processing to support the activity_food_list_detail view.
 */
public class FoodListDetailActivity extends AppCompatActivity {

    /**
     * private global variables
     */
    private static final String TAG = "FoodTracker";
    private EditText _itemName;
    private EditText _fruitCalories;
    private EditText _grainCalories;
    private EditText _vegetableCalories;
    private EditText _proteinCalories;
    private EditText _dairyCalories;

    /**
     * Default method to load the activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list_detail);
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
        Log.i(TAG, "FoodListDetailActivity - Menu Options Loaded");
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
                Log.i(TAG, "FoodListDetailActivity - The food list icon was clicked");
                // load food list screen
                Intent foodListScreen = new Intent(getApplicationContext(), FoodListActivity.class);
                startActivity(foodListScreen);
                break;
            case R.id.menu_recorder:
                Log.i(TAG, "FoodListDetailActivity - The recorder icon was clicked");
                // load food list screen
                Intent recorder = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(recorder);
                break;
            case R.id.menu_alert:
                Log.i(TAG, "FoodListDetailActivity - The alert icon was clicked");
                // load food list screen
                Intent alert = new Intent(getApplicationContext(), AlertActivity.class);
                startActivity(alert);
                break;
            case R.id.menu_reporting:
                Log.i(TAG, "FoodListDetailActivity - The reporting icon was clicked");
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
        Log.i(TAG, "FoodListDetailActivity - onStart");
    }

    /**
     * System method used for logging activity life cycle onResume()
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "FoodListDetailActivity - onResume");
    }

    /**
     * System method used for logging activity life cycle onPause()
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "FoodListDetailActivity - onPause");
    }

    /**
     * System method used for logging activity life cycle onStop()
     */
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "FoodListDetailActivity - onStop");
    }

    /**
     * System method used for logging activity life cycle onRestart()
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "FoodListDetailActivity - onRestart");
    }

    /**
     * System method used for logging activity life cycle onDestroy()
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "FoodListDetailActivity - onDestroy");
    }

    /**
     * System method used for saving state instance
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "FoodListDetailActivity - onSaveInstanceState");
    }

    /**
     * System method used for restoring state instance
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "FoodListDetailActivity - onRestoreInstanceState");
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
        _itemName = (EditText)findViewById(R.id.txtItemName);
        _fruitCalories = (EditText)findViewById(R.id.txtFruitCalories);
        _grainCalories = (EditText)findViewById(R.id.txtGrainCalories);
        _vegetableCalories = (EditText)findViewById(R.id.txtVegetableCalories);
        _proteinCalories = (EditText)findViewById(R.id.txtProteinCalories);
        _dairyCalories = (EditText)findViewById(R.id.txtDairyCalories);

        // get any key/value data pairs for this activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // Research on setting UI Element Data from
            // http://stackoverflow.com/questions/4590957/how-to-set-text-in-an-edittext
            String value = extras.getString("itemSelected");
            _itemName.setText(value, TextView.BufferType.EDITABLE);
        }

        // set listener for onClick event
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // handle click
                Log.i(TAG, "FoodListDetailActivity - Update Food Item Button Clicked");

                try {
                    // log form details
                    Log.i(TAG, "FoodListDetailActivity - Form Item Name:" + _itemName.getText());
                    Log.i(TAG, "FoodListDetailActivity - Form Fruit Calories:" + _fruitCalories.getText());
                    Log.i(TAG, "FoodListDetailActivity - Form Grain Calories:" + _grainCalories.getText());
                    Log.i(TAG, "FoodListDetailActivity - Form Vegetable Calories:" + _vegetableCalories.getText());
                    Log.i(TAG, "FoodListDetailActivity - Form Protein Calories:" + _proteinCalories.getText());
                    Log.i(TAG, "FoodListDetailActivity - Form Dairy Calories:" + _dairyCalories.getText());

                    // send Toast confirm
                    Toast.makeText(getApplicationContext(),  _itemName.getText() +  " has been updated.", Toast.LENGTH_SHORT).show();

                    // todo: record to repository

                } catch (Exception e) {
                    Log.i(TAG, "FoodListDetailActivity ERROR - " + e.getMessage());
                }

                // redirect back to food list screen
                Intent foodListScreen = new Intent(getApplicationContext(), FoodListActivity.class);
                startActivity(foodListScreen);
            }
        });
    }
}
